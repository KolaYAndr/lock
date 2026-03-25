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
import org.homesharing.lockbox.data.network.client.INGRESS_BASE_ENDPOINT
import org.homesharing.lockbox.data.network.dto.access.AccessDto
import org.homesharing.lockbox.data.network.dto.access.AccessResponseDto
import org.homesharing.lockbox.data.network.dto.access_create.AccessCreateRequestDto
import org.homesharing.lockbox.data.network.dto.access_revoke.AccessRevokeResponseDto
import org.homesharing.lockbox.data.network.dto.box.BoxWithStatusResponseDto
import org.homesharing.lockbox.data.network.dto.box_list.BoxListResponseDto
import org.homesharing.lockbox.data.network.dto.create_linking_code.CreateLinkingCodeDto
import org.homesharing.lockbox.data.network.dto.open_box.OpenBoxResponseDto
import org.homesharing.lockbox.data.network.dto.physical_sign_up.PhysicalSignUpRequestDto
import org.homesharing.lockbox.data.network.dto.physical_sign_up.PhysicalSignUpResponseDto
import org.homesharing.lockbox.data.network.dto.update_box.UpdateBoxRequestDto
import org.homesharing.lockbox.data.network.dto.update_box.UpdateBoxResponseDto
import org.homesharing.lockbox.domain.api.BoxesApi

internal const val DEVICES = "devices"
internal const val BOXES = "boxes"
internal const val ACCESSES = "accesses"
internal const val SIGNUP = "signup"
internal const val TEST = "test"

private const val PAGE_PARAMETER = "page"
private const val SIZE_PARAMETER = "size"

internal class LockBoxBoxesApi(
    private val client: HttpClient,
    private val json: Json,
) : BoxesApi {

    override suspend fun getBoxes(
        page: Int,
        size: Int,
    ): ApiResponse =
        client.get {
            url("$INGRESS_BASE_ENDPOINT/$BOXES")
            parameter(PAGE_PARAMETER, page)
            parameter(SIZE_PARAMETER, size)
        }.toApiResponse<BoxListResponseDto>(json)

    override suspend fun getBoxByMac(
        macAddress: String,
    ): ApiResponse =
        client.get {
            url("$INGRESS_BASE_ENDPOINT/$BOXES/$macAddress")
        }.toApiResponse<BoxWithStatusResponseDto>(json)

    override suspend fun updateBoxByMac(
        macAddress: String,
        ownerSub: String,
        alias: String?,
        address: String?,
    ): ApiResponse {
        val request = UpdateBoxRequestDto(
            alias = alias,
            address = address,
            ownerSub = ownerSub,
            macAddress = macAddress,
        )
        return client.patch {
            url("$INGRESS_BASE_ENDPOINT/$BOXES/$macAddress")
            setBody(request)
        }.toApiResponse<UpdateBoxResponseDto>(json)
    }

    override suspend fun openBox(
        macAddress: String,
    ): ApiResponse =
        client.post {
            url("$INGRESS_BASE_ENDPOINT/$BOXES/$macAddress")
        }.toApiResponse<OpenBoxResponseDto>(json)

    override suspend fun deleteBox(
        macAddress: String,
    ): ApiResponse =
        client.delete {
            url("$INGRESS_BASE_ENDPOINT/$BOXES/$macAddress")
        }.toApiResponse<Unit>(json)

    override suspend fun getAccessedList(
        macAddress: String,
    ): ApiResponse =
        client.get {
            url("$INGRESS_BASE_ENDPOINT/$BOXES/$macAddress/$ACCESSES")
        }.toApiResponse<AccessResponseDto>(json)

    override suspend fun createAccess(
        macAddress: String,
        alias: String,
        startTime: String,
        endTime: String,
    ): ApiResponse {
        val request = AccessCreateRequestDto(startTime, endTime, alias)
        return client.post {
            url("$INGRESS_BASE_ENDPOINT/$BOXES/$macAddress/$ACCESSES")
            setBody(request)
        }.toApiResponse<AccessDto>(json)
    }

    override suspend fun revokeAccess(
        macAddress: String,
        tokenId: String,
    ): ApiResponse =
        client.delete {
            url("$INGRESS_BASE_ENDPOINT/$BOXES/$macAddress/$ACCESSES/$tokenId")
        }.toApiResponse<AccessRevokeResponseDto>(json)

    override suspend fun createLinkingCode(): ApiResponse =
        client.post {
            url("$INGRESS_BASE_ENDPOINT/$SIGNUP")
        }.toApiResponse<CreateLinkingCodeDto>(json)

    override suspend fun physicalSignUp(
        code: Int,
        macAddress: String,
    ): ApiResponse {
        val request = PhysicalSignUpRequestDto(code, macAddress)
        return client.post {
            url("$INGRESS_BASE_ENDPOINT/$DEVICES/$BOXES/$SIGNUP")
            setBody(request)
        }.toApiResponse<PhysicalSignUpResponseDto>(json)
    }

    override suspend fun createTestDevice(): ApiResponse =
        client.post {
            url("$INGRESS_BASE_ENDPOINT/$DEVICES/$BOXES/$TEST")
        }.toApiResponse<Unit>(json)
}
