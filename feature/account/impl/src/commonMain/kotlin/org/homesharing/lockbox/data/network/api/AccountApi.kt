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
     * Получает информацию о профиле текущего пользователя.
     * Требует авторизации (Bearer token).
     *
     * @return [ApiResponse] с данными профиля пользователя [ProfileInfoDto]
     */
    suspend fun getProfileInfo(): ApiResponse<ProfileInfoDto>

    /**
     * Создает или обновляет данные профиля текущего пользователя.
     * Требует авторизации (Bearer token).
     *
     * @param request объект [ProfileInfoRequestDto] с данными профиля
     * @return [ApiResponse] с полным объектом профиля пользователя [ProfileInfoDto] (201 Created)
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
