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
import org.homesharing.lockbox.data.network.dto.flat_detail.FlatDetailDto
import org.homesharing.lockbox.data.repository.paging.FlatPagingSource
import org.homesharing.lockbox.domain.api.response.Response
import org.homesharing.lockbox.domain.api.response.parseLockboxApiResponse
import org.homesharing.lockbox.domain.model.flat.Flat
import org.homesharing.lockbox.domain.model.flat.toDomain
import org.homesharing.lockbox.domain.model.flat_detail.FlatDetail
import org.homesharing.lockbox.domain.model.flat_detail.toDomain
import org.homesharing.lockbox.domain.model.flat_detail.toDto
import org.homesharing.lockbox.domain.model.premise_type.PremiseType
import org.homesharing.lockbox.domain.model.premise_type.toDto
import org.homesharing.lockbox.domain.model.room.RoomInput
import org.homesharing.lockbox.domain.model.room.toDto
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

    override suspend fun getFlat(id: String): Response<FlatDetail> {
        return parseLockboxApiResponse(
            apiCall = { api.getFlat(id) },
            mapper = FlatDetailDto::toDomain
        )
    }

    override suspend fun createFlat(
        address: FlatDetail.Address,
        name: String?,
        description: String?,
        premiseType: PremiseType?,
        livingArea: Double?,
        rooms: List<RoomInput>,
    ): Response<FlatDetail> {
        val request = CreateFlatRequestDto(
            address = address.toDto(),
            name = name,
            description = description,
            premiseType = premiseType?.toDto(),
            livingArea = livingArea,
            rooms = rooms.takeIf { it.isNotEmpty() }?.map { it.toDto() },
        )

        return parseLockboxApiResponse(
            apiCall = { api.createFlat(request) },
            mapper = FlatDetailDto::toDomain
        )
    }

    override suspend fun updateFlat(
        id: String,
        address: FlatDetail.Address,
        name: String?,
        description: String?,
        premiseType: PremiseType?,
        livingArea: Double?,
        rooms: List<RoomInput>,
    ): Response<FlatDetail> {
        val request = CreateFlatRequestDto(
            address = address.toDto(),
            name = name,
            description = description,
            premiseType = premiseType?.toDto(),
            livingArea = livingArea,
            rooms = rooms.takeIf { it.isNotEmpty() }?.map { it.toDto() },
        )

        return parseLockboxApiResponse(
            apiCall = { api.updateFlat(id, request) },
            mapper = FlatDetailDto::toDomain
        )
    }
}
