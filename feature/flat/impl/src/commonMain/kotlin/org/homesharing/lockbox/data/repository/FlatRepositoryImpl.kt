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
import org.homesharing.lockbox.data.network.dto.flat_detail.FlatIncludeDto
import org.homesharing.lockbox.data.network.dto.flat_document.FlatDocumentListDto
import org.homesharing.lockbox.data.network.dto.flat_photo.ReorderPhotosRequestDto
import org.homesharing.lockbox.data.repository.paging.FlatPagingSource
import org.homesharing.lockbox.domain.api.response.Response
import org.homesharing.lockbox.domain.api.response.parseLockboxApiResponse
import org.homesharing.lockbox.domain.model.flat.Flat
import org.homesharing.lockbox.domain.model.flat.toDomain
import org.homesharing.lockbox.domain.model.flat_detail.FlatDetail
import org.homesharing.lockbox.domain.model.flat_detail.toDomain
import org.homesharing.lockbox.domain.model.flat_detail.toDto
import org.homesharing.lockbox.domain.model.flat_document.FlatDocumentList
import org.homesharing.lockbox.domain.model.flat_document.FlatDocumentType
import org.homesharing.lockbox.domain.model.flat_document.toDomain
import org.homesharing.lockbox.domain.model.flat_document.toDto
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

    override suspend fun getFlatPhotos(id: String): Response<List<String>> {
        return parseLockboxApiResponse(
            apiCall = { api.getFlatPhotos(id) },
            mapper = { it.items.map { photo -> photo.url } }
        )
    }

    override suspend fun uploadFlatPhoto(
        id: String,
        data: ByteArray,
        fileName: String,
        contentType: String,
    ): Response<String> {
        return parseLockboxApiResponse(
            apiCall = { api.uploadFlatPhoto(id, data, fileName, contentType) },
            mapper = { it.url }
        )
    }

    override suspend fun reorderFlatPhotos(
        id: String,
        photoIds: List<String>,
    ): Response<List<String>> {
        val request = ReorderPhotosRequestDto(photoIds = photoIds)

        return parseLockboxApiResponse(
            apiCall = { api.reorderFlatPhotos(id, request) },
            mapper = { it.items.map { photo -> photo.url } }
        )
    }

    override suspend fun deleteFlatPhoto(id: String, photoId: String): Response<Unit> {
        return parseLockboxApiResponse {
            api.deleteFlatPhoto(id, photoId)
        }
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

    override suspend fun archiveFlat(id: String): Response<FlatDetail> {
        return parseLockboxApiResponse(
            apiCall = { api.archiveFlat(id) },
            mapper = FlatDetailDto::toDomain
        )
    }

    override suspend fun restoreFlat(id: String): Response<FlatDetail> {
        return parseLockboxApiResponse(
            apiCall = { api.restoreFlat(id) },
            mapper = FlatDetailDto::toDomain
        )
    }

    override suspend fun getFlatDocuments(id: String): Response<FlatDocumentList> {
        return parseLockboxApiResponse(
            apiCall = { api.getFlatDocuments(id) },
            mapper = FlatDocumentListDto::toDomain
        )
    }

    override suspend fun uploadFlatDocument(
        id: String,
        data: ByteArray,
        fileName: String,
        contentType: String,
        docType: FlatDocumentType?,
    ): Response<FlatDocumentList.FlatDocument> {
        return parseLockboxApiResponse(
            apiCall = {
                api.uploadFlatDocument(
                    id = id,
                    data = data,
                    fileName = fileName,
                    contentType = contentType,
                    docType = docType?.toDto(),
                )
            },
            mapper = FlatDocumentListDto.FlatDocumentDto::toDomain
        )
    }

    override suspend fun deleteFlatDocument(id: String, documentId: String): Response<Unit> {
        return parseLockboxApiResponse {
            api.deleteFlatDocument(id, documentId)
        }
    }
}
