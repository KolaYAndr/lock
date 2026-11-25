package org.homesharing.lockbox.data.network.api

import org.homesharing.lockbox.data.network.api.response.ApiResponse
import org.homesharing.lockbox.data.network.dto.contract.ContractDto
import org.homesharing.lockbox.data.network.dto.contract.ContractDraftDto
import org.homesharing.lockbox.data.network.dto.contract.ContractPageDto
import org.homesharing.lockbox.data.network.dto.contract.ContractPdfDto

internal interface ContractApi {
    suspend fun getContracts(
        limit: Int,
        cursor: String?,
    ): ApiResponse<ContractPageDto>

    suspend fun createContract(request: ContractDraftDto): ApiResponse<ContractDto>

    suspend fun getContract(id: String): ApiResponse<ContractDto>

    suspend fun updateContract(
        id: String,
        request: ContractDraftDto,
    ): ApiResponse<ContractDto>

    suspend fun deleteContract(id: String): ApiResponse<Unit>

    suspend fun getContractPdf(id: String): ApiResponse<ContractPdfDto>
}
