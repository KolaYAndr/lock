package org.homesharing.lockbox.domain.repository

import androidx.paging.ItemSnapshotList
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import org.homesharing.lockbox.domain.api.response.Response
import org.homesharing.lockbox.domain.model.flat.Flat
import org.homesharing.lockbox.domain.model.premise_type.PremiseType

interface FlatRepository {
    fun getFlatsFlow(): Flow<PagingData<Flat>>

    suspend fun getFlatsSnapshot(
        limit: Int,
        cursor: String?
    ): Flow<ItemSnapshotList<Flat>>

    suspend fun getFlat(
        id: String
    ): Response<Flat>

    suspend fun createFlat(
        name: String,
        premiseType: PremiseType,
        city: String,
        street: String?,
        house: String?,
        entrance: String?,
        apartment: String?,
        block: String?,
        building: String?,
        litera: String?,
        comment: String?,
        livingArea: Double?,
        roomNames: List<String>
    ): Response<Flat>

    suspend fun updateFlat(
        id: String,
        name: String,
        premiseType: PremiseType,
        city: String,
        street: String?,
        house: String?,
        entrance: String?,
        apartment: String?,
        block: String?,
        building: String?,
        litera: String?,
        comment: String?,
        livingArea: Double?,
        roomNames: List<String>
    ): Response<Flat>
}
