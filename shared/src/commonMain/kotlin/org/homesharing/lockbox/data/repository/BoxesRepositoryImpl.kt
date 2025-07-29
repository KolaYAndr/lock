package org.homesharing.lockbox.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.homesharing.lockbox.data.network.dto.access_revoke.AccessRevokeResponseDto
import org.homesharing.lockbox.data.network.dto.accessed.AccessDto
import org.homesharing.lockbox.data.network.dto.accessed.AccessResponseDto
import org.homesharing.lockbox.data.network.dto.box.BoxResponseDto
import org.homesharing.lockbox.data.network.dto.create_linking_code.CreateLinkingCodeDto
import org.homesharing.lockbox.data.network.dto.mockbox.MockboxDto
import org.homesharing.lockbox.data.network.dto.open_box.OpenBoxResponseDto
import org.homesharing.lockbox.data.network.dto.update_box.UpdateBoxResponseDto
import org.homesharing.lockbox.data.network.paging.BoxesPagingSource
import org.homesharing.lockbox.data.network.token.refresh_manager.RefreshManager
import org.homesharing.lockbox.data.network.token.storage.TokenStorage
import org.homesharing.lockbox.domain.api.AccountApi
import org.homesharing.lockbox.domain.api.BoxesApi
import org.homesharing.lockbox.domain.api.response.Response
import org.homesharing.lockbox.domain.api.response.parseLockBoxApiResponse
import org.homesharing.lockbox.domain.model.access.Access
import org.homesharing.lockbox.domain.model.access.Accesses
import org.homesharing.lockbox.domain.model.access.toDomain
import org.homesharing.lockbox.domain.model.access_revoke.AccessRevoke
import org.homesharing.lockbox.domain.model.access_revoke.toDomain
import org.homesharing.lockbox.domain.model.box.Box
import org.homesharing.lockbox.domain.model.box.toDomain
import org.homesharing.lockbox.domain.model.create_linking_code.CreateLinkingCode
import org.homesharing.lockbox.domain.model.create_linking_code.toDomain
import org.homesharing.lockbox.domain.model.mockbox.Mockbox
import org.homesharing.lockbox.domain.model.mockbox.toDomain
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
    private val pagingSourceFactory: () -> BoxesPagingSource,
) : BoxesRepository {

    override fun getBoxesPagingFlow(): Flow<PagingData<Box>> =
        Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = false,
                initialLoadSize = PAGE_SIZE
            ),
            pagingSourceFactory = { pagingSourceFactory() },
        ).flow


    override suspend fun getBoxByMac(address: String): Response<Box> {
        if (refreshManager.needsRefresh(tokenStorage.getNextRefreshTime())) {
            try {
                refreshToken()
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                return Response.Failure(e.message.orEmpty())
            }
        }

        return parseLockBoxApiResponse(
            apiCall = {
                boxesApi.getBoxByMac(
                    accessToken = tokenStorage.getAccessToken(),
                    macAddress = address
                )
            },
            mapper = BoxResponseDto::toDomain
        )
    }

    override suspend fun openBoxByMac(address: String): Response<OpenBox> {
        if (refreshManager.needsRefresh(tokenStorage.getNextRefreshTime())) {
            try {
                refreshToken()
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                return Response.Failure(e.message.orEmpty())
            }
        }

        return parseLockBoxApiResponse(
            apiCall = {
                boxesApi.openBox(
                    accessToken = tokenStorage.getAccessToken(),
                    macAddress = address
                )
            },
            mapper = OpenBoxResponseDto::toDomain
        )
    }

    override suspend fun unbindBox(address: String): Response<UpdateBox> {
        if (refreshManager.needsRefresh(tokenStorage.getNextRefreshTime())) {
            try {
                refreshToken()
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                return Response.Failure(e.message.orEmpty())
            }
        }

        return parseLockBoxApiResponse(
            apiCall = {
                boxesApi.deleteBox(
                    accessToken = tokenStorage.getAccessToken(),
                    macAddress = address
                )
            },
            mapper = UpdateBoxResponseDto::toDomain
        )
    }

    override suspend fun updateBoxName(macAddress: String, newName: String): Response<UpdateBox> {
        if (refreshManager.needsRefresh(tokenStorage.getNextRefreshTime())) {
            try {
                refreshToken()
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                return Response.Failure(e.message.orEmpty())
            }
        }

        return parseLockBoxApiResponse(
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
        newAddress: String,
    ): Response<UpdateBox> {
        if (refreshManager.needsRefresh(tokenStorage.getNextRefreshTime())) {
            try {
                refreshToken()
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                return Response.Failure(e.message.orEmpty())
            }
        }

        return parseLockBoxApiResponse(
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

    override suspend fun getListOfAccesses(macAddress: String): Response<Accesses> {
        if (refreshManager.needsRefresh(tokenStorage.getNextRefreshTime())) {
            try {
                refreshToken()
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                return Response.Failure(e.message.orEmpty())
            }
        }

        return parseLockBoxApiResponse(
            apiCall = {
                boxesApi.getAccessedList(
                    accessToken = tokenStorage.getAccessToken(),
                    macAddress = macAddress
                )
            },
            mapper = AccessResponseDto::toDomain
        )
    }

    override suspend fun createAccess(
        macAddress: String,
        startTime: Long,
        endTime: Long,
        alias: String?,
    ): Response<Access> {
        if (refreshManager.needsRefresh(tokenStorage.getNextRefreshTime())) {
            try {
                refreshToken()
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                return Response.Failure(e.message.orEmpty())
            }
        }

        return parseLockBoxApiResponse(
            apiCall = {
                boxesApi.createAccess(
                    accessToken = tokenStorage.getAccessToken(),
                    macAddress = macAddress,
                    alias = alias,
                    startTime = formatToBackendTimestamp(startTime),
                    endTime = formatToBackendTimestamp(endTime)
                )
            },
            mapper = AccessDto::toDomain
        )
    }

    override suspend fun revokeAccess(macAddress: String, tokenId: String): Response<AccessRevoke> {
        if (refreshManager.needsRefresh(tokenStorage.getNextRefreshTime())) {
            try {
                refreshToken()
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                return Response.Failure(e.message.orEmpty())
            }
        }

        return parseLockBoxApiResponse(
            apiCall = {
                boxesApi.revokeAccess(
                    accessToken = tokenStorage.getAccessToken(),
                    macAddress = macAddress,
                    tokenId = tokenId
                )
            },
            mapper = AccessRevokeResponseDto::toDomain
        )
    }

    private fun formatToBackendTimestamp(epochMillis: Long): String {
        val ldt = Instant
            .fromEpochMilliseconds(epochMillis)
            .toLocalDateTime(TimeZone.UTC)

        val year = ldt.year.toString().padStart(4, '0')
        val month = ldt.monthNumber.toString().padStart(2, '0')
        val day = ldt.dayOfMonth.toString().padStart(2, '0')
        val hour = ldt.hour.toString().padStart(2, '0')
        val minute = ldt.minute.toString().padStart(2, '0')
        val second = ldt.second.toString().padStart(2, '0')

        return "$year-$month-$day" + "T" + "$hour:$minute:$second"
    }

    private suspend fun refreshToken() {
        val tokenResponse =
            accountApi.refreshTokens(
                accessToken = tokenStorage.getAccessToken(),
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
    }

    override suspend fun createLinkingCode(): Response<CreateLinkingCode> {
        if (refreshManager.needsRefresh(tokenStorage.getNextRefreshTime())) {
            try {
                refreshToken()
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                return Response.Failure(e.message.orEmpty())
            }
        }

        return parseLockBoxApiResponse(
            apiCall = {
                boxesApi.createLinkingCode(
                    accessToken = tokenStorage.getAccessToken()
                )
            },
            mapper = CreateLinkingCodeDto::toDomain
        )
    }

    override suspend fun physicalSignUp(code: Int): Response<Mockbox> {
        if (refreshManager.needsRefresh(tokenStorage.getNextRefreshTime())) {
            try {
                refreshToken()
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                return Response.Failure(e.message.orEmpty())
            }
        }

        return parseLockBoxApiResponse(
            apiCall = {
                boxesApi.physicalSignUp(
                    accessToken = tokenStorage.getAccessToken(),
                    code = code
                )
            },
            mapper = MockboxDto::toDomain
        )
    }
}
