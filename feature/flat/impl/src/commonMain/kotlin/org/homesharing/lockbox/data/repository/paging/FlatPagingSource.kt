package org.homesharing.lockbox.data.repository.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import org.homesharing.lockbox.data.network.api.FlatApi
import org.homesharing.lockbox.data.network.api.response.ApiResponse
import org.homesharing.lockbox.data.network.dto.flat_list.FlatStatusFilterDto
import org.homesharing.lockbox.data.network.dto.flat_list.FlatSummaryListDto
import kotlin.coroutines.cancellation.CancellationException

internal class FlatPagingSource(
    private val api: FlatApi,
    private val status: FlatStatusFilterDto,
) : PagingSource<String, FlatSummaryListDto.FlatSummaryDto>() {
    override suspend fun load(
        params: LoadParams<String>
    ): LoadResult<String, FlatSummaryListDto.FlatSummaryDto> {
        val cursor = params.key
        val size = params.loadSize

        try {
            val apiResponse = api.getFlats(
                cursor = cursor,
                limit = size,
                status = status,
            )

            return when (apiResponse) {
                is ApiResponse.Failure -> LoadResult.Error(Throwable(apiResponse.message))
                is ApiResponse.Success -> {
                    val endOfPagination =
                        apiResponse.data.nextCursor == null || apiResponse.data.items.isEmpty()
                    LoadResult.Page(
                        data = apiResponse.data.items,
                        prevKey = null,
                        nextKey = if (endOfPagination) null else apiResponse.data.nextCursor
                    )
                }
            }
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(
        state: PagingState<String, FlatSummaryListDto.FlatSummaryDto>
    ): String? = null
}
