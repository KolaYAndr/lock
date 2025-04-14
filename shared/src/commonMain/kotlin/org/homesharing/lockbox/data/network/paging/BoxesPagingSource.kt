package org.homesharing.lockbox.data.network.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import org.homesharing.lockbox.domain.api.BoxesApi
import org.homesharing.lockbox.domain.model.box.Box
import org.homesharing.lockbox.domain.model.box.toDomain

private const val STARTING_PAGE = 0

class BoxesPagingSource(
    private val api: BoxesApi
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
            val apiResponse = api.getBoxes(page, size)

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
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }
}
