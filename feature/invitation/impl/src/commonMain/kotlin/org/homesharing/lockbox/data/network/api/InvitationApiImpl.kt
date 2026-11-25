package org.homesharing.lockbox.data.network.api

import io.ktor.client.HttpClient
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.encodeURLPathPart
import kotlinx.serialization.json.Json
import org.homesharing.lockbox.data.network.api.response.ApiResponse
import org.homesharing.lockbox.data.network.api.response.toApiResponse
import org.homesharing.lockbox.data.network.client.GO_BASE_ENDPOINT
import org.homesharing.lockbox.data.network.dto.invitation.GuestInviteDto
import org.homesharing.lockbox.data.network.dto.invitation.InviteDto
import org.homesharing.lockbox.data.network.dto.invitation.InviteAcceptanceDto
import org.homesharing.lockbox.data.network.dto.invitation.InviteDraftDto
import org.homesharing.lockbox.data.network.dto.invitation.InvitePageDto

private const val INVITES_URL = "$GO_BASE_ENDPOINT/invites"

internal class InvitationApiImpl(
    private val client: HttpClient,
    private val json: Json,
) : InvitationApi {
    override suspend fun getInvites(
        limit: Int,
        cursor: String?,
    ): ApiResponse<InvitePageDto> =
        client.get {
            url("$INVITES_URL")
            parameter("limit", limit)
            parameter("cursor", cursor)
        }.toApiResponse(json)

    override suspend fun createInvite(request: InviteDraftDto): ApiResponse<InviteDto> =
        client.post {
            url("$INVITES_URL")
            setBody(request)
        }.toApiResponse(json)

    override suspend fun getInvite(id: String): ApiResponse<InviteDto> =
        client.get {
            url("$INVITES_URL/$id")
        }.toApiResponse(json)

    override suspend fun updateInvite(
        id: String,
        request: InviteDraftDto,
    ): ApiResponse<InviteDto> =
        client.put {
            url("$INVITES_URL/$id")
            setBody(request)
        }.toApiResponse(json)

    override suspend fun deleteInvite(id: String): ApiResponse<Unit> =
        client.delete {
            url("$INVITES_URL/$id")
        }.toApiResponse(json)

    override suspend fun cancelInvite(id: String): ApiResponse<InviteDto> =
        client.post {
            url("$INVITES_URL/$id/cancel")
        }.toApiResponse(json)

    override suspend fun resendInvite(id: String): ApiResponse<InviteDto> =
        client.post {
            url("$INVITES_URL/$id/resend")
        }.toApiResponse(json)

    override suspend fun getGuestInvite(token: String): ApiResponse<GuestInviteDto> =
        client.get {
            url("$INVITES_URL/by-token/${token.encodeURLPathPart()}")
        }.toApiResponse(json)

    override suspend fun acceptInvite(token: String): ApiResponse<InviteAcceptanceDto> =
        client.post {
            url("$INVITES_URL/by-token/${token.encodeURLPathPart()}/accept")
        }.toApiResponse(json)

    override suspend fun declineInvite(token: String): ApiResponse<GuestInviteDto> =
        client.post {
            url("$INVITES_URL/by-token/${token.encodeURLPathPart()}/decline")
        }.toApiResponse(json)
}
