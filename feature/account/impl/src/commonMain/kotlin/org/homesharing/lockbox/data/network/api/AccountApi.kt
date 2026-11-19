package org.homesharing.lockbox.data.network.api

import org.homesharing.lockbox.data.network.api.response.ApiResponse
import org.homesharing.lockbox.data.network.dto.avatar.AvatarDto
import org.homesharing.lockbox.data.network.dto.profile_info.ProfileInfoDto
import org.homesharing.lockbox.data.network.dto.profile_info.ProfileInfoRequestDto
import org.homesharing.lockbox.data.network.dto.role.UserRoleDto
import org.homesharing.lockbox.data.network.dto.update_account.UpdateAccountDto

internal interface AccountApi {
    suspend fun getAvatar(): ApiResponse<AvatarDto>

    suspend fun sendImage(
        data: ByteArray
    ): ApiResponse<Unit>

    suspend fun deactivateUser(): ApiResponse<UpdateAccountDto>

    /**
     * Получает профиль текущего пользователя (`GET /api/profile-info`).
     * Требует авторизации (Bearer token).
     *
     * Возвращает персональные данные, контактную и правовую информацию,
     * активную и доступные роли, статус заполненности личности и список
     * незаполненных полей.
     *
     * @return [ApiResponse] с полным профилем пользователя [ProfileInfoDto] (200 OK)
     */
    suspend fun getProfileInfo(): ApiResponse<ProfileInfoDto>

    /**
     * Создаёт или частично обновляет профиль текущего пользователя (`POST /api/profile-info`).
     * Отсутствующие в запросе поля сохраняют текущие значения, обязательных полей нет.
     * Требует авторизации (Bearer token).
     *
     * Поддерживаемые поля: `first_name`, `last_name`, `middle_name` (до 100 символов),
     * `contact_info` (`email`, `phone_number`) и `legal_info` (`living_address`,
     * `birth_date`, `passport` с `passport_series`, `passport_number`, `issuer`,
     * `issued_at`, `division_code`).
     *
     * @param request объект [ProfileInfoRequestDto] с изменяемыми полями профиля
     * @return [ApiResponse] с полным профилем пользователя [ProfileInfoDto] (201 Created)
     */
    suspend fun updateProfileInfo(
        request: ProfileInfoRequestDto
    ): ApiResponse<ProfileInfoDto>

    /**
     * Активирует роль арендодателя (landlord) для текущего пользователя.
     * Требует авторизации (Bearer token).
     *
     * @return [ApiResponse] с полным объектом профиля пользователя [ProfileInfoDto] (200 OK)
     */
    suspend fun becomeLandlord(): ApiResponse<ProfileInfoDto>

    /**
     * Обновляет активную роль текущего пользователя.
     * Требует авторизации (Bearer token).
     *
     * @param role новая активная роль пользователя [UserRoleDto]
     * @return [ApiResponse] с полным объектом профиля пользователя [ProfileInfoDto] (200 OK)
     */
    suspend fun updateActiveRole(
        role: UserRoleDto
    ): ApiResponse<ProfileInfoDto>
}
