package org.homesharing.lockbox.data.network.api

import org.homesharing.lockbox.data.network.api.response.ApiResponse
import org.homesharing.lockbox.data.network.dto.contact_file.ContractFileDto
import org.homesharing.lockbox.data.network.dto.contract_detailed.ContractDetailedDto
import org.homesharing.lockbox.data.network.dto.contract_list.ContractListDto
import org.homesharing.lockbox.data.network.dto.flat.FlatDto
import org.homesharing.lockbox.data.network.dto.flat_list.FlatListDto

internal interface DocumentsApi {
    suspend fun getContracts(
        cursor: String?,
        limit: Int,
    ): ApiResponse<ContractListDto>

    suspend fun deleteContract(
        id: String
    ): ApiResponse<Unit>

    suspend fun getContract(
        id: String
    ): ApiResponse<ContractDetailedDto>

    suspend fun getContractFile(
        id: String
    ): ApiResponse<ContractFileDto>

    suspend fun getFlats(
        cursor: String,
        limit: Int,
    ): ApiResponse<FlatListDto>

    suspend fun createFlat(): ApiResponse<FlatDto>
}