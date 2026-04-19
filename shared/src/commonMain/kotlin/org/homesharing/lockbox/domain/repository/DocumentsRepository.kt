package org.homesharing.lockbox.domain.repository

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import org.homesharing.lockbox.domain.api.response.Response
import org.homesharing.lockbox.domain.model.contract.Contract

interface DocumentsRepository {
    fun getDocumentsPagingFlow(): Flow<PagingData<Contract>>
    suspend fun deleteContract(id: String): Response<Unit>
}