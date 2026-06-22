package org.homesharing.lockbox.data.network.api

import org.homesharing.lockbox.data.network.api.response.ApiResponse
import org.homesharing.lockbox.data.network.dto.create_flat.CreateFlatRequestDto
import org.homesharing.lockbox.data.network.dto.flat.FlatDto
import org.homesharing.lockbox.data.network.dto.flat_list.FlatListDto

internal interface FlatApi {
    suspend fun getFlats(
        limit: Int,
        cursor: String?,
    ): ApiResponse<FlatListDto>

    suspend fun getFlat(
        id: String,
        includeInvites: Boolean = false,
    ): ApiResponse<FlatDto>

    suspend fun createFlat(
        request: CreateFlatRequestDto,
    ): ApiResponse<FlatDto>

    suspend fun updateFlat(
        id: String,
        request: CreateFlatRequestDto,
    ): ApiResponse<FlatDto>
}