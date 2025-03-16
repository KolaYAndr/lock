package org.homesharing.lockbox.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import org.homesharing.lockbox.data.network.dto.box.BoxResponseDto
import org.homesharing.lockbox.data.network.dto.open_box.OpenBoxResponseDto
import org.homesharing.lockbox.data.network.dto.update_box.UpdateBoxResponseDto
import org.homesharing.lockbox.data.network.paging.BoxesPagingSource
import org.homesharing.lockbox.domain.api.BoxesApi
import org.homesharing.lockbox.domain.api.response.Response
import org.homesharing.lockbox.domain.api.response.parseLockBoxApiResponse
import org.homesharing.lockbox.domain.model.box.Box
import org.homesharing.lockbox.domain.model.box.toDomain
import org.homesharing.lockbox.domain.model.open_box.OpenBox
import org.homesharing.lockbox.domain.model.open_box.toDomain
import org.homesharing.lockbox.domain.model.update_box.UpdateBox
import org.homesharing.lockbox.domain.model.update_box.toDomain
import org.homesharing.lockbox.domain.repository.BoxesRepository

private const val PAGE_SIZE = 10

class BoxesRepositoryImpl(
    private val api: BoxesApi,
    private val pagingSourceFactory: () -> BoxesPagingSource
) : BoxesRepository {

    override fun getBoxesPagingFlow(): Flow<PagingData<Box>> =
        Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = false,
                initialLoadSize = PAGE_SIZE
            ),
            pagingSourceFactory = { pagingSourceFactory() },
        ).flow.flowOn(Dispatchers.IO)


    override suspend fun getBoxByMac(address: String): Response<Box> =
        withContext(Dispatchers.IO) {
            parseLockBoxApiResponse(
                apiCall = { api.getBoxByMac(address) },
                mapper = BoxResponseDto::toDomain
            )
        }

    override suspend fun openBoxByMac(address: String): Response<OpenBox> =
        withContext(Dispatchers.IO) {
            parseLockBoxApiResponse(
                apiCall = { api.openBox(mac = address) },
                mapper = OpenBoxResponseDto::toDomain
            )
        }

    override suspend fun unbindBox(address: String): Response<UpdateBox> =
        withContext(Dispatchers.IO) {
            parseLockBoxApiResponse(
                apiCall = { api.deleteBox(mac = address) },
                mapper = UpdateBoxResponseDto::toDomain
            )
        }

    override suspend fun updateBoxName(macAddress: String, newName: String): Response<UpdateBox> =
        withContext(Dispatchers.IO) {
            parseLockBoxApiResponse(
                apiCall = { api.updateBoxByMac(macAddress = macAddress, alias = newName) },
                mapper = UpdateBoxResponseDto::toDomain
            )
        }

    override suspend fun updateBoxAddress(
        macAddress: String,
        newAddress: String
    ): Response<UpdateBox> =
        withContext(Dispatchers.IO) {
            parseLockBoxApiResponse(
                apiCall = { api.updateBoxByMac(macAddress = macAddress, address = newAddress) },
                mapper = UpdateBoxResponseDto::toDomain
            )
        }
}
