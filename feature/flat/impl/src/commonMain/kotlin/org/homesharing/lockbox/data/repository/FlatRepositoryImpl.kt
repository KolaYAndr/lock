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
import org.homesharing.lockbox.data.network.dto.flat_files.FlatDocumentDto
import org.homesharing.lockbox.data.network.dto.flat_files.toDomain
import org.homesharing.lockbox.data.network.dto.flat_detail.FlatDetailDto
import org.homesharing.lockbox.data.network.dto.flat_detail.FlatIncludeDto
import org.homesharing.lockbox.data.repository.paging.FlatPagingSource
import org.homesharing.lockbox.domain.api.response.Response
import org.homesharing.lockbox.domain.api.response.parseLockboxApiResponse
import org.homesharing.lockbox.domain.model.flat.Flat
import org.homesharing.lockbox.domain.model.flat.toDomain
import org.homesharing.lockbox.domain.model.flat_detail.FlatDetail
import org.homesharing.lockbox.domain.model.flat_detail.toDomain
import org.homesharing.lockbox.domain.model.flat_detail.toDto
import org.homesharing.lockbox.domain.model.flat_files.FlatDocument
import org.homesharing.lockbox.domain.model.flat_files.FlatDocumentType
import org.homesharing.lockbox.domain.model.flat_list.FlatStatusFilter
import org.homesharing.lockbox.domain.model.flat_list.toDto
import org.homesharing.lockbox.domain.model.premise_type.PremiseType
import org.homesharing.lockbox.domain.model.premise_type.toDto
import org.homesharing.lockbox.domain.model.room.RoomInput
import org.homesharing.lockbox.domain.model.room.toDto
import org.homesharing.lockbox.domain.repository.FlatRepository

private const val PAGE_SIZE = 10

internal class FlatRepositoryImpl(
    private val api: FlatApi,
) : FlatRepository {

    override fun getFlatsFlow(status: FlatStatusFilter): Flow<PagingData<Flat>> =
        Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = false,
            ),
            pagingSourceFactory = { FlatPagingSource(api, status.toDto()) }
        ).flow
            .map { pagingData ->
                pagingData.map { it.toDomain() }
            }

    override suspend fun getFlatsSnapshot(
        limit: Int,
        cursor: String?,
        status: FlatStatusFilter,
    ): Flow<ItemSnapshotList<Flat>> =
        Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = false,
            ),
            pagingSourceFactory = { FlatPagingSource(api, status.toDto()) }
        ).flow
            .asItemSnapshotListFlow()
            .map { snapshot ->
                ItemSnapshotList(
                    placeholdersBefore = snapshot.placeholdersBefore,
                    placeholdersAfter = snapshot.placeholdersAfter,
                    items = snapshot.items.map { it.toDomain() }
                )
            }

    override suspend fun getFlat(
        id: String,
        includeInvites: Boolean,
    ): Response<FlatDetail> {
        return parseLockboxApiResponse(
            apiCall = {
                api.getFlat(
                    id = id,
                    include = if (includeInvites) {
                        FlatIncludeDto.Invites
                    } else {
                        null
                    },
                )
            },
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

    override suspend fun archiveFlat(id: String): Response<FlatDetail> =
        parseLockboxApiResponse({ api.archiveFlat(id) }, FlatDetailDto::toDomain)

    override suspend fun restoreFlat(id: String): Response<FlatDetail> =
        parseLockboxApiResponse({ api.restoreFlat(id) }, FlatDetailDto::toDomain)

    override suspend fun getPhotos(id: String): Response<List<FlatDetail.Photo>> =
        parseLockboxApiResponse({ api.getPhotos(id) }) { response ->
            response.items.map(FlatDetailDto.PhotoDto::toDomain)
        }

    override suspend fun uploadPhoto(
        id: String,
        data: ByteArray,
        fileName: String,
        contentType: String,
    ): Response<FlatDetail.Photo> =
        parseLockboxApiResponse(
            { api.uploadPhoto(id, data, fileName, contentType) },
            FlatDetailDto.PhotoDto::toDomain,
        )

    override suspend fun reorderPhotos(id: String, photoIds: List<String>): Response<List<FlatDetail.Photo>> =
        parseLockboxApiResponse({ api.reorderPhotos(id, photoIds) }) { response ->
            response.items.map(FlatDetailDto.PhotoDto::toDomain)
        }

    override suspend fun deletePhoto(id: String, photoId: String): Response<Unit> =
        parseLockboxApiResponse { api.deletePhoto(id, photoId) }

    override suspend fun getDocuments(id: String): Response<List<FlatDocument>> =
        parseLockboxApiResponse({ api.getDocuments(id) }) { response ->
            response.items.map(FlatDocumentDto::toDomain)
        }

    override suspend fun uploadDocument(
        id: String,
        data: ByteArray,
        fileName: String,
        contentType: String,
        documentType: FlatDocumentType?,
    ): Response<FlatDocument> =
        parseLockboxApiResponse(
            { api.uploadDocument(id, data, fileName, contentType, documentType?.apiValue) },
            FlatDocumentDto::toDomain,
        )

    override suspend fun deleteDocument(id: String, documentId: String): Response<Unit> =
        parseLockboxApiResponse { api.deleteDocument(id, documentId) }
}
