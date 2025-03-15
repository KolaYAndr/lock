package org.homesharing.lockbox.domain.api

import org.homesharing.lockbox.data.network.api.ApiResponse
import org.homesharing.lockbox.data.network.dto.box_list.BoxListResponseDto
import org.homesharing.lockbox.data.network.dto.box.BoxResponseDto
import org.homesharing.lockbox.data.network.dto.box_status.BoxStatusResponseDto
import org.homesharing.lockbox.data.network.dto.open_box.OpenBoxResponseDto
import org.homesharing.lockbox.data.network.dto.update_box.UpdateBoxRequestDto
import org.homesharing.lockbox.data.network.dto.update_box.UpdateBoxResponseDto

interface BoxesApi {

    suspend fun getBoxes(page: Int, size: Int): ApiResponse<BoxListResponseDto>

    suspend fun getBoxByMac(address: String): ApiResponse<BoxResponseDto>

    suspend fun getBoxStatusByMac(address: String): ApiResponse<BoxStatusResponseDto>

    suspend fun updateBoxByMac(
        address: String,
        request: UpdateBoxRequestDto
    ): ApiResponse<UpdateBoxResponseDto>

    suspend fun openBox(mac: String): ApiResponse<OpenBoxResponseDto>

    suspend fun deleteBox(mac: String): ApiResponse<UpdateBoxResponseDto>
}
