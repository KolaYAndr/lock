package org.homesharing.lockbox.data.network.api

import org.homesharing.lockbox.data.network.api.response.ApiResponse

internal interface DocumentsApi {
    suspend fun getFlats(
        cursor: String,
        limit: Int,
    ): ApiResponse

    suspend fun createFlat(): ApiResponse
}