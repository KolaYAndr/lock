package org.homesharing.lockbox.data.network.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import org.homesharing.lockbox.data.network.api.response.ApiResponse
import org.homesharing.lockbox.data.network.dto.box_list.BoxListResponseDto
import org.homesharing.lockbox.domain.api.BoxesApi
import org.homesharing.lockbox.domain.model.box.BoxWithStatus
import org.homesharing.lockbox.domain.model.box.toDomain
import kotlin.coroutines.cancellation.CancellationException

private const val STARTING_PAGE = 0

@Suppress("UNCHECKED_CAST")
internal class BoxesPagingSource(
    private val boxesApi: BoxesApi,
) : PagingSource<Int, BoxWithStatus>() {

    override fun getRefreshKey(state: PagingState<Int, BoxWithStatus>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, BoxWithStatus> {
        val page = params.key ?: STARTING_PAGE
        val size = params.loadSize
        try {
            val apiResponse = boxesApi.getBoxes(
                page = page,
                size = size
            )

            return when (apiResponse) {
                is ApiResponse.Failure -> {
                    if (apiResponse.errorCode == "empty_boxes") {
                        LoadResult.Page(emptyList(), null, null)
                    } else {
                        LoadResult.Error(Throwable(apiResponse.message))
                    }
                }

                is ApiResponse.Success<*> -> {
                    val response = apiResponse as ApiResponse.Success<BoxListResponseDto>
                    val endOfPagination = response.data.size < size
                    LoadResult.Page(
                        data = response.data.boxes.map { it.toDomain() },
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
