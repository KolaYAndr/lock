package org.homesharing.lockbox.data.repository

import androidx.paging.ItemSnapshotList
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.asItemSnapshotListFlow
import androidx.paging.map
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.homesharing.lockbox.data.network.api.FlatApi
import org.homesharing.lockbox.data.network.dto.create_flat.CreateFlatRequestDto
import org.homesharing.lockbox.data.network.dto.room.RoomDto
import org.homesharing.lockbox.data.repository.paging.FlatPagingSource
import org.homesharing.lockbox.domain.api.response.Response
import org.homesharing.lockbox.domain.api.response.parseLockboxApiResponse
import org.homesharing.lockbox.domain.model.flat.Flat
import org.homesharing.lockbox.domain.model.flat.toDomain
import org.homesharing.lockbox.domain.model.premise_type.PremiseType
import org.homesharing.lockbox.domain.model.premise_type.toDto
import org.homesharing.lockbox.domain.repository.FlatRepository

private const val PAGE_SIZE = 10

internal class FlatRepositoryImpl(
    private val api: FlatApi,
) : FlatRepository {

    override fun getFlatsFlow(): Flow<PagingData<Flat>> =
        Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = false,
            ),
            pagingSourceFactory = { FlatPagingSource(api) }
        ).flow
            .map { pagingData ->
                pagingData.map { it.toDomain() }
            }

    override suspend fun getFlatsSnapshot(limit: Int, cursor: String?): Flow<ItemSnapshotList<Flat>> =
        Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = false,
            ),
            pagingSourceFactory = { FlatPagingSource(api) }
        ).flow
            .asItemSnapshotListFlow()
            .map { snapshot ->
                ItemSnapshotList(
                    placeholdersBefore = snapshot.placeholdersBefore,
                    placeholdersAfter = snapshot.placeholdersAfter,
                    items = snapshot.items.map { it.toDomain() }
                )
            }

    override suspend fun getFlat(id: String): Response<Flat> {
        return parseLockboxApiResponse(
            apiCall = { api.getFlat(id) },
            mapper = { it.toDomain() }
        )
    }

    override suspend fun createFlat(
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
    ): Response<Flat> {
        val request = CreateFlatRequestDto(
            name = name,
            premiseType = premiseType.toDto(),
            address = CreateFlatRequestDto.AddressRequestDto(
                city = city,
                street = street,
                house = house,
                entrance = entrance,
                apartment = apartment,
                block = block,
                building = building,
                litera = litera,
                comment = comment,
            ),
            livingArea = livingArea,
            rooms = roomNames.map { RoomDto(name = it, position = null) }
        )

        return parseLockboxApiResponse(
            apiCall = { api.createFlat(request) },
            mapper = { it.toDomain() }
        )
    }

    override suspend fun updateFlat(
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
    ): Response<Flat> {
        val request = CreateFlatRequestDto(
            name = name,
            premiseType = premiseType.toDto(),
            address = CreateFlatRequestDto.AddressRequestDto(
                city = city,
                street = street,
                house = house,
                entrance = entrance,
                apartment = apartment,
                block = block,
                building = building,
                litera = litera,
                comment = comment,
            ),
            livingArea = livingArea,
            rooms = roomNames.map { RoomDto(name = it, position = null) }
        )

        return parseLockboxApiResponse(
            apiCall = { api.updateFlat(id, request) },
            mapper = { it.toDomain() }
        )
    }
}
