package org.homesharing.lockbox.domain.api

import org.homesharing.lockbox.data.network.model.BoxListResponse
import org.homesharing.lockbox.data.network.model.BoxResponse
import org.homesharing.lockbox.data.network.model.BoxStatusResponse

interface BoxesApi {
    suspend fun createCode()
    suspend fun getBoxes(page: Int, size: Int): BoxListResponse
    suspend fun getBoxByMac(address: String): BoxResponse
    suspend fun createShareToken()
    suspend fun getBoxStatusByMac(address: String): BoxStatusResponse
    suspend fun updateBoxByMac(address: String): BoxResponse
    suspend fun openBox(mac: String)
}