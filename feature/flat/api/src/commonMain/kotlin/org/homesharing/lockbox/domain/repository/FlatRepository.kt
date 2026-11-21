package org.homesharing.lockbox.domain.repository

import androidx.paging.ItemSnapshotList
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import org.homesharing.lockbox.domain.api.response.Response
import org.homesharing.lockbox.domain.model.flat.Flat
import org.homesharing.lockbox.domain.model.flat_detail.FlatDetail
import org.homesharing.lockbox.domain.model.premise_type.PremiseType
import org.homesharing.lockbox.domain.model.room.RoomInput

interface FlatRepository {
    /**
     * Получает поток страниц списка квартир текущего пользователя (`GET /flats`).
     * Требует авторизации.
     *
     * @return [Flow] с [PagingData] квартир
     */
    fun getFlatsFlow(): Flow<PagingData<Flat>>

    /**
     * Получает снимок страницы списка квартир (`GET /flats`).
     * Требует авторизации.
     *
     * @param limit размер страницы
     * @param cursor курсор следующей страницы; при `null` возвращается первая страница
     * @return [Flow] со снимком страницы квартир
     */
    suspend fun getFlatsSnapshot(
        limit: Int,
        cursor: String?
    ): Flow<ItemSnapshotList<Flat>>

    /**
     * Получает детальную проекцию квартиры (`GET /flats/{id}`).
     * Требует авторизации.
     *
     * @param id идентификатор квартиры (uuid)
     * @return [Response] с детальной информацией о квартире
     */
    suspend fun getFlat(
        id: String
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
}
