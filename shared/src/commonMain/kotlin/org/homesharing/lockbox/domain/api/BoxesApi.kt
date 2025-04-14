package org.homesharing.lockbox.domain.api

import org.homesharing.lockbox.data.network.api.ApiResponse
import org.homesharing.lockbox.data.network.dto.box_list.BoxListResponseDto
import org.homesharing.lockbox.data.network.dto.box.BoxResponseDto
import org.homesharing.lockbox.data.network.dto.box_status.BoxStatusResponseDto
import org.homesharing.lockbox.data.network.dto.open_box.OpenBoxResponseDto
import org.homesharing.lockbox.data.network.dto.update_box.UpdateBoxResponseDto

internal interface BoxesApi {

    suspend fun getBoxes(
        accessToken: String,
        page: Int,
        size: Int
    ): ApiResponse<BoxListResponseDto>

    suspend fun getBoxByMac(
        accessToken: String,
        address: String
    ): ApiResponse<BoxResponseDto>

    suspend fun getBoxStatusByMac(
        accessToken: String,
        address: String
    ): ApiResponse<BoxStatusResponseDto>

    suspend fun updateBoxByMac(
        accessToken: String,
        macAddress: String,
        alias: String? = null,
        address: String? = null
    ): ApiResponse<UpdateBoxResponseDto>

    suspend fun openBox(
        accessToken: String,
        mac: String
    ): ApiResponse<OpenBoxResponseDto>

    suspend fun deleteBox(
        accessToken: String,
        mac: String
    ): ApiResponse<UpdateBoxResponseDto>
}
