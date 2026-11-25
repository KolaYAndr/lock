package org.homesharing.lockbox.domain.repository

import androidx.paging.ItemSnapshotList
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import org.homesharing.lockbox.domain.api.response.Response
import org.homesharing.lockbox.domain.model.flat.Flat
import org.homesharing.lockbox.domain.model.flat_detail.FlatDetail
import org.homesharing.lockbox.domain.model.flat_files.FlatDocument
import org.homesharing.lockbox.domain.model.flat_files.FlatDocumentType
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
     * Частично обновляет квартиру текущего пользователя (`PATCH /flats/{id}`).
     * Требует авторизации.
     *
     * @param id идентификатор квартиры (uuid)
     * @param address адрес квартиры; обязателен только `city`
     * @param name название; при `null` или пустом значении сервер подставляет `full_address`
     * @param description описание (до 10000 символов)
     * @param premiseType тип помещения; при `null` сервер использует `residential`
     * @param livingArea жилая площадь; отрицательное значение отклоняется сервером (`400`)
     * @param rooms комнаты; `position` по умолчанию — индекс в массиве
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

    suspend fun archiveFlat(id: String): Response<FlatDetail>

    suspend fun restoreFlat(id: String): Response<FlatDetail>

    suspend fun getPhotos(id: String): Response<List<FlatDetail.Photo>>

    suspend fun uploadPhoto(
        id: String,
        data: ByteArray,
        fileName: String,
        contentType: String,
    ): Response<FlatDetail.Photo>

    suspend fun reorderPhotos(id: String, photoIds: List<String>): Response<List<FlatDetail.Photo>>

    suspend fun deletePhoto(id: String, photoId: String): Response<Unit>

    suspend fun getDocuments(id: String): Response<List<FlatDocument>>

    suspend fun uploadDocument(
        id: String,
        data: ByteArray,
        fileName: String,
        contentType: String,
        documentType: FlatDocumentType? = null,
    ): Response<FlatDocument>

    suspend fun deleteDocument(id: String, documentId: String): Response<Unit>
}
