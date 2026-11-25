package org.homesharing.lockbox.domain.repository

import kotlinx.coroutines.flow.Flow
import org.homesharing.lockbox.domain.api.response.Response
import org.homesharing.lockbox.domain.model.personal_document.PersonalDocumentList
import org.homesharing.lockbox.domain.model.personal_document.PersonalDocumentType
import org.homesharing.lockbox.domain.model.profile_info.ProfileInfo
import org.homesharing.lockbox.domain.model.role.UserRole

interface AccountRepository {
    /**
     * Выполняет локальный выход из аккаунта, очищая сохранённые токены из хранилища.
     */
    suspend fun logOut()

    /**
     * Поток состояния авторизации пользователя (`true`, если локальные токены присутствуют).
     */
    val loggedIn: Flow<Boolean>
    /**
     * Получает presigned URL на аватар текущего пользователя.
     * Требует авторизации.
     *
     * Ссылка действительна 3600 секунд.
     *
     * @return [Response] с presigned URL аватара
     */
    suspend fun getImageLink(): Response<String>

    /**
     * Загружает аватар текущего пользователя.
     * Требует авторизации.
     *
     * @param data содержимое изображения
     * @return [Response] с сообщением о результате загрузки
     */
    suspend fun sendImage(data: ByteArray): Response<String>

    /**
     * Получает профиль текущего пользователя.
     * Требует авторизации.
     *
     * @return [Response] с полным профилем пользователя
     */
    suspend fun getProfileInfo(): Response<ProfileInfo>

    /**
     * Создаёт или частично обновляет профиль текущего пользователя.
     * Отсутствующие поля сохраняют текущие значения, обязательных полей нет.
     * Требует авторизации.
     *
     * @param firstName имя (до 100 символов)
     * @param lastName фамилия (до 100 символов)
     * @param middleName отчество (до 100 символов)
     * @param phoneNumber номер телефона
     * @param email адрес электронной почты
     * @param livingAddress адрес проживания
     * @param birthDate дата рождения в формате ISO (`yyyy-MM-dd`)
     * @param passportSeries серия паспорта
     * @param passportNumber номер паспорта
     * @param passportIssuer кем выдан паспорт
     * @param passportIssuedAt дата выдачи паспорта в формате ISO (`yyyy-MM-dd`)
     * @param passportDivisionCode код подразделения
     * @return [Response] с полным профилем пользователя
     */
    suspend fun updateProfileInfo(
        firstName: String? = null,
        lastName: String? = null,
        middleName: String? = null,
        phoneNumber: String? = null,
        email: String? = null,
        livingAddress: String? = null,
        birthDate: String? = null,
        passportSeries: String? = null,
        passportNumber: String? = null,
        passportIssuer: String? = null,
        passportIssuedAt: String? = null,
        passportDivisionCode: String? = null,
    ): Response<ProfileInfo>

    /**
     * Получает список документов текущего пользователя.
     * Требует авторизации.
     *
     * Для каждого документа возвращается presigned URL для скачивания с TTL 300 секунд.
     *
     * @return [Response] со списком документов
     */
    suspend fun getPersonalDocuments(): Response<PersonalDocumentList>

    /**
     * Получает документ текущего пользователя по идентификатору.
     * Требует авторизации.
     *
     * @param id идентификатор документа (uuid)
     * @return [Response] с документом; для чужого идентификатора — ошибка (`404`)
     */
    suspend fun getPersonalDocument(id: String): Response<PersonalDocumentList.PersonalDocument>

    /**
     * Загружает документ текущего пользователя.
     * Требует авторизации.
     *
     * @param data содержимое файла (PDF/JPEG/PNG, до 20 MiB)
     * @param fileName имя загружаемого файла
     * @param contentType MIME-тип файла
     * @param docType тип документа; при `null` сервер использует `passport`
     * @return [Response] с загруженным документом
     */
    suspend fun uploadPersonalDocument(
        data: ByteArray,
        fileName: String,
        contentType: String,
        docType: PersonalDocumentType? = null,
    ): Response<PersonalDocumentList.PersonalDocument>

    /**
     * Удаляет документ текущего пользователя.
     * Требует авторизации.
     *
     * @param id идентификатор документа (uuid)
     * @return [Response] без полезной нагрузки
     */
    suspend fun deletePersonalDocument(id: String): Response<Unit>

    /**
     * Выдаёт роль арендодателя (landlord) текущему пользователю и переключает
     * контекст на неё.
     * Требует авторизации.
     *
     * @return [Response] с полным профилем пользователя
     */
    suspend fun becomeLandlord(): Response<ProfileInfo>
    /**
     * Переключает активную роль текущего пользователя.
     * Права и сессии не изменяются.
     * Требует авторизации.
     *
     * @param role новая активная роль пользователя
     * @return [Response] с полным профилем пользователя
     */
    suspend fun updateActiveRole(role: UserRole): Response<ProfileInfo>
}
