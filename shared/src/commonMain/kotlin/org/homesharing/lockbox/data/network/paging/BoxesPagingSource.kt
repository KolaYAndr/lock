package org.homesharing.lockbox.data.network.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import org.homesharing.lockbox.data.network.api.BoxesApi
import org.homesharing.lockbox.data.network.api.response.ApiResponse
import org.homesharing.lockbox.data.network.dto.box.BoxWithStatusDto
import kotlin.coroutines.cancellation.CancellationException

private const val STARTING_PAGE = 0

internal class BoxesPagingSource(
    private val boxesApi: BoxesApi,
) : PagingSource<Int, BoxWithStatusDto>() {

    override fun getRefreshKey(state: PagingState<Int, BoxWithStatusDto>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, BoxWithStatusDto> {
        val page = params.key ?: STARTING_PAGE
        val size = params.loadSize
        try {
            val apiResponse = boxesApi.getBoxes(
                page = page,
                size = size
            )

            return when (apiResponse) {
                is ApiResponse.Failure ->
                    LoadResult.Error(Throwable(apiResponse.message))

                is ApiResponse.Success -> {
                    val endOfPagination = apiResponse.data.boxes.size < size
                    LoadResult.Page(
                        data = apiResponse.data.boxes,
                        prevKey = if (page == 0) null else page - 1,
                        nextKey = if (endOfPagination) null else page + 1
                    )
                }
            }
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }
}
