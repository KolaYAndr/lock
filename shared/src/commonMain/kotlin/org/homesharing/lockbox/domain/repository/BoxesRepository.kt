package org.homesharing.lockbox.domain.repository

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import org.homesharing.lockbox.data.network.dto.BoxDto
import org.homesharing.lockbox.data.network.dto.BoxResponseDto
import org.homesharing.lockbox.data.network.dto.OpenBoxResponseDto
import org.homesharing.lockbox.data.network.dto.update_box.UpdateBoxResponseDto

interface BoxesRepository {
    fun getBoxesPagingFlow(): Flow<PagingData<BoxDto>>
    suspend fun getBoxByMac(address: String): BoxResponseDto
    suspend fun openBoxByMac(address: String): OpenBoxResponseDto
    suspend fun unbindBox(address: String): UpdateBoxResponseDto
    suspend fun updateBoxName(macAddress: String, newName: String): UpdateBoxResponseDto
    suspend fun updateBoxAddress(macAddress: String, newAddress: String): UpdateBoxResponseDto
}