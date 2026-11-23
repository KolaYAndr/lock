package org.homesharing.lockbox.data.network.api

import org.homesharing.lockbox.data.network.api.response.ApiResponse
import org.homesharing.lockbox.data.network.dto.create_flat.CreateFlatRequestDto
import org.homesharing.lockbox.data.network.dto.flat_detail.FlatDetailDto
import org.homesharing.lockbox.data.network.dto.flat_detail.FlatIncludeDto
import org.homesharing.lockbox.data.network.dto.flat_document.FlatDocumentListDto
import org.homesharing.lockbox.data.network.dto.flat_list.FlatStatusFilterDto
import org.homesharing.lockbox.data.network.dto.flat_list.FlatSummaryListDto
import org.homesharing.lockbox.data.network.dto.flat_photo.FlatPhotoListDto
import org.homesharing.lockbox.data.network.dto.flat_photo.ReorderPhotosRequestDto

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
     * Получает фотографии квартиры в порядке отображения (`GET /flats/{id}/photos`).
     * Требует авторизации (Bearer token). Доступно и для архивных квартир.
     *
     * @param id идентификатор квартиры (uuid)
     * @return [ApiResponse] со списком фотографий [FlatPhotoListDto] (200 OK)
     */
    suspend fun getFlatPhotos(
        id: String,
    ): ApiResponse<FlatPhotoListDto>

    /**
     * Загружает одно фото квартиры (`POST /flats/{id}/photos`).
     * Требует авторизации (Bearer token).
     *
     * Тело запроса — `multipart/form-data`, файл в поле `photo` (до 10 MiB).
     * Тип изображения определяется сервером по magic bytes: jpeg/png/webp/avif/heic.
     *
     * @param id идентификатор квартиры (uuid)
     * @param data содержимое изображения
     * @param fileName имя загружаемого файла
     * @param contentType MIME-тип файла
     * @return [ApiResponse] с загруженным фото [FlatPhotoListDto.FlatPhotoDto] (201 Created)
     */
    suspend fun uploadFlatPhoto(
        id: String,
        data: ByteArray,
        fileName: String,
        contentType: String,
    ): ApiResponse<FlatPhotoListDto.FlatPhotoDto>

    /**
     * Задаёт новый порядок фотографий квартиры (`PUT /flats/{id}/photos/order`).
     * Требует авторизации (Bearer token).
     *
     * Тело запроса — `application/json` с обязательным массивом `photo_ids`.
     * Список должен быть перестановкой всех фото квартиры без дублей.
     *
     * @param id идентификатор квартиры (uuid)
     * @param request объект [ReorderPhotosRequestDto] с новым порядком фотографий
     * @return [ApiResponse] со списком фотографий в новом порядке [FlatPhotoListDto] (200 OK)
     */
    suspend fun reorderFlatPhotos(
        id: String,
        request: ReorderPhotosRequestDto,
    ): ApiResponse<FlatPhotoListDto>

    /**
     * Удаляет фото квартиры (`DELETE /flats/{id}/photos/{photoId}`).
     * Требует авторизации (Bearer token).
     *
     * @param id идентификатор квартиры (uuid)
     * @param photoId идентификатор фотографии (uuid)
     * @return [ApiResponse] без тела ответа (204 No Content)
     */
    suspend fun deleteFlatPhoto(
        id: String,
        photoId: String,
    ): ApiResponse<Unit>

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

    /**
     * Мягко архивирует квартиру текущего пользователя (`POST /flats/{id}/archive`).
     * Требует авторизации (Bearer token).
     *
     * Тело запроса отсутствует. Операция идемпотентна: повторная архивация успешна.
     *
     * @param id идентификатор квартиры (uuid)
     * @return [ApiResponse] с детальной проекцией архивной квартиры [FlatDetailDto] (200 OK)
     */
    suspend fun archiveFlat(
        id: String,
    ): ApiResponse<FlatDetailDto>

    /**
     * Возвращает квартиру текущего пользователя из архива (`POST /flats/{id}/restore`).
     * Требует авторизации (Bearer token).
     *
     * Тело запроса отсутствует.
     *
     * @param id идентификатор квартиры (uuid)
     * @return [ApiResponse] с детальной проекцией восстановленной квартиры [FlatDetailDto] (200 OK)
     */
    suspend fun restoreFlat(
        id: String,
    ): ApiResponse<FlatDetailDto>

    /**
     * Получает документы права собственности квартиры (`GET /flats/{id}/documents`).
     * Требует авторизации (Bearer token). Документы видны только владельцу:
     * для чужой квартиры сервер возвращает `404 Not Found`.
     *
     * Для каждого документа возвращается presigned URL с TTL 3600 секунд;
     * значение может быть пустой строкой.
     *
     * @param id идентификатор квартиры (uuid)
     * @return [ApiResponse] со списком документов [FlatDocumentListDto] (200 OK)
     */
    suspend fun getFlatDocuments(
        id: String,
    ): ApiResponse<FlatDocumentListDto>
}
