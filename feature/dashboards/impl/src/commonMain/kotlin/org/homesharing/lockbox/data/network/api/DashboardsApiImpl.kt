package org.homesharing.lockbox.data.network.api

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.url
import kotlinx.serialization.json.Json
import org.homesharing.lockbox.data.network.api.response.ApiResponse
import org.homesharing.lockbox.data.network.api.response.toApiResponse
import org.homesharing.lockbox.data.network.client.GO_BASE_ENDPOINT
import org.homesharing.lockbox.data.network.dto.guest_dashboard.GuestDashboardDto
import org.homesharing.lockbox.data.network.dto.guest_process.GuestProcessListDto
import org.homesharing.lockbox.data.network.dto.landlord_dashboard.LandlordDashboardDto

// API endpoints
private const val DASHBOARD = "dashboard"
private const val GUEST = "guest"
private const val PROCESSES = "processes"

// API arguments
private const val HORIZON_DAYS = "horizon_days"
private const val INVITE_ID = "invite_id"
private const val LIMIT = "limit"
private const val CURSOR = "cursor"

// API url
private const val DASHBOARD_URL = "$GO_BASE_ENDPOINT/$DASHBOARD"
private const val GUEST_DASHBOARD_URL = "$GO_BASE_ENDPOINT/$GUEST/$DASHBOARD"
private const val GUEST_PROCESSES_URL = "$GO_BASE_ENDPOINT/$GUEST/$PROCESSES"

internal class DashboardsApiImpl(
    private val client: HttpClient,
    private val json: Json,
) : DashboardsApi {
    override suspend fun getLandlordDashboard(
        horizonDays: Int,
    ): ApiResponse<LandlordDashboardDto> {
        return client.get {
            url(DASHBOARD_URL)
            parameter(HORIZON_DAYS, horizonDays)
        }.toApiResponse<LandlordDashboardDto>(json)
    }

    override suspend fun getGuestDashboard(
        inviteId: String?,
    ): ApiResponse<GuestDashboardDto> {
        return client.get {
            url(GUEST_DASHBOARD_URL)
            inviteId?.let { parameter(INVITE_ID, it) }
        }.toApiResponse<GuestDashboardDto>(json)
    }

    override suspend fun getGuestProcesses(
        limit: Int,
        cursor: String?,
    ): ApiResponse<GuestProcessListDto> {
        return client.get {
            url(GUEST_PROCESSES_URL)
            parameter(LIMIT, limit)
            cursor?.let { parameter(CURSOR, it) }
        }.toApiResponse<GuestProcessListDto>(json)
    }
}
