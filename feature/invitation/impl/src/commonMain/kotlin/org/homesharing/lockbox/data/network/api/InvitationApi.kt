package org.homesharing.lockbox.data.network.api

import org.homesharing.lockbox.data.network.api.response.ApiResponse
import org.homesharing.lockbox.data.network.dto.invite.InviteCreateRequestDto
import org.homesharing.lockbox.data.network.dto.invite.InviteDto
import org.homesharing.lockbox.data.network.dto.invite_list.InviteListDto

internal interface InvitationApi {
    /**
     * Получает страницу инвайтов текущего пользователя (`GET /api/invites`).
     * Требует авторизации (Bearer token).
     *
     * Без `flat_id` возвращаются инвайты по всем квартирам. С `flat_id` возвращается
     * полная история по указанной квартире, включая архивные инвайты.
     *
     * @param flatId идентификатор квартиры (uuid); при `null` фильтр не применяется
     * @param limit размер страницы
     * @param cursor курсор следующей страницы из `next_cursor`; при `null` возвращается первая страница
     * @return [ApiResponse] со страницей инвайтов [InviteListDto] (200 OK)
     */
    suspend fun getInvites(
        flatId: String?,
        limit: Int,
        cursor: String?,
    ): ApiResponse<InviteListDto>

    /**
     * Создаёт инвайт для гостя (`POST /api/invites`).
     * Требует авторизации (Bearer token).
     *
     * Квартира должна принадлежать вызывающему лендлорду и быть активной. Сервер
     * генерирует access token, ставит `status = sent`, `email_status = pending`,
     * `terms_version = 1`, `expires_at = now + 30 дней` и асинхронно отправляет письмо гостю.
     *
     * @param request данные создаваемого инвайта [InviteCreateRequestDto]
     * @return [ApiResponse] с созданным инвайтом [InviteDto] (201 Created)
     */
    suspend fun createInvite(
        request: InviteCreateRequestDto,
    ): ApiResponse<InviteDto>
}
