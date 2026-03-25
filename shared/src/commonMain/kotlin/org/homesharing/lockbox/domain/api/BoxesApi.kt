package org.homesharing.lockbox.domain.api

import org.homesharing.lockbox.data.network.api.response.ApiResponse

internal interface BoxesApi {
    suspend fun getBoxes(
        page: Int,
        size: Int,
    ): ApiResponse

    suspend fun getBoxByMac(
        macAddress: String,
    ): ApiResponse

    suspend fun updateBoxByMac(
        macAddress: String,
        ownerSub: String,
        alias: String? = null,
        address: String? = null,
    ): ApiResponse

    suspend fun openBox(
        macAddress: String,
    ): ApiResponse

    suspend fun deleteBox(
        macAddress: String
    ): ApiResponse

    suspend fun getAccessedList(
        macAddress: String,
    ): ApiResponse

    suspend fun createAccess(
        macAddress: String,
        alias: String,
        startTime: String,
        endTime: String,
    ): ApiResponse

    suspend fun revokeAccess(
        macAddress: String,
        tokenId: String,
    ): ApiResponse

    suspend fun createLinkingCode(): ApiResponse

    suspend fun physicalSignUp(
        code: Int,
        macAddress: String,
    ): ApiResponse

    suspend fun createTestDevice(): ApiResponse
}
