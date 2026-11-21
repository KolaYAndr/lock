package org.homesharing.lockbox.data.network.api

import org.homesharing.lockbox.data.network.api.response.ApiResponse
import org.homesharing.lockbox.data.network.dto.create_flat.CreateFlatRequestDto
import org.homesharing.lockbox.data.network.dto.flat_detail.FlatDetailDto
import org.homesharing.lockbox.data.network.dto.flat_list.FlatListDto

internal interface FlatApi {
    /**
     * Получает страницу списка квартир текущего пользователя (`GET /flats`).
     * Требует авторизации (Bearer token).
     *
     * @param limit размер страницы
     * @param cursor курсор следующей страницы; при `null` возвращается первая страница
     * @return [ApiResponse] со страницей квартир [FlatListDto] (200 OK)
     */
    suspend fun getFlats(
        limit: Int,
        cursor: String?,
    ): ApiResponse<FlatListDto>

    /**
     * Получает детальную проекцию квартиры (`GET /flats/{id}`).
     * Требует авторизации (Bearer token).
     *
     * @param id идентификатор квартиры (uuid)
     * @param includeInvites при `true` в ответ добавляются инвайты (`include=invites`, максимум 5)
     * @return [ApiResponse] с детальной информацией о квартире [FlatDetailDto] (200 OK)
     */
    suspend fun getFlat(
        id: String,
        includeInvites: Boolean = false,
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
     * Частично обновляет квартиру текущего пользователя (`PATCH /flats/{id}`).
     * Требует авторизации (Bearer token).
     *
     * @param id идентификатор квартиры (uuid)
     * @param request объект [CreateFlatRequestDto] с изменяемыми полями квартиры
     * @return [ApiResponse] с детальной проекцией обновлённой квартиры [FlatDetailDto] (200 OK)
     */
    suspend fun updateFlat(
        id: String,
        request: CreateFlatRequestDto,
    ): ApiResponse<FlatDetailDto>
}
