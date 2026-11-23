package org.homesharing.lockbox.domain.repository

import androidx.paging.ItemSnapshotList
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import org.homesharing.lockbox.domain.api.response.Response
import org.homesharing.lockbox.domain.model.flat.Flat
import org.homesharing.lockbox.domain.model.flat_detail.FlatDetail
import org.homesharing.lockbox.domain.model.flat_document.FlatDocumentList
import org.homesharing.lockbox.domain.model.flat_list.FlatStatusFilter
import org.homesharing.lockbox.domain.model.premise_type.PremiseType
import org.homesharing.lockbox.domain.model.room.RoomInput

interface FlatRepository {
    /**
     * Получает поток страниц списка квартир текущего пользователя (`GET /flats`).
     * Требует авторизации.
     *
     * @param status фильтр по статусу квартир; по умолчанию возвращаются только активные
     * @return [Flow] с [PagingData] квартир
     */
    fun getFlatsFlow(status: FlatStatusFilter = FlatStatusFilter.Active): Flow<PagingData<Flat>>

    /**
     * Получает снимок страницы списка квартир (`GET /flats`).
     * Требует авторизации.
     *
     * @param limit размер страницы
     * @param cursor курсор следующей страницы; при `null` возвращается первая страница
     * @param status фильтр по статусу квартир; по умолчанию возвращаются только активные
     * @return [Flow] со снимком страницы квартир
     */
    suspend fun getFlatsSnapshot(
        limit: Int,
        cursor: String?,
        status: FlatStatusFilter = FlatStatusFilter.Active,
    ): Flow<ItemSnapshotList<Flat>>

    /**
     * Получает детальную проекцию квартиры (`GET /flats/{id}`).
     * Требует авторизации. Архивные квартиры доступны для чтения.
     *
     * @param id идентификатор квартиры (uuid)
     * @param includeInvites при `true` добавляет первые 5 инвайтов,
     * без него поле `invites` отсутствует
     * @return [Response] с детальной информацией о квартире
     */
    suspend fun getFlat(
        id: String,
        includeInvites: Boolean,
    ): Response<FlatDetail>

    /**
     * Получает URL фотографий квартиры в порядке отображения (`GET /flats/{id}/photos`).
     * Требует авторизации. Доступно и для архивных квартир.
     *
     * @param id идентификатор квартиры (uuid)
     * @return [Response] со списком URL фотографий
     */
    suspend fun getFlatPhotos(
        id: String,
    ): Response<List<String>>

    /**
     * Загружает одно фото квартиры (`POST /flats/{id}/photos`).
     * Требует авторизации.
     *
     * Тело — `multipart/form-data`, файл в поле `photo` (до 10 MiB).
     * Тип определяется сервером по magic bytes: jpeg/png/webp/avif/heic.
     *
     * @param id идентификатор квартиры (uuid)
     * @param data содержимое изображения
     * @param fileName имя загружаемого файла
     * @param contentType MIME-тип файла
     * @return [Response] с URL загруженного фото
     */
    suspend fun uploadFlatPhoto(
        id: String,
        data: ByteArray,
        fileName: String,
        contentType: String,
    ): Response<String>

    /**
     * Задаёт новый порядок фотографий квартиры (`PUT /flats/{id}/photos/order`).
     * Требует авторизации.
     *
     * Список `photoIds` должен быть перестановкой всех фото квартиры без дублей.
     *
     * @param id идентификатор квартиры (uuid)
     * @param photoIds новый порядок идентификаторов фотографий
     * @return [Response] со списком URL фотографий в новом порядке
     */
    suspend fun reorderFlatPhotos(
        id: String,
        photoIds: List<String>,
    ): Response<List<String>>

    /**
     * Удаляет фото квартиры (`DELETE /flats/{id}/photos/{photoId}`).
     * Требует авторизации.
     *
     * @param id идентификатор квартиры (uuid)
     * @param photoId идентификатор фотографии (uuid)
     * @return [Response] без тела ответа
     */
    suspend fun deleteFlatPhoto(
        id: String,
        photoId: String,
    ): Response<Unit>

    /**
     * Создаёт квартиру текущего пользователя (`POST /flats`).
     * Требует авторизации.
     *
     * @param address адрес квартиры; обязателен только `city`
     * @param name название; при `null` или пустом значении сервер подставляет `full_address`
     * @param description описание (до 10000 символов)
     * @param premiseType тип помещения; при `null` сервер использует `residential`
     * @param livingArea жилая площадь; отрицательное значение отклоняется сервером (`400`)
     * @param rooms комнаты; `position` по умолчанию — индекс в массиве
     * @return [Response] с детальной проекцией созданной квартиры
     */
    suspend fun createFlat(
        address: FlatDetail.Address,
        name: String? = null,
        description: String? = null,
        premiseType: PremiseType? = null,
        livingArea: Double? = null,
        rooms: List<RoomInput> = emptyList(),
    ): Response<FlatDetail>

    /**
     * Обновляет квартиру текущего пользователя (`PATCH /flats/{id}`).
     * Требует авторизации.
     *
     * Переданные поля заменяются. `address` обязателен; если `rooms` не передан,
     * список комнат очищается.
     *
     * @param id идентификатор квартиры (uuid)
     * @param address адрес квартиры (обязателен)
     * @param name название квартиры
     * @param description описание квартиры
     * @param premiseType тип помещения
     * @param livingArea жилая площадь
     * @param rooms комнаты; при отсутствии список комнат очищается
     * @return [Response] с детальной проекцией обновлённой квартиры
     */
    suspend fun updateFlat(
        id: String,
        address: FlatDetail.Address,
        name: String? = null,
        description: String? = null,
        premiseType: PremiseType? = null,
        livingArea: Double? = null,
        rooms: List<RoomInput> = emptyList(),
    ): Response<FlatDetail>

    /**
     * Мягко архивирует квартиру текущего пользователя (`POST /flats/{id}/archive`).
     * Требует авторизации.
     *
     * Операция идемпотентна: повторная архивация успешна.
     *
     * @param id идентификатор квартиры (uuid)
     * @return [Response] с детальной проекцией архивной квартиры
     */
    suspend fun archiveFlat(
        id: String,
    ): Response<FlatDetail>

    /**
     * Возвращает квартиру текущего пользователя из архива (`POST /flats/{id}/restore`).
     * Требует авторизации.
     *
     * @param id идентификатор квартиры (uuid)
     * @return [Response] с детальной проекцией восстановленной квартиры
     */
    suspend fun restoreFlat(
        id: String,
    ): Response<FlatDetail>

    /**
     * Получает документы права собственности квартиры (`GET /flats/{id}/documents`).
     * Требует авторизации. Документы видны только владельцу.
     *
     * @param id идентификатор квартиры (uuid)
     * @return [Response] со списком документов квартиры
     */
    suspend fun getFlatDocuments(
        id: String,
    ): Response<FlatDocumentList>
}
