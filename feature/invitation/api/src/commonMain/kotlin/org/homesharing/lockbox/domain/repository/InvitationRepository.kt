package org.homesharing.lockbox.domain.repository

import org.homesharing.lockbox.domain.api.response.Response
import org.homesharing.lockbox.domain.model.invite_list.InviteList

interface InvitationRepository {
    /**
     * Получает страницу инвайтов текущего пользователя (`GET /api/invites`).
     * Требует авторизации.
     *
     * @param flatId при `null` возвращаются инвайты по всем квартирам; иначе —
     * полная история по указанной квартире, включая архивные инвайты
     * @param limit размер страницы
     * @param cursor курсор следующей страницы; при `null` возвращается первая страница
     * @return [Response] со страницей инвайтов
     */
    suspend fun getInvites(
        flatId: String?,
        limit: Int,
        cursor: String?,
    ): Response<InviteList>
}
