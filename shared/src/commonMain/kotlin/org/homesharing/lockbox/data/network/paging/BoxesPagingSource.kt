package org.homesharing.lockbox.data.network.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import kotlinx.coroutines.CancellationException
import org.homesharing.lockbox.data.network.token.refresh_manager.RefreshManager
import org.homesharing.lockbox.data.network.token.storage.TokenStorage
import org.homesharing.lockbox.domain.api.AccountApi
import org.homesharing.lockbox.domain.api.BoxesApi
import org.homesharing.lockbox.domain.model.box.Box
import org.homesharing.lockbox.domain.model.box.toDomain

private const val STARTING_PAGE = 0

internal class BoxesPagingSource(
    private val boxesApi: BoxesApi,
    private val accountApi: AccountApi,
    private val tokenStorage: TokenStorage,
    private val refreshManager: RefreshManager
) : PagingSource<Int, Box>() {

    override fun getRefreshKey(state: PagingState<Int, Box>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Box> {
        val page = params.key ?: STARTING_PAGE
        val size = params.loadSize
        if (refreshManager.needsRefresh(tokenStorage.getNextRefreshTime())) {
            try {
                refreshToken()
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                return LoadResult.Error(Throwable(e.message))
            }
        }

        try {
            val apiResponse = boxesApi.getBoxes(
                accessToken = tokenStorage.getAccessToken(),
                page = page,
                size = size
            )

            if (apiResponse.error) {
                return LoadResult.Error(Throwable(apiResponse.errorText))
            } else {
                val endOfPagination = apiResponse.body!!.boxesOnPage < size
                return LoadResult.Page(
                    data = apiResponse.body.boxes.map { it.toDomain() },
                    prevKey = if (page == 0) null else page - 1,
                    nextKey = if (endOfPagination) null else page + 1
                )
            }
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }

    private suspend fun refreshToken() {
        val tokenResponse =
            accountApi.refreshTokens(
                accessToken = tokenStorage.getAccessToken(),
                refreshToken = tokenStorage.getRefreshToken()
            )

        if (tokenResponse.error.isNullOrEmpty()) {
            val accessToken = tokenResponse.accessToken!!
            val refreshToken = tokenResponse.refreshToken!!
            val expiresIn = tokenResponse.expiresIn!!

            with(tokenStorage) {
                saveAccessToken(accessToken)
                saveRefreshToken(refreshToken)
                saveNextRefreshTime(expiresIn + refreshManager.currentTime)
            }
        }
    }
}
