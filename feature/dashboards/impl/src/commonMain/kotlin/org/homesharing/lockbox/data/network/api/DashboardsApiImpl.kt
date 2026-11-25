package org.homesharing.lockbox.data.network.api

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.url
import kotlinx.serialization.json.Json
import org.homesharing.lockbox.data.network.api.response.ApiResponse
import org.homesharing.lockbox.data.network.api.response.toApiResponse
import org.homesharing.lockbox.data.network.client.GO_BASE_ENDPOINT
import org.homesharing.lockbox.data.network.dto.landlord_dashboard.LandlordDashboardDto

// API endpoints
private const val DASHBOARD = "dashboard"

// API arguments
private const val HORIZON_DAYS = "horizon_days"

// API url
private const val DASHBOARD_URL = "$GO_BASE_ENDPOINT/$DASHBOARD"

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
}
