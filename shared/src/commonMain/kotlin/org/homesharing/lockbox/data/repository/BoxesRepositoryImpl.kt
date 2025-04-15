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
import org.homesharing.lockbox.data.network.token.refresh_manager.RefreshManager
import org.homesharing.lockbox.data.network.token.storage.TokenStorage
import org.homesharing.lockbox.domain.api.AccountApi
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

internal class BoxesRepositoryImpl(
    private val boxesApi: BoxesApi,
    private val accountApi: AccountApi,
    private val refreshManager: RefreshManager,
    private val tokenStorage: TokenStorage,
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
            if (refreshManager.needsRefresh(tokenStorage.getNextRefreshTime()))
                refreshToken()

            parseLockBoxApiResponse(
                apiCall = {
                    boxesApi.getBoxByMac(
                        accessToken = tokenStorage.getAccessToken(),
                        address = address
                    )
                },
                mapper = BoxResponseDto::toDomain
            )
        }

    override suspend fun openBoxByMac(address: String): Response<OpenBox> =
        withContext(Dispatchers.IO) {
            if (refreshManager.needsRefresh(tokenStorage.getNextRefreshTime()))
                refreshToken()

            parseLockBoxApiResponse(
                apiCall = {
                    boxesApi.openBox(
                        accessToken = tokenStorage.getAccessToken(),
                        mac = address
                    )
                },
                mapper = OpenBoxResponseDto::toDomain
            )
        }

    override suspend fun unbindBox(address: String): Response<UpdateBox> =
        withContext(Dispatchers.IO) {
            if (refreshManager.needsRefresh(tokenStorage.getNextRefreshTime()))
                refreshToken()

            parseLockBoxApiResponse(
                apiCall = {
                    boxesApi.deleteBox(
                        accessToken = tokenStorage.getAccessToken(),
                        mac = address
                    )
                },
                mapper = UpdateBoxResponseDto::toDomain
            )
        }

    override suspend fun updateBoxName(macAddress: String, newName: String): Response<UpdateBox> =
        withContext(Dispatchers.IO) {
            if (refreshManager.needsRefresh(tokenStorage.getNextRefreshTime()))
                refreshToken()

            parseLockBoxApiResponse(
                apiCall = {
                    boxesApi.updateBoxByMac(
                        macAddress = macAddress,
                        alias = newName,
                        accessToken = tokenStorage.getAccessToken(),
                    )
                },
                mapper = UpdateBoxResponseDto::toDomain
            )
        }

    override suspend fun updateBoxAddress(
        macAddress: String,
        newAddress: String
    ): Response<UpdateBox> =
        withContext(Dispatchers.IO) {
            if (refreshManager.needsRefresh(tokenStorage.getNextRefreshTime()))
                refreshToken()

            parseLockBoxApiResponse(
                apiCall = {
                    boxesApi.updateBoxByMac(
                        accessToken = tokenStorage.getAccessToken(),
                        macAddress = macAddress,
                        address = newAddress
                    )
                },
                mapper = UpdateBoxResponseDto::toDomain
            )
        }

    private suspend fun refreshToken() {
        try {
            val tokenResponse =
                accountApi.refreshTokens(
                    accesstoken = tokenStorage.getAccessToken(),
                    refreshToken = tokenStorage.getRefreshToken()
                )

            if (tokenResponse.error.isNullOrEmpty()) {
                val accessToken = tokenResponse.accessToken!!
                val refreshToken = tokenResponse.refreshToken!!
                val expiresIn = tokenResponse.expiresIn!!

                with(tokenStorage) {
                    saveAccessToken(accessToken)
                    saveRefreshToken(refreshToken)
                    saveNextRefreshTime(expiresIn + refreshManager.currentTime)
                }
            }
        } catch (_: Exception) { }
    }
}
