package org.homesharing.lockbox.data.network.api

import org.homesharing.lockbox.data.network.api.response.ApiResponse
import org.homesharing.lockbox.data.network.dto.create_flat.CreateFlatRequestDto
import org.homesharing.lockbox.data.network.dto.flat_detail.FlatDetailDto
import org.homesharing.lockbox.data.network.dto.flat_detail.FlatIncludeDto
import org.homesharing.lockbox.data.network.dto.flat_list.FlatStatusFilterDto
import org.homesharing.lockbox.data.network.dto.flat_list.FlatSummaryListDto

internal interface FlatApi {
    /**
     * Получает страницу списка квартир текущего пользователя (`GET /flats`).
     * Требует авторизации (Bearer token).
     *
     * По умолчанию возвращаются только активные квартиры. Значения `limit` вне
     * диапазона 1..100 клампятся сервером, невалидный `status` возвращает `400 Bad Request`.
     *
     * @param limit размер страницы (1..100, по умолчанию 10)
     * @param cursor курсор следующей страницы из `next_cursor`; при `null` возвращается первая страница
     * @param status фильтр по статусу квартир [FlatStatusFilterDto] (по умолчанию `active`)
     * @return [ApiResponse] со страницей квартир [FlatSummaryListDto] (200 OK)
     */
    suspend fun getFlats(
        limit: Int,
        cursor: String?,
        status: FlatStatusFilterDto,
    ): ApiResponse<FlatSummaryListDto>

    /**
     * Получает детальную проекцию квартиры (`GET /flats/{id}`).
     * Требует авторизации (Bearer token). Архивные квартиры доступны для чтения.
     *
     * Секции из `include` передаются повторяющимися query-параметрами.
     * Без `include` поле `invites` в ответе отсутствует; с `include=invites`
     * возвращаются первые 5 инвайтов (или пустой список).
     *
     * @param id идентификатор квартиры (uuid)
     *  @param include при `true` добавляет первые 5 инвайтов,
     *  без него поле `invites` отсутствует (например, `invites`)
     * @return [ApiResponse] с детальной информацией о квартире [FlatDetailDto] (200 OK)
     */
    suspend fun getFlat(
        id: String,
        include: FlatIncludeDto?,
    ): ApiResponse<FlatDetailDto>

    /**
     * Создаёт квартиру текущего пользователя (`POST /flats`).
     * Требует авторизации (Bearer token).
     *
     * @param request объект [CreateFlatRequestDto] с адресом и необязательными полями квартиры
     * @return [ApiResponse] с детальной проекцией созданной квартиры [FlatDetailDto] (201 Created)
     */
    suspend fun createFlat(
        request: CreateFlatRequestDto,
    ): ApiResponse<FlatDetailDto>

    /**
     * Обновляет квартиру текущего пользователя (`PATCH /flats/{id}`).
     * Требует авторизации (Bearer token).
     *
     * Переданные поля заменяются. `address` обязателен; если `rooms` не передан,
     * список комнат очищается.
     *
     * @param id идентификатор квартиры (uuid)
     * @param request объект [CreateFlatRequestDto] с новыми полями квартиры
     * @return [ApiResponse] с детальной проекцией обновлённой квартиры [FlatDetailDto] (200 OK)
     */
    suspend fun updateFlat(
        id: String,
        request: CreateFlatRequestDto,
    ): ApiResponse<FlatDetailDto>
}
