package org.homesharing.lockbox.data.network.api

import org.homesharing.lockbox.data.network.api.response.ApiResponse
import org.homesharing.lockbox.data.network.dto.contract_list.ContractListDto
import org.homesharing.lockbox.data.network.dto.flat.FlatDto
import org.homesharing.lockbox.data.network.dto.flat_list.FlatListDto

internal interface DocumentsApi {
    suspend fun getContracts(
        cursor: String?,
        limit: Int,
    ): ApiResponse<ContractListDto>

    suspend fun getFlats(
        cursor: String,
        limit: Int,
    ): ApiResponse<FlatListDto>

    suspend fun createFlat(): ApiResponse<FlatDto>
}