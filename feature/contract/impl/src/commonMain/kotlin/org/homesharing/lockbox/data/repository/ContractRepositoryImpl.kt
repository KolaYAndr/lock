package org.homesharing.lockbox.data.repository

import org.homesharing.lockbox.data.network.api.ContractApi
import org.homesharing.lockbox.data.network.dto.contract.ContractDto
import org.homesharing.lockbox.data.network.dto.contract.ContractPageDto
import org.homesharing.lockbox.data.network.dto.contract.ContractPdfDto
import org.homesharing.lockbox.data.network.dto.contract.toDomain
import org.homesharing.lockbox.data.network.dto.contract.toDto
import org.homesharing.lockbox.domain.api.response.Response
import org.homesharing.lockbox.domain.api.response.parseLockboxApiResponse
import org.homesharing.lockbox.domain.model.contract.Contract
import org.homesharing.lockbox.domain.model.contract.ContractDraft
import org.homesharing.lockbox.domain.model.contract.ContractPage
import org.homesharing.lockbox.domain.model.contract.ContractPdf
import org.homesharing.lockbox.domain.repository.ContractRepository

internal class ContractRepositoryImpl(
    private val api: ContractApi,
) : ContractRepository {
    override suspend fun getContracts(
        limit: Int,
        cursor: String?,
    ): Response<ContractPage> =
        parseLockboxApiResponse({ api.getContracts(limit, cursor) }, ContractPageDto::toDomain)

    override suspend fun createContract(request: ContractDraft): Response<Contract> =
        parseLockboxApiResponse({ api.createContract(request.toDto()) }, ContractDto::toDomain)

    override suspend fun getContract(id: String): Response<Contract> =
        parseLockboxApiResponse({ api.getContract(id) }, ContractDto::toDomain)

    override suspend fun updateContract(
        id: String,
        request: ContractDraft,
    ): Response<Contract> =
        parseLockboxApiResponse({ api.updateContract(id, request.toDto()) }, ContractDto::toDomain)

    override suspend fun deleteContract(id: String): Response<Unit> =
        parseLockboxApiResponse { api.deleteContract(id) }

    override suspend fun getContractPdf(id: String): Response<ContractPdf> =
        parseLockboxApiResponse({ api.getContractPdf(id) }, ContractPdfDto::toDomain)
}
