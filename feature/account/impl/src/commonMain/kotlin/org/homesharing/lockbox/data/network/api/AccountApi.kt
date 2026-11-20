package org.homesharing.lockbox.data.network.api

import org.homesharing.lockbox.data.network.api.response.ApiResponse
import org.homesharing.lockbox.data.network.dto.avatar.AvatarDto
import org.homesharing.lockbox.data.network.dto.avatar.AvatarUploadResponseDto
import org.homesharing.lockbox.data.network.dto.personal_document.PersonalDocumentListDto
import org.homesharing.lockbox.data.network.dto.personal_document.PersonalDocumentTypeDto
import org.homesharing.lockbox.data.network.dto.profile_info.ProfileInfoDto
import org.homesharing.lockbox.data.network.dto.profile_info.ProfileInfoRequestDto
import org.homesharing.lockbox.data.network.dto.role.UserRoleDto
import org.homesharing.lockbox.data.network.dto.update_account.UpdateAccountDto

internal interface AccountApi {
    suspend fun getAvatar(): ApiResponse<AvatarDto>

    /**
     * Загружает аватар текущего пользователя (`POST /api/avatars/upload`).
     * Требует авторизации (Bearer token).
     *
     * Тело запроса — `multipart/form-data`, файл в поле `avatar`.
     * Тип изображения определяется сервером по magic bytes.
     *
     * @param data содержимое изображения
     * @return [ApiResponse] с результатом загрузки [AvatarUploadResponseDto] (201 Created)
     */
    suspend fun sendImage(
        data: ByteArray
    ): ApiResponse<AvatarUploadResponseDto>

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
     * Получает список документов текущего пользователя (`GET /api/profile-info/documents`).
     * Требует авторизации (Bearer token).
     *
     * Для каждого документа возвращается presigned URL для скачивания с TTL 300 секунд.
     *
     * @return [ApiResponse] со списком документов [PersonalDocumentListDto] (200 OK)
     */
    suspend fun getPersonalDocuments(): ApiResponse<PersonalDocumentListDto>

    /**
     * Получает документ текущего пользователя по идентификатору
     * (`GET /api/profile-info/documents/{id}`).
     * Требует авторизации (Bearer token).
     *
     * Для документа возвращается presigned URL для скачивания с TTL 300 секунд.
     * Чужой идентификатор возвращает `404 Not Found`.
     *
     * @param id идентификатор документа (uuid)
     * @return [ApiResponse] с документом [PersonalDocumentListDto.PersonalDocumentDto] (200 OK)
     */
    suspend fun getPersonalDocument(
        id: String
    ): ApiResponse<PersonalDocumentListDto.PersonalDocumentDto>

    /**
     * Загружает документ текущего пользователя (`POST /api/profile-info/documents`).
     * Требует авторизации (Bearer token).
     *
     * Тело запроса — `multipart/form-data`: файл в поле `document` (PDF/JPEG/PNG, до 20 MiB)
     * и необязательный тип документа в поле `doc_type` (по умолчанию `passport`).
     *
     * @param data содержимое загружаемого файла
     * @param fileName имя загружаемого файла
     * @param contentType MIME-тип файла (`application/pdf`, `image/jpeg`, `image/png`)
     * @param docType тип документа [PersonalDocumentTypeDto]; при `null` сервер использует `passport`
     * @return [ApiResponse] с загруженным документом
     * [PersonalDocumentListDto.PersonalDocumentDto] (201 Created)
     */
    suspend fun uploadPersonalDocument(
        data: ByteArray,
        fileName: String,
        contentType: String,
        docType: PersonalDocumentTypeDto?,
    ): ApiResponse<PersonalDocumentListDto.PersonalDocumentDto>

    /**
     * Удаляет документ текущего пользователя (`DELETE /api/profile-info/documents/{id}`).
     * Требует авторизации (Bearer token).
     *
     * @param id идентификатор документа (uuid)
     * @return [ApiResponse] без тела ответа (204 No Content)
     */
    suspend fun deletePersonalDocument(id: String): ApiResponse<Unit>

    /**
     * Выдаёт роль арендодателя (landlord) текущему пользователю
     * (`POST /api/profile-info/landlord`) и переключает контекст на неё.
     * Тело запроса отсутствует.
     * Требует авторизации (Bearer token).
     *
     * @return [ApiResponse] с полным объектом профиля пользователя [ProfileInfoDto] (200 OK)
     */
    suspend fun becomeLandlord(): ApiResponse<ProfileInfoDto>

    /**
     * Переключает активную роль текущего пользователя (`PUT /api/profile-info/active-role`).
     * Права и сессии не изменяются.
     * Требует авторизации (Bearer token).
     *
     * Тело запроса — `application/json` с обязательным полем `active_role`.
     *
     * @param role новая активная роль пользователя [UserRoleDto]
     * @return [ApiResponse] с полным объектом профиля пользователя [ProfileInfoDto] (200 OK)
     */
    suspend fun updateActiveRole(
        role: UserRoleDto
    ): ApiResponse<ProfileInfoDto>
}
