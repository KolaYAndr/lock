package org.homesharing.lockbox.data.repository

import org.homesharing.lockbox.data.network.api.DashboardApi
import org.homesharing.lockbox.data.network.dto.dashboard.GuestDashboardDto
import org.homesharing.lockbox.data.network.dto.dashboard.LandlordDashboardDto
import org.homesharing.lockbox.data.network.dto.dashboard.toDomain
import org.homesharing.lockbox.domain.api.response.Response
import org.homesharing.lockbox.domain.api.response.parseLockboxApiResponse
import org.homesharing.lockbox.domain.model.dashboard.GuestDashboard
import org.homesharing.lockbox.domain.model.dashboard.LandlordDashboard
import org.homesharing.lockbox.domain.repository.DashboardRepository

internal class DashboardRepositoryImpl(
    private val api: DashboardApi,
) : DashboardRepository {
    override suspend fun getLandlordDashboard(horizonDays: Int?): Response<LandlordDashboard> =
        parseLockboxApiResponse({ api.getLandlordDashboard(horizonDays) }, LandlordDashboardDto::toDomain)

    override suspend fun getGuestDashboard(): Response<GuestDashboard> =
        parseLockboxApiResponse({ api.getGuestDashboard() }, GuestDashboardDto::toDomain)
}
