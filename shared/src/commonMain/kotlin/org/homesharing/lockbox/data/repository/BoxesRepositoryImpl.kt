package org.homesharing.lockbox.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.homesharing.lockbox.data.network.api.response.AuthResponse
import org.homesharing.lockbox.data.network.dto.access_revoke.AccessRevokeResponseDto
import org.homesharing.lockbox.data.network.dto.access.AccessDto
import org.homesharing.lockbox.data.network.dto.access.AccessResponseDto
import org.homesharing.lockbox.data.network.dto.box.BoxResponseDto
import org.homesharing.lockbox.data.network.dto.box.BoxWithStatusResponseDto
import org.homesharing.lockbox.data.network.dto.create_linking_code.CreateLinkingCodeDto
import org.homesharing.lockbox.data.network.dto.open_box.OpenBoxResponseDto
import org.homesharing.lockbox.data.network.dto.physical_sign_up.PhysicalSignUpResponseDto
import org.homesharing.lockbox.data.network.dto.update_box.UpdateBoxResponseDto
import org.homesharing.lockbox.data.network.paging.BoxesPagingSource
import org.homesharing.lockbox.data.network.token.refresh_manager.RefreshManager
import org.homesharing.lockbox.data.network.token.storage.TokenStorage
import org.homesharing.lockbox.domain.api.AccountApi
import org.homesharing.lockbox.domain.api.BoxesApi
import org.homesharing.lockbox.domain.api.response.Response
import org.homesharing.lockbox.domain.api.response.parseLockBoxApiResponse
import org.homesharing.lockbox.domain.model.physical_sign_up.PhysicalSignUp
import org.homesharing.lockbox.domain.model.access.Access
import org.homesharing.lockbox.domain.model.access.Accesses
import org.homesharing.lockbox.domain.model.access.toDomain
import org.homesharing.lockbox.domain.model.access_revoke.AccessRevoke
import org.homesharing.lockbox.domain.model.access_revoke.toDomain
import org.homesharing.lockbox.domain.model.box.Box
import org.homesharing.lockbox.domain.model.box.BoxWithStatus
import org.homesharing.lockbox.domain.model.box.toDomain
import org.homesharing.lockbox.domain.model.create_linking_code.CreateLinkingCode
import org.homesharing.lockbox.domain.model.create_linking_code.toDomain
import org.homesharing.lockbox.domain.model.open_box.OpenBox
import org.homesharing.lockbox.domain.model.open_box.toDomain
import org.homesharing.lockbox.domain.model.physical_sign_up.toDomain
import org.homesharing.lockbox.domain.model.update_box.UpdateBox
import org.homesharing.lockbox.domain.model.update_box.toDomain
import org.homesharing.lockbox.domain.repository.BoxesRepository
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

private const val PAGE_SIZE = 10
private const val PAD_ZERO = '0'

internal class BoxesRepositoryImpl(
    private val boxesApi: BoxesApi,
    private val accountApi: AccountApi,
    private val refreshManager: RefreshManager,
    private val tokenStorage: TokenStorage,
    private val pagingSourceFactory: () -> BoxesPagingSource,
) : BoxesRepository {
    override fun getBoxesPagingFlow(): Flow<PagingData<BoxWithStatus>> =
        Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = false,
                initialLoadSize = PAGE_SIZE
            ),
            pagingSourceFactory = { pagingSourceFactory() },
        ).flow


    override suspend fun getBoxByMac(address: String): Response<BoxWithStatus> =
        try {
            refreshTokenIfNeed()
            parseLockBoxApiResponse(
                apiCall = {
                    boxesApi.getBoxByMac(
                        accessToken = tokenStorage.getAccessToken(),
                        macAddress = address
                    )
                },
                mapper = BoxWithStatusResponseDto::toDomain
            )
        } catch (e: Exception) {
            Response.Failure(e.message ?: "")
        }

    override suspend fun openBoxByMac(address: String): Response<OpenBox> =
        try {
            refreshTokenIfNeed()
            parseLockBoxApiResponse(
                apiCall = {
                    boxesApi.openBox(
                        accessToken = tokenStorage.getAccessToken(),
                        macAddress = address
                    )
                },
                mapper = OpenBoxResponseDto::toDomain
            )
        } catch (e: Exception) {
            Response.Failure(e.message ?: "")
        }

    override suspend fun unbindBox(address: String): Response<UpdateBox> =
        try {
            refreshTokenIfNeed()
            parseLockBoxApiResponse(
                apiCall = {
                    boxesApi.deleteBox(
                        accessToken = tokenStorage.getAccessToken(),
                        macAddress = address
                    )
                },
                mapper = UpdateBoxResponseDto::toDomain
            )
        } catch (e: Exception) {
            Response.Failure(e.message ?: "")
        }

    override suspend fun updateBoxName(macAddress: String, newName: String): Response<UpdateBox> =
        try {
            refreshTokenIfNeed()
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
        } catch (e: Exception) {
            Response.Failure(e.message ?: "")
        }

    override suspend fun updateBoxAddress(
        macAddress: String,
        newAddress: String,
    ): Response<UpdateBox> =
        try {
            refreshTokenIfNeed()
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
        } catch (e: Exception) {
            Response.Failure(e.message ?: "")
        }

    override suspend fun getListOfAccesses(macAddress: String): Response<Accesses> =
        try {
            refreshTokenIfNeed()
            parseLockBoxApiResponse(
                apiCall = {
                    boxesApi.getAccessedList(
                        accessToken = tokenStorage.getAccessToken(),
                        macAddress = macAddress
                    )
                },
                mapper = AccessResponseDto::toDomain
            )
        } catch (e: Exception) {
            Response.Failure(e.message ?: "")
        }

    override suspend fun createAccess(
        macAddress: String,
        startDate: Long,
        startTime: Long,
        endDate: Long,
        endTime: Long,
        alias: String
    ): Response<Access> =
        try {
            refreshTokenIfNeed()
            parseLockBoxApiResponse(
                apiCall = {
                    boxesApi.createAccess(
                        accessToken = tokenStorage.getAccessToken(),
                        macAddress = macAddress,
                        alias = alias,
                        startTime = formatToBackendTimestamp(startDate + startTime),
                        endTime = formatToBackendTimestamp(endDate + endTime)
                    )
                },
                mapper = AccessDto::toDomain
            )
        } catch (e: Exception) {
            Response.Failure(e.message ?: "")
        }

    override suspend fun revokeAccess(macAddress: String, tokenId: String): Response<AccessRevoke> =
        try {
            refreshTokenIfNeed()
            parseLockBoxApiResponse(
                apiCall = {
                    boxesApi.revokeAccess(
                        accessToken = tokenStorage.getAccessToken(),
                        macAddress = macAddress,
                        tokenId = tokenId
                    )
                },
                mapper = AccessRevokeResponseDto::toDomain
            )
        } catch (e: Exception) {
            Response.Failure(e.message ?: "")
        }

    override suspend fun createLinkingCode(): Response<CreateLinkingCode> = try {
        refreshTokenIfNeed()
        parseLockBoxApiResponse(
            apiCall = { boxesApi.createLinkingCode(accessToken = tokenStorage.getAccessToken()) },
            mapper = CreateLinkingCodeDto::toDomain
        )
    } catch (e: Exception) {
        Response.Failure(e.message ?: "")
    }

    override suspend fun physicalSignUp(code: Int, macAddress: String): Response<PhysicalSignUp> =
        try {
            refreshTokenIfNeed()
            parseLockBoxApiResponse(
                apiCall = {
                    boxesApi.physicalSignUp(
                        accessToken = tokenStorage.getAccessToken(),
                        code = code,
                        macAddress = macAddress
                    )
                },
                mapper = PhysicalSignUpResponseDto::toDomain
            )
        } catch (e: Exception) {
            Response.Failure(e.message ?: "")
        }

    override suspend fun createTestDevice(): Response<Box> = try {
        refreshTokenIfNeed()
        parseLockBoxApiResponse(
            apiCall = { boxesApi.createTestDevice(accessToken = tokenStorage.getAccessToken()) },
            mapper = BoxResponseDto::toDomain
        )
    } catch (e: Exception) {
        Response.Failure(e.message ?: "")
    }

    @OptIn(ExperimentalTime::class)
    private fun formatToBackendTimestamp(epochMillis: Long): String {
        val ldt = Instant
            .fromEpochMilliseconds(epochMillis)
            .toLocalDateTime(TimeZone.UTC)

        val year = ldt.year.toString().padStart(4, PAD_ZERO)
        val month = ldt.month.toString().padStart(2, PAD_ZERO)
        val day = ldt.day.toString().padStart(2, PAD_ZERO)
        val hour = ldt.hour.toString().padStart(2, PAD_ZERO)
        val minute = ldt.minute.toString().padStart(2, PAD_ZERO)
        val second = ldt.second.toString().padStart(2, PAD_ZERO)

        return "$year-$month-${day}T$hour:$minute:$second"
    }

    private suspend fun refreshTokenIfNeed() {
        if (refreshManager.needsRefresh(tokenStorage.getNextRefreshTime())) {
            refreshToken()
        }
    }

    private suspend fun refreshToken() {
        val tokenResponse = accountApi.refreshTokens(
            accessToken = tokenStorage.getAccessToken(),
            refreshToken = tokenStorage.getRefreshToken()
        )

        if (tokenResponse is AuthResponse.Success) {
            val accessToken = tokenResponse.accessToken
            val refreshToken = tokenResponse.refreshToken
            val expiresIn = tokenResponse.expiresIn

            with(tokenStorage) {
                saveAccessToken(accessToken)
                saveRefreshToken(refreshToken)
                saveNextRefreshTime(expiresIn + refreshManager.currentTime)
            }
        }
    }
}
