package org.homesharing.lockbox.domain.api

import org.homesharing.lockbox.data.network.dto.BoxListResponseDto
import org.homesharing.lockbox.data.network.dto.BoxResponseDto
import org.homesharing.lockbox.data.network.dto.BoxStatusResponseDto
import org.homesharing.lockbox.data.network.dto.OpenBoxResponseDto
import org.homesharing.lockbox.data.network.dto.update_box.UpdateBoxRequestDto
import org.homesharing.lockbox.data.network.dto.update_box.UpdateBoxResponseDto

interface BoxesApi {
    suspend fun getBoxes(page: Int, size: Int): BoxListResponseDto
    suspend fun getBoxByMac(address: String): BoxResponseDto
    suspend fun getBoxStatusByMac(address: String): BoxStatusResponseDto
    suspend fun updateBoxByMac(address: String, request: UpdateBoxRequestDto): UpdateBoxResponseDto
    suspend fun openBox(mac: String): OpenBoxResponseDto
    suspend fun deleteBox(mac: String): UpdateBoxResponseDto
}