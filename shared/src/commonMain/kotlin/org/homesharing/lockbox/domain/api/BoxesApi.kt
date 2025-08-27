package org.homesharing.lockbox.domain.api

import org.homesharing.lockbox.data.network.api.response.ApiResponse

internal interface BoxesApi {

    suspend fun getBoxes(
        accessToken: String,
        page: Int,
        size: Int
    ): ApiResponse

    suspend fun getBoxByMac(
        accessToken: String,
        macAddress: String
    ): ApiResponse

    suspend fun getBoxStatusByMac(
        accessToken: String,
        macAddress: String
    ): ApiResponse

    suspend fun updateBoxByMac(
        accessToken: String,
        macAddress: String,
        alias: String? = null,
        address: String? = null
    ): ApiResponse

    suspend fun openBox(
        accessToken: String,
        macAddress: String
    ): ApiResponse

    suspend fun deleteBox(
        accessToken: String,
        macAddress: String
    ): ApiResponse

    suspend fun getAccessedList(
        accessToken: String,
        macAddress: String
    ): ApiResponse

    suspend fun createAccess(
        accessToken: String,
        macAddress: String,
        alias: String?,
        startTime: String,
        endTime: String,
    ): ApiResponse

    suspend fun revokeAccess(
        accessToken: String,
        macAddress: String,
        tokenId: String
    ): ApiResponse

    suspend fun createLinkingCode(
        accessToken: String,
    ): ApiResponse

    suspend fun physicalSignUp(
        accessToken: String,
        code: Int
    ): ApiResponse
}
