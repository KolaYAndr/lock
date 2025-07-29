package org.homesharing.lockbox.domain.api

import org.homesharing.lockbox.data.network.api.ApiResponse
import org.homesharing.lockbox.data.network.dto.access_revoke.AccessRevokeResponseDto
import org.homesharing.lockbox.data.network.dto.accessed.AccessDto
import org.homesharing.lockbox.data.network.dto.accessed.AccessResponseDto
import org.homesharing.lockbox.data.network.dto.box_list.BoxListResponseDto
import org.homesharing.lockbox.data.network.dto.box.BoxResponseDto
import org.homesharing.lockbox.data.network.dto.box_status.BoxStatusResponseDto
import org.homesharing.lockbox.data.network.dto.create_linking_code.CreateLinkingCodeDto
import org.homesharing.lockbox.data.network.dto.mockbox.MockboxDto
import org.homesharing.lockbox.data.network.dto.open_box.OpenBoxResponseDto
import org.homesharing.lockbox.data.network.dto.update_box.UpdateBoxResponseDto

internal interface BoxesApi {

    suspend fun getBoxes(
        accessToken: String,
        page: Int,
        size: Int
    ): ApiResponse<BoxListResponseDto>

    suspend fun getBoxByMac(
        accessToken: String,
        macAddress: String
    ): ApiResponse<BoxResponseDto>

    suspend fun getBoxStatusByMac(
        accessToken: String,
        macAddress: String
    ): ApiResponse<BoxStatusResponseDto>

    suspend fun updateBoxByMac(
        accessToken: String,
        macAddress: String,
        alias: String? = null,
        address: String? = null
    ): ApiResponse<UpdateBoxResponseDto>

    suspend fun openBox(
        accessToken: String,
        macAddress: String
    ): ApiResponse<OpenBoxResponseDto>

    suspend fun deleteBox(
        accessToken: String,
        macAddress: String
    ): ApiResponse<UpdateBoxResponseDto>

    suspend fun getAccessedList(
        accessToken: String,
        macAddress: String
    ): ApiResponse<AccessResponseDto>

    suspend fun createAccess(
        accessToken: String,
        macAddress: String,
        alias: String?,
        startTime: String,
        endTime: String,
    ): ApiResponse<AccessDto>

    suspend fun revokeAccess(
        accessToken: String,
        macAddress: String,
        tokenId: String
    ): ApiResponse<AccessRevokeResponseDto>

    suspend fun createLinkingCode(
        accessToken: String,
    ): ApiResponse<CreateLinkingCodeDto>

    suspend fun physicalSignUp(
        accessToken: String,
        code: Int
    ): ApiResponse<MockboxDto>
}
