package org.homesharing.lockbox.data.network.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import org.homesharing.lockbox.data.network.dto.BoxDto
import org.homesharing.lockbox.domain.api.BoxesApi

private const val STARTING_PAGE = 0

class BoxesPagingSource(
    private val api: BoxesApi
) : PagingSource<Int, BoxDto>() {
    override fun getRefreshKey(state: PagingState<Int, BoxDto>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, BoxDto> {
        val page = params.key ?: STARTING_PAGE
        val size = params.loadSize

        val response = api.getBoxes(page, size)
        val endOfPagination = response.body.boxesOnPage < size
        return  LoadResult.Page(
            data = response.body.boxes,
            prevKey = if (page == 0) null else page - 1,
            nextKey = if (endOfPagination) null else page + 1
        )
    }
}
