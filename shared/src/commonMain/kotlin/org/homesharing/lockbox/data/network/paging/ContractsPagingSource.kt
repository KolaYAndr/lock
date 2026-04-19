package org.homesharing.lockbox.data.network.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import org.homesharing.lockbox.data.network.api.DocumentsApi
import org.homesharing.lockbox.data.network.api.response.ApiResponse
import org.homesharing.lockbox.data.network.dto.contract_list.ContractDto
import kotlin.coroutines.cancellation.CancellationException

internal class ContractsPagingSource(
    private val documentsApi: DocumentsApi
) : PagingSource<String, ContractDto>() {
    override fun getRefreshKey(state: PagingState<String, ContractDto>): String? = null

    override suspend fun load(params: LoadParams<String>): LoadResult<String, ContractDto> {
        val cursor = params.key
        val size = params.loadSize

        try {
            val apiResponse = documentsApi.getContracts(
                cursor = cursor,
                limit = size
            )

            return when (apiResponse) {
                is ApiResponse.Failure -> LoadResult.Error(Throwable(apiResponse.message))
                is ApiResponse.Success -> {
                    val endOfPagination = apiResponse.data.contracts.size < size
                    LoadResult.Page(
                        data = apiResponse.data.contracts,
                        prevKey = cursor,
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
}