package org.homesharing.lockbox.domain.repository

import org.homesharing.lockbox.domain.api.response.Response
import org.homesharing.lockbox.domain.model.contract.Contract
import org.homesharing.lockbox.domain.model.contract.ContractDraft
import org.homesharing.lockbox.domain.model.contract.ContractPage
import org.homesharing.lockbox.domain.model.contract.ContractPdf

interface ContractRepository {
    suspend fun getContracts(
        limit: Int = 10,
        cursor: String? = null,
    ): Response<ContractPage>

    suspend fun createContract(request: ContractDraft): Response<Contract>

    suspend fun getContract(id: String): Response<Contract>

    suspend fun updateContract(
        id: String,
        request: ContractDraft,
    ): Response<Contract>

    suspend fun deleteContract(id: String): Response<Unit>

    suspend fun getContractPdf(id: String): Response<ContractPdf>
}
