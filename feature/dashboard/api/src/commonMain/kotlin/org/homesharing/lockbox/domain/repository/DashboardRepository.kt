package org.homesharing.lockbox.domain.repository

import org.homesharing.lockbox.domain.api.response.Response
import org.homesharing.lockbox.domain.model.dashboard.GuestDashboard
import org.homesharing.lockbox.domain.model.dashboard.LandlordDashboard

interface DashboardRepository {
    suspend fun getLandlordDashboard(horizonDays: Int? = null): Response<LandlordDashboard>

    suspend fun getGuestDashboard(): Response<GuestDashboard>
}
