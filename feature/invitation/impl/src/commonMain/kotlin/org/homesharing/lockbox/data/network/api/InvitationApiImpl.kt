package org.homesharing.lockbox.data.network.api

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.url
import kotlinx.serialization.json.Json
import org.homesharing.lockbox.data.network.api.response.ApiResponse
import org.homesharing.lockbox.data.network.api.response.toApiResponse
import org.homesharing.lockbox.data.network.client.GO_BASE_ENDPOINT
import org.homesharing.lockbox.data.network.dto.invite_list.InviteListDto

// API endpoints
private const val INVITES = "invites"

// API arguments
private const val FLAT_ID = "flat_id"
private const val LIMIT = "limit"
private const val CURSOR = "cursor"

// API url
private const val INVITES_URL = "$GO_BASE_ENDPOINT/$INVITES"

internal class InvitationApiImpl(
    private val client: HttpClient,
    private val json: Json,
) : InvitationApi {
    override suspend fun getInvites(
        flatId: String?,
        limit: Int,
        cursor: String?,
    ): ApiResponse<InviteListDto> {
        return client.get {
            url(INVITES_URL)
            flatId?.let { parameter(FLAT_ID, it) }
            parameter(LIMIT, limit)
            cursor?.let { parameter(CURSOR, it) }
        }.toApiResponse<InviteListDto>(json)
    }
}
