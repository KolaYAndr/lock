package org.homesharing.lockbox.data.repository

import androidx.paging.ItemSnapshotList
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.asItemSnapshotListFlow
import androidx.paging.map
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.homesharing.lockbox.data.network.api.BoxesApi
import org.homesharing.lockbox.data.network.dto.access.AccessDto
import org.homesharing.lockbox.data.network.dto.access.AccessesDto
import org.homesharing.lockbox.data.network.dto.access_revoke.AccessRevokeDto
import org.homesharing.lockbox.data.network.dto.box.BoxWithStatusDto
import org.homesharing.lockbox.data.network.dto.create_linking_code.CreateLinkingCodeDto
import org.homesharing.lockbox.data.network.dto.open_box.OpenBoxDto
import org.homesharing.lockbox.data.network.dto.physical_sign_up.PhysicalSignUpDto
import org.homesharing.lockbox.data.network.dto.update_box.UpdateBoxDto
import org.homesharing.lockbox.data.network.paging.BoxesPagingSource
import org.homesharing.lockbox.domain.api.response.Response
import org.homesharing.lockbox.domain.api.response.parseLockBoxApiResponse
import org.homesharing.lockbox.domain.model.access.Access
import org.homesharing.lockbox.domain.model.access.Accesses
import org.homesharing.lockbox.domain.model.access.toDomain
import org.homesharing.lockbox.domain.model.access_revoke.AccessRevoke
import org.homesharing.lockbox.domain.model.access_revoke.toDomain
import org.homesharing.lockbox.domain.model.box.BoxWithStatus
import org.homesharing.lockbox.domain.model.box.toDomain
import org.homesharing.lockbox.domain.model.create_linking_code.CreateLinkingCode
import org.homesharing.lockbox.domain.model.create_linking_code.toDomain
import org.homesharing.lockbox.domain.model.open_box.OpenBox
import org.homesharing.lockbox.domain.model.open_box.toDomain
import org.homesharing.lockbox.domain.model.physical_sign_up.PhysicalSignUp
import org.homesharing.lockbox.domain.model.physical_sign_up.toDomain
import org.homesharing.lockbox.domain.model.update_box.UpdateBox
import org.homesharing.lockbox.domain.model.update_box.toDomain
import org.homesharing.lockbox.domain.repository.BoxesRepository
import kotlin.collections.map
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

private const val PAGE_SIZE = 10
private const val PAD_ZERO = '0'

internal class BoxesRepositoryImpl(
    private val boxesApi: BoxesApi,
) : BoxesRepository {
    override fun getBoxesPagingFlow(): Flow<PagingData<BoxWithStatus>> =
        Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = false,
                initialLoadSize = PAGE_SIZE
            ),
            pagingSourceFactory = { BoxesPagingSource(boxesApi) },
        ).flow
            .map { pagingData ->
                pagingData.map { it.toDomain() }
            }

    override fun getBoxesSnapshot(): Flow<ItemSnapshotList<BoxWithStatus>> =
        Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = false,
                initialLoadSize = PAGE_SIZE
            ),
            pagingSourceFactory = { BoxesPagingSource(boxesApi) },
        ).flow
            .asItemSnapshotListFlow()
            .map { snapshot ->
                ItemSnapshotList(
                    placeholdersBefore = snapshot.placeholdersBefore,
                    placeholdersAfter = snapshot.placeholdersAfter,
                    items = snapshot.items.map { it.toDomain() }
                )
            }

    override suspend fun getBoxByMac(address: String): Response<BoxWithStatus> =
        parseLockBoxApiResponse(
            apiCall = {
                boxesApi.getBoxByMac(
                    macAddress = address
                )
            },
            mapper = BoxWithStatusDto::toDomain
        )

    override suspend fun openBoxByMac(address: String): Response<OpenBox> =
        parseLockBoxApiResponse(
            apiCall = {
                boxesApi.openBox(
                    macAddress = address
                )
            },
            mapper = OpenBoxDto::toDomain
        )

    override suspend fun unbindBox(address: String): Response<Unit> =
        parseLockBoxApiResponse {
            boxesApi.deleteBox(
                macAddress = address
            )
        }

    override suspend fun updateBoxName(
        macAddress: String,
        newName: String,
        ownerSub: String,
    ): Response<UpdateBox> =
        parseLockBoxApiResponse(
            apiCall = {
                boxesApi.updateBoxByMac(
                    macAddress = macAddress,
                    alias = newName,
                    ownerSub = ownerSub
                )
            },
            mapper = UpdateBoxDto::toDomain
        )

    override suspend fun updateBoxAddress(
        macAddress: String,
        newAddress: String,
        ownerSub: String,
    ): Response<UpdateBox> =
        parseLockBoxApiResponse(
            apiCall = {
                boxesApi.updateBoxByMac(
                    macAddress = macAddress,
                    address = newAddress,
                    ownerSub = ownerSub
                )
            },
            mapper = UpdateBoxDto::toDomain
        )

    override suspend fun getListOfAccesses(macAddress: String): Response<Accesses> =
        parseLockBoxApiResponse(
            apiCall = {
                boxesApi.getAccessedList(
                    macAddress = macAddress
                )
            },
            mapper = AccessesDto::toDomain
        )

    override suspend fun createAccess(
        macAddress: String,
        startDate: Long,
        startTime: Long,
        endDate: Long,
        endTime: Long,
        alias: String,
    ): Response<Access> =
        parseLockBoxApiResponse(
            apiCall = {
                boxesApi.createAccess(
                    macAddress = macAddress,
                    alias = alias,
                    startTime = formatToBackendTimestamp(startDate + startTime),
                    endTime = formatToBackendTimestamp(endDate + endTime)
                )
            },
            mapper = AccessDto::toDomain
        )

    override suspend fun revokeAccess(macAddress: String, tokenId: String): Response<AccessRevoke> =
        parseLockBoxApiResponse(
            apiCall = {
                boxesApi.revokeAccess(
                    macAddress = macAddress,
                    tokenId = tokenId
                )
            },
            mapper = AccessRevokeDto::toDomain
        )

    override suspend fun createLinkingCode(): Response<CreateLinkingCode> =
        parseLockBoxApiResponse(
            apiCall = { boxesApi.createLinkingCode() },
            mapper = CreateLinkingCodeDto::toDomain
        )

    override suspend fun physicalSignUp(code: Int, macAddress: String): Response<PhysicalSignUp> =
        parseLockBoxApiResponse(
            apiCall = {
                boxesApi.physicalSignUp(
                    code = code,
                    macAddress = macAddress
                )
            },
            mapper = PhysicalSignUpDto::toDomain
        )

    override suspend fun createTestDevice(): Response<Unit> =
        parseLockBoxApiResponse { boxesApi.createTestDevice() }

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
}
