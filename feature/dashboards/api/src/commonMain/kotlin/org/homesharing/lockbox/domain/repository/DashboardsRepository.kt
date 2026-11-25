package org.homesharing.lockbox.domain.repository

import org.homesharing.lockbox.domain.api.response.Response
import org.homesharing.lockbox.domain.model.landlord_dashboard.LandlordDashboard

interface DashboardsRepository {
    /**
     * Получает данные главного экрана арендодателя (`GET /api/dashboard`):
     * счётчики объектов и открытых приглашений, текущие проживания и ближайшие заезды/выезды.
     * Требует авторизации.
     *
     * @param horizonDays горизонт планирования в днях (1..365, по умолчанию 30)
     * @return [Response] с данными дашборда арендодателя
     */
    suspend fun getLandlordDashboard(
        horizonDays: Int = 30,
    ): Response<LandlordDashboard>
}
