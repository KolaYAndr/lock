package org.homesharing.lockbox.data.network.api

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.url
import kotlinx.serialization.json.Json
import org.homesharing.lockbox.data.network.api.response.ApiResponse
import org.homesharing.lockbox.data.network.api.response.toApiResponse
import org.homesharing.lockbox.data.network.client.GO_BASE_ENDPOINT
import org.homesharing.lockbox.data.network.dto.dashboard.GuestDashboardDto
import org.homesharing.lockbox.data.network.dto.dashboard.LandlordDashboardDto

internal class DashboardApiImpl(
    private val client: HttpClient,
    private val json: Json,
) : DashboardApi {
    override suspend fun getLandlordDashboard(horizonDays: Int?): ApiResponse<LandlordDashboardDto> =
        client.get {
            url("$GO_BASE_ENDPOINT/dashboard")
            parameter("horizon_days", horizonDays)
        }.toApiResponse(json)

    override suspend fun getGuestDashboard(): ApiResponse<GuestDashboardDto> =
        client.get {
            url("$GO_BASE_ENDPOINT/guest/dashboard")
        }.toApiResponse(json)
}
