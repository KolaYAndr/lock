package org.homesharing.lockbox.data.repository

import org.homesharing.lockbox.data.network.api.DashboardsApi
import org.homesharing.lockbox.data.network.dto.guest_dashboard.GuestDashboardDto
import org.homesharing.lockbox.data.network.dto.landlord_dashboard.LandlordDashboardDto
import org.homesharing.lockbox.domain.api.response.Response
import org.homesharing.lockbox.domain.api.response.parseLockboxApiResponse
import org.homesharing.lockbox.domain.model.guest_dashboard.GuestDashboard
import org.homesharing.lockbox.domain.model.guest_dashboard.toDomain
import org.homesharing.lockbox.domain.model.landlord_dashboard.LandlordDashboard
import org.homesharing.lockbox.domain.model.landlord_dashboard.toDomain
import org.homesharing.lockbox.domain.repository.DashboardsRepository

internal class DashboardsRepositoryImpl(
    private val api: DashboardsApi,
) : DashboardsRepository {
    override suspend fun getLandlordDashboard(
        horizonDays: Int,
    ): Response<LandlordDashboard> {
        return parseLockboxApiResponse(
            apiCall = { api.getLandlordDashboard(horizonDays = horizonDays) },
            mapper = LandlordDashboardDto::toDomain,
        )
    }

    override suspend fun getGuestDashboard(
        inviteId: String?,
    ): Response<GuestDashboard> {
        return parseLockboxApiResponse(
            apiCall = { api.getGuestDashboard(inviteId = inviteId) },
            mapper = GuestDashboardDto::toDomain,
        )
    }
}
