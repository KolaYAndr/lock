package org.homesharing.lockbox.data.network.api

import io.ktor.client.HttpClient
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.patch
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import kotlinx.serialization.json.Json
import org.homesharing.lockbox.data.network.api.response.ApiResponse
import org.homesharing.lockbox.data.network.api.response.toApiResponse
import org.homesharing.lockbox.data.network.client.KOTLIN_BASE_ENDPOINT
import org.homesharing.lockbox.data.network.dto.access.AccessDto
import org.homesharing.lockbox.data.network.dto.access.AccessesDto
import org.homesharing.lockbox.data.network.dto.access_create.AccessCreateRequest
import org.homesharing.lockbox.data.network.dto.access_revoke.AccessRevokeDto
import org.homesharing.lockbox.data.network.dto.box.BoxWithStatusDto
import org.homesharing.lockbox.data.network.dto.box_list.BoxListDto
import org.homesharing.lockbox.data.network.dto.create_linking_code.CreateLinkingCodeDto
import org.homesharing.lockbox.data.network.dto.open_box.OpenBoxDto
import org.homesharing.lockbox.data.network.dto.physical_sign_up.PhysicalSignUpDto
import org.homesharing.lockbox.data.network.dto.physical_sign_up.PhysicalSignUpRequest
import org.homesharing.lockbox.data.network.dto.update_box.UpdateBoxDto
import org.homesharing.lockbox.data.network.dto.update_box.UpdateBoxRequest

private const val DEVICES = "devices"
private const val BOXES = "boxes"
private const val ACCESSES = "accesses"
private const val SIGNUP = "signup"
private const val TEST = "test"

private const val PAGE_PARAMETER = "page"
private const val SIZE_PARAMETER = "size"

internal class LockBoxBoxesApi(
    private val client: HttpClient,
    private val json: Json,
) : BoxesApi {

    override suspend fun getBoxes(
        page: Int,
        size: Int,
    ): ApiResponse<BoxListDto> =
        client.get {
            url("$KOTLIN_BASE_ENDPOINT/$BOXES")
            parameter(PAGE_PARAMETER, page)
            parameter(SIZE_PARAMETER, size)
        }.toApiResponse<BoxListDto>(json)

    override suspend fun getBoxByMac(
        macAddress: String,
    ): ApiResponse<BoxWithStatusDto> =
        client.get {
            url("$KOTLIN_BASE_ENDPOINT/$BOXES/$macAddress")
        }.toApiResponse<BoxWithStatusDto>(json)

    override suspend fun updateBoxByMac(
        macAddress: String,
        ownerSub: String,
        alias: String?,
        address: String?,
    ): ApiResponse<UpdateBoxDto> {
        val request = UpdateBoxRequest(
            alias = alias,
            address = address,
            ownerSub = ownerSub,
            macAddress = macAddress,
        )
        return client.patch {
            url("$KOTLIN_BASE_ENDPOINT/$BOXES/$macAddress")
            setBody(request)
        }.toApiResponse<UpdateBoxDto>(json)
    }

    override suspend fun openBox(
        macAddress: String,
    ): ApiResponse<OpenBoxDto> =
        client.post {
            url("$KOTLIN_BASE_ENDPOINT/$BOXES/$macAddress")
        }.toApiResponse<OpenBoxDto>(json)

    override suspend fun deleteBox(
        macAddress: String,
    ): ApiResponse<Unit> =
        client.delete {
            url("$KOTLIN_BASE_ENDPOINT/$BOXES/$macAddress")
        }.toApiResponse<Unit>(json)

    override suspend fun getAccessedList(
        macAddress: String,
    ): ApiResponse<AccessesDto> =
        client.get {
            url("$KOTLIN_BASE_ENDPOINT/$BOXES/$macAddress/$ACCESSES")
        }.toApiResponse<AccessesDto>(json)

    override suspend fun createAccess(
        macAddress: String,
        alias: String,
        startTime: String,
        endTime: String,
    ): ApiResponse<AccessDto> {
        val request = AccessCreateRequest(startTime, endTime, alias)
        return client.post {
            url("$KOTLIN_BASE_ENDPOINT/$BOXES/$macAddress/$ACCESSES")
            setBody(request)
        }.toApiResponse<AccessDto>(json)
    }

    override suspend fun revokeAccess(
        macAddress: String,
        tokenId: String,
    ): ApiResponse<AccessRevokeDto> =
        client.delete {
            url("$KOTLIN_BASE_ENDPOINT/$BOXES/$macAddress/$ACCESSES/$tokenId")
        }.toApiResponse<AccessRevokeDto>(json)

    override suspend fun createLinkingCode(): ApiResponse<CreateLinkingCodeDto> =
        client.post {
            url("$KOTLIN_BASE_ENDPOINT/$SIGNUP")
        }.toApiResponse<CreateLinkingCodeDto>(json)

    override suspend fun physicalSignUp(
        code: Int,
        macAddress: String,
    ): ApiResponse<PhysicalSignUpDto> {
        val request = PhysicalSignUpRequest(code, macAddress)
        return client.post {
            url("$KOTLIN_BASE_ENDPOINT/$DEVICES/$BOXES/$SIGNUP")
            setBody(request)
        }.toApiResponse<PhysicalSignUpDto>(json)
    }

    override suspend fun createTestDevice(): ApiResponse<Unit> =
        client.post {
            url("$KOTLIN_BASE_ENDPOINT/$DEVICES/$BOXES/$TEST")
        }.toApiResponse<Unit>(json)
}
