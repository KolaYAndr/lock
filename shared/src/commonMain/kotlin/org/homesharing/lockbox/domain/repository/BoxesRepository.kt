package org.homesharing.lockbox.domain.repository

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import org.homesharing.lockbox.domain.api.response.Response
import org.homesharing.lockbox.domain.model.access.Access
import org.homesharing.lockbox.domain.model.access.Accesses
import org.homesharing.lockbox.domain.model.access_revoke.AccessRevoke
import org.homesharing.lockbox.domain.model.box.Box
import org.homesharing.lockbox.domain.model.create_linking_code.CreateLinkingCode
import org.homesharing.lockbox.domain.model.mockbox.Mockbox
import org.homesharing.lockbox.domain.model.open_box.OpenBox
import org.homesharing.lockbox.domain.model.update_box.UpdateBox

interface BoxesRepository {
    fun getBoxesPagingFlow(): Flow<PagingData<Box>>

    suspend fun getBoxByMac(address: String): Response<Box>

    suspend fun openBoxByMac(address: String): Response<OpenBox>

    suspend fun unbindBox(address: String): Response<UpdateBox>

    suspend fun updateBoxName(macAddress: String, newName: String): Response<UpdateBox>

    suspend fun updateBoxAddress(macAddress: String, newAddress: String): Response<UpdateBox>

    suspend fun getListOfAccesses(macAddress: String): Response<Accesses>

    suspend fun createAccess(
        macAddress: String,
        startDate: Long,
        startTime: Long,
        endDate: Long,
        endTime: Long,
        alias: String?,
    ): Response<Access>

    suspend fun revokeAccess(macAddress: String, tokenId: String): Response<AccessRevoke>

    suspend fun createLinkingCode(): Response<CreateLinkingCode>

    suspend fun physicalSignUp(code: Int): Response<Mockbox>

}
