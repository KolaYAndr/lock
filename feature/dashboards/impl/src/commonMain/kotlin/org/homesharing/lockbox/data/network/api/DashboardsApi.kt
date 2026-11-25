package org.homesharing.lockbox.data.network.api

import org.homesharing.lockbox.data.network.api.response.ApiResponse
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
}
