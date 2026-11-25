package org.homesharing.lockbox.data.network.api

import org.homesharing.lockbox.data.network.api.response.ApiResponse
import org.homesharing.lockbox.data.network.dto.dashboard.GuestDashboardDto
import org.homesharing.lockbox.data.network.dto.dashboard.LandlordDashboardDto

internal interface DashboardApi {
    suspend fun getLandlordDashboard(horizonDays: Int?): ApiResponse<LandlordDashboardDto>

    suspend fun getGuestDashboard(): ApiResponse<GuestDashboardDto>
}
