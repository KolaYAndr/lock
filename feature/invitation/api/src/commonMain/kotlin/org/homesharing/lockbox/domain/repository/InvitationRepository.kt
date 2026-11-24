package org.homesharing.lockbox.domain.repository

import org.homesharing.lockbox.domain.api.response.Response
import org.homesharing.lockbox.domain.model.guest_invite.GuestInvite
import org.homesharing.lockbox.domain.model.invite.Invite
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

    /**
     * Получает полные детали инвайта с журналом событий (`GET /api/invites/{id}`).
     * Требует авторизации. Доступно только владельцу инвайта.
     *
     * @param id идентификатор инвайта (uuid)
     * @return [Response] с полными деталями инвайта
     */
    suspend fun getInvite(
        id: String,
    ): Response<Invite>

    /**
     * Получает гостевую проекцию инвайта по токену (`GET /api/invites/by-token/{token}`).
     * Публичный эндпоинт (не требует авторизации).
     *
     * Гостевой лендинг по ссылке из письма. Фиксирует первое открытие (`sent` → `opened`,
     * либо `expired`), возвращает гостевую проекцию без id инвайта, лендлорда и документов;
     * `settings.intercom_code` вычищается.
     *
     * @param token токен инвайта (string, opaque)
     * @return [Response] с гостевой проекцией инвайта
     */
    suspend fun getInviteByToken(
        token: String,
    ): Response<GuestInvite>

    /**
     * Полностью заменяет условия открытого инвайта (`PUT /api/invites/{id}`).
     * Требует авторизации. Доступно только владельцу инвайта.
     *
     * Email гостя неизменяем. `totalPrice` и `remainingPrice` из [checkIn]
     * не отправляются: сервер считает итоговую стоимость сам.
     *
     * @param id идентификатор инвайта (uuid)
     * @param flatId идентификатор активной квартиры лендлорда (uuid)
     * @param guest контактные данные гостя
     * @param checkIn новые детали планируемого заезда
     * @param settings настройки заселения; при `null` сервер использует zero-value
     * @return [Response] с обновлённым инвайтом
     */
    suspend fun updateInvite(
        id: String,
        flatId: String,
        guest: Invite.Guest,
        checkIn: Invite.CheckInDetails,
        settings: Invite.CheckInSettings? = null,
    ): Response<Invite>

    /**
     * Отменяет инвайт (`DELETE /api/invites/{id}`).
     * Требует авторизации. Доступно только владельцу инвайта.
     *
     * Устаревший мягкий алиас на cancel: запись и журнал событий сохраняются,
     * сервер делегирует вызов CancelInvite.
     *
     * @param id идентификатор инвайта (uuid)
     * @return [Response] без тела ответа
     */
    suspend fun deleteInvite(
        id: String,
    ): Response<Unit>

    /**
     * Повторно отправляет инвайт гостю (`POST /api/invites/{id}/resend`).
     * Требует авторизации.
     *
     * Повторно ставит письмо в очередь для инвайта, ждущего решения: `email_status = pending`,
     * `expires_at` продлевается на 30 дней, в журнал добавляется событие `resent`.
     *
     * @param id идентификатор инвайта (uuid)
     * @return [Response] с обновлённым инвайтом
     */
    suspend fun resendInvite(
        id: String,
    ): Response<Invite>

    /**
     * Отзывает инвайт (`POST /api/invites/{id}/cancel`).
     * Требует авторизации. Доступно только владельцу инвайта.
     *
     * Явный отзыв: `status = revoked`, гостевая ссылка перестаёт работать,
     * запись и журнал событий остаются.
     *
     * @param id идентификатор инвайта (uuid)
     * @return [Response] с отозванным инвайтом
     */
    suspend fun cancelInvite(
        id: String,
    ): Response<Invite>

    /**
     * Создаёт инвайт для гостя (`POST /api/invites`).
     * Требует авторизации.
     *
     * Квартира должна принадлежать текущему лендлорду и быть активной.
     * `totalPrice` и `remainingPrice` из [checkIn] при создании не отправляются:
     * сервер считает итоговую стоимость сам.
     *
     * @param flatId идентификатор активной квартиры лендлорда (uuid)
     * @param guest контактные данные гостя
     * @param checkIn детали планируемого заезда
     * @param settings настройки заселения; при `null` сервер использует zero-value
     * @return [Response] с созданным инвайтом
     */
    suspend fun createInvite(
        flatId: String,
        guest: Invite.Guest,
        checkIn: Invite.CheckInDetails,
        settings: Invite.CheckInSettings? = null,
    ): Response<Invite>
}
