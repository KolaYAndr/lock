package org.homesharing.lockbox.data.network.api

import org.homesharing.lockbox.data.network.api.response.ApiResponse
import org.homesharing.lockbox.data.network.dto.access.AccessDto
import org.homesharing.lockbox.data.network.dto.access.AccessesDto
import org.homesharing.lockbox.data.network.dto.access_revoke.AccessRevokeDto
import org.homesharing.lockbox.data.network.dto.box.BoxWithStatusDto
import org.homesharing.lockbox.data.network.dto.box_list.BoxListDto
import org.homesharing.lockbox.data.network.dto.create_linking_code.CreateLinkingCodeDto
import org.homesharing.lockbox.data.network.dto.open_box.OpenBoxDto
import org.homesharing.lockbox.data.network.dto.physical_sign_up.PhysicalSignUpDto
import org.homesharing.lockbox.data.network.dto.update_box.UpdateBoxDto

internal interface BoxesApi {
    suspend fun getBoxes(
        page: Int,
        size: Int,
    ): ApiResponse<BoxListDto>

    suspend fun getBoxByMac(
        macAddress: String,
    ): ApiResponse<BoxWithStatusDto>

    suspend fun updateBoxByMac(
        macAddress: String,
        ownerSub: String,
        alias: String? = null,
        address: String? = null,
    ): ApiResponse<UpdateBoxDto>

    suspend fun openBox(
        macAddress: String,
    ): ApiResponse<OpenBoxDto>

    suspend fun deleteBox(
        macAddress: String
    ): ApiResponse<Unit>

    suspend fun getAccessedList(
        macAddress: String,
    ): ApiResponse<AccessesDto>

    suspend fun createAccess(
        macAddress: String,
        alias: String,
        startTime: String,
        endTime: String,
    ): ApiResponse<AccessDto>

    suspend fun revokeAccess(
        macAddress: String,
        tokenId: String,
    ): ApiResponse<AccessRevokeDto>

    suspend fun createLinkingCode(): ApiResponse<CreateLinkingCodeDto>

    suspend fun physicalSignUp(
        code: Int,
        macAddress: String,
    ): ApiResponse<PhysicalSignUpDto>

    suspend fun createTestDevice(): ApiResponse<Unit>
}
