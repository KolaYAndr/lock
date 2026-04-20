package org.homesharing.lockbox.domain.repository

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import org.homesharing.lockbox.domain.api.response.Response
import org.homesharing.lockbox.domain.model.contract.Contract
import org.homesharing.lockbox.domain.model.contract_detailed.ContractDetailed
import org.homesharing.lockbox.domain.model.contract_file.ContractFile

interface DocumentsRepository {
    fun getDocumentsPagingFlow(): Flow<PagingData<Contract>>
    suspend fun deleteContract(id: String): Response<Unit>
    suspend fun getContract(id: String): Response<ContractDetailed>
    suspend fun getContractFile(id: String): Response<ContractFile>
}