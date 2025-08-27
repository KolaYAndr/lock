package org.homesharing.lockbox.data.network.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import kotlinx.coroutines.CancellationException
import org.homesharing.lockbox.data.network.api.response.ApiResponse
import org.homesharing.lockbox.data.network.api.response.AuthResponse
import org.homesharing.lockbox.data.network.dto.box_list.BoxListResponseDto
import org.homesharing.lockbox.data.network.token.refresh_manager.RefreshManager
import org.homesharing.lockbox.data.network.token.storage.TokenStorage
import org.homesharing.lockbox.domain.api.AccountApi
import org.homesharing.lockbox.domain.api.BoxesApi
import org.homesharing.lockbox.domain.model.box.Box
import org.homesharing.lockbox.domain.model.box.toDomain

private const val STARTING_PAGE = 0
private const val NO_BOXES_MESSAGE_CODE = "empty_boxes"

@Suppress("UNCHECKED_CAST")
internal class BoxesPagingSource(
    private val boxesApi: BoxesApi,
    private val accountApi: AccountApi,
    private val tokenStorage: TokenStorage,
    private val refreshManager: RefreshManager,
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

        try {
            refreshTokenIfNeed()
            val apiResponse = boxesApi.getBoxes(
                accessToken = tokenStorage.getAccessToken(),
                page = page,
                size = size
            )

            return if (apiResponse is ApiResponse.Success<*>) {
                val response = apiResponse as ApiResponse.Success<BoxListResponseDto>
                val endOfPagination = response.data.boxesOnPage < size
                LoadResult.Page(
                    data = response.data.boxes.map { it.toDomain() },
                    prevKey = if (page == 0) null else page - 1,
                    nextKey = if (endOfPagination) null else page + 1
                )
            } else {
                val response = apiResponse as ApiResponse.Failure
                if (checkIfUserHasNoBoxes(response.errorCode)) LoadResult.Page(
                    data = emptyList(),
                    prevKey = null,
                    nextKey = null
                )
                else LoadResult.Error(Throwable(response.message))
            }
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }

    private fun checkIfUserHasNoBoxes(errorCode: String) = errorCode == NO_BOXES_MESSAGE_CODE

    private suspend fun refreshTokenIfNeed() {
        if (refreshManager.needsRefresh(tokenStorage.getNextRefreshTime())) {
            refreshToken()
        }
    }

    private suspend fun refreshToken() {
        val tokenResponse =
            accountApi.refreshTokens(
                accessToken = tokenStorage.getAccessToken(),
                refreshToken = tokenStorage.getRefreshToken()
            )

        if (tokenResponse is AuthResponse.Success) {
            val accessToken = tokenResponse.accessToken
            val refreshToken = tokenResponse.refreshToken
            val expiresIn = tokenResponse.expiresIn

            with(tokenStorage) {
                saveAccessToken(accessToken)
                saveRefreshToken(refreshToken)
                saveNextRefreshTime(expiresIn + refreshManager.currentTime)
            }
        }
    }
}
