package org.homesharing.lockbox.data.network.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import org.homesharing.lockbox.data.network.model.BoxDto
import org.homesharing.lockbox.domain.api.BoxesApi

private const val STARTING_PAGE = 0

class BoxesPagingSource(
    private val api: BoxesApi
) : PagingSource<Int, BoxDto>() {
    override fun getRefreshKey(state: PagingState<Int, BoxDto>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, BoxDto> {
        val start = params.key ?: STARTING_PAGE
        val offset = params.loadSize

        val response = api.getBoxes(start, offset)
        val endOfPagination = response.body.boxes.isEmpty()
        return  LoadResult.Page(
            data = response.body.boxes,
            prevKey = if (start == 0) null else start - offset,
            nextKey = if (endOfPagination) null else start + offset
        )
    }
}
