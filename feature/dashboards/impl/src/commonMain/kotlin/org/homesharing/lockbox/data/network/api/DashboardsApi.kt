package org.homesharing.lockbox.data.network.api

import org.homesharing.lockbox.data.network.api.response.ApiResponse
import org.homesharing.lockbox.data.network.dto.guest_dashboard.GuestDashboardDto
import org.homesharing.lockbox.data.network.dto.landlord_dashboard.LandlordDashboardDto

internal interface DashboardsApi {
    /**
     * Получает данные главного экрана арендодателя (`GET /api/dashboard`):
     * счётчики объектов и открытых приглашений, текущие проживания и ближайшие заезды/выезды.
     * Требует авторизации (Bearer token).
     *
     * @param horizonDays горизонт планирования в днях (1..365, по умолчанию 30)
     * @return [ApiResponse] с данными дашборда арендодателя [LandlordDashboardDto] (200 OK)
     */
    suspend fun getLandlordDashboard(
        horizonDays: Int = 30,
    ): ApiResponse<LandlordDashboardDto>

    /**
     * Получает данные главного экрана гостя (`GET /api/guest/dashboard`):
     * состояние текущего или выбранного процесса заселения, следующее действие для UI,
     * данные приглашения, проживания и контракта.
     * Требует авторизации (Bearer token).
     *
     * @param inviteId идентификатор приглашения для выбора процесса (uuid); при `null` выбирается самый приоритетный процесс
     * @return [ApiResponse] с данными дашборда гостя [GuestDashboardDto] (200 OK)
     */
    suspend fun getGuestDashboard(
        inviteId: String? = null,
    ): ApiResponse<GuestDashboardDto>
}
