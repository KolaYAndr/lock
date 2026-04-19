package org.homesharing.lockbox.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.homesharing.lockbox.data.network.api.DocumentsApi
import org.homesharing.lockbox.data.network.paging.ContractsPagingSource
import org.homesharing.lockbox.domain.model.contract.Contract
import org.homesharing.lockbox.domain.model.contract.toDomain
import org.homesharing.lockbox.domain.repository.DocumentsRepository

private const val PAGE_SIZE = 10

internal class DocumentsRepositoryImpl(
    private val documentsApi: DocumentsApi,
) : DocumentsRepository {
    override fun getDocumentsPagingFlow(): Flow<PagingData<Contract>> =
        Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = false,
                initialLoadSize = PAGE_SIZE,
            ),
            pagingSourceFactory = { ContractsPagingSource(documentsApi) },
        ).flow
            .map { pagingData ->
                pagingData.map { contractDto ->
                    contractDto.toDomain()
                }
            }
}