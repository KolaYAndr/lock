package org.homesharing.lockbox.data.network.api

import io.ktor.client.HttpClient
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.client.request.patch
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.HttpHeaders
import io.ktor.http.parameters
import kotlinx.serialization.json.Json
import org.homesharing.lockbox.data.network.api.response.ApiResponse
import org.homesharing.lockbox.data.network.api.response.toApiResponse
import org.homesharing.lockbox.data.network.client.bearerToken
import org.homesharing.lockbox.data.network.dto.access_revoke.AccessRevokeResponseDto
import org.homesharing.lockbox.data.network.dto.access.AccessDto
import org.homesharing.lockbox.data.network.dto.access.AccessResponseDto
import org.homesharing.lockbox.data.network.dto.box_list.BoxListResponseDto
import org.homesharing.lockbox.data.network.dto.box_status.BoxStatusResponseDto
import org.homesharing.lockbox.data.network.dto.open_box.OpenBoxResponseDto
import org.homesharing.lockbox.data.network.dto.update_box.UpdateBoxRequestDto
import org.homesharing.lockbox.data.network.dto.update_box.UpdateBoxResponseDto
import org.homesharing.lockbox.domain.api.BoxesApi
import org.homesharing.lockbox.data.network.dto.access_create.AccessCreateRequestDto
import org.homesharing.lockbox.data.network.dto.box.BoxWithStatusResponseDto
import org.homesharing.lockbox.data.network.dto.create_linking_code.CreateLinkingCodeDto
import org.homesharing.lockbox.data.network.dto.physical_sign_up.PhysicalSignUpRequestDto
import org.homesharing.lockbox.data.network.dto.physical_sign_up.PhysicalSignUpResponseDto

//internal const val BOXES_URL = "$BASE_ENDPOINT/api/v1"
internal const val BOXES_URL = "http://10.0.2.2:8080/api/v1"
internal const val DEVICES = "devices"
internal const val BOXES = "boxes"
internal const val STATUS = "status"
internal const val AUTH = "auth"
internal const val OPEN = "open"
internal const val ACCESS = "access"
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
        accessToken: String,
        page: Int,
        size: Int,
    ): ApiResponse =
        client.get {
            header(HttpHeaders.Authorization, bearerToken(accessToken))
            url("$BOXES_URL/$BOXES")
            parameters {
                parameter(PAGE_PARAMETER, page)
                parameter(SIZE_PARAMETER, size)
            }
        }.toApiResponse<BoxListResponseDto>(json)

    override suspend fun getBoxByMac(
        accessToken: String,
        macAddress: String,
    ): ApiResponse =
        client.get {
            header(HttpHeaders.Authorization, bearerToken(accessToken))
            url("$BOXES_URL/$BOXES/$macAddress")
        }.toApiResponse<BoxWithStatusResponseDto>(json)

    override suspend fun getBoxStatusByMac(
        accessToken: String,
        macAddress: String,
    ): ApiResponse =
        client.get {
            header(HttpHeaders.Authorization, bearerToken(accessToken))
            url("$BOXES_URL/$BOXES/$macAddress/$STATUS")
        }.toApiResponse<BoxStatusResponseDto>(json)

    override suspend fun updateBoxByMac(
        accessToken: String,
        macAddress: String,
        alias: String?,
        address: String?,
    ): ApiResponse {
        val request = UpdateBoxRequestDto(alias, address)
        return client.patch {
            header(HttpHeaders.Authorization, bearerToken(accessToken))
            url("$BOXES_URL/$BOXES/$macAddress")
            setBody(request)
        }.toApiResponse<UpdateBoxResponseDto>(json)
    }

    override suspend fun openBox(
        accessToken: String,
        macAddress: String,
    ): ApiResponse =
        client.post {
            header(HttpHeaders.Authorization, bearerToken(accessToken))
            url("$BOXES_URL/$BOXES/$AUTH/$OPEN/$macAddress")
        }.toApiResponse<OpenBoxResponseDto>(json)

    override suspend fun deleteBox(
        accessToken: String,
        macAddress: String,
    ): ApiResponse =
        client.delete {
            header(HttpHeaders.Authorization, bearerToken(accessToken))
            url("$BOXES_URL/$BOXES/$macAddress")
        }.toApiResponse<UpdateBoxResponseDto>(json)

    override suspend fun getAccessedList(
        accessToken: String,
        macAddress: String,
    ): ApiResponse =
        client.get {
            header(HttpHeaders.Authorization, bearerToken(accessToken))
            url("$BOXES_URL/$BOXES/$ACCESS/$macAddress/$ACCESSES")
        }.toApiResponse<AccessResponseDto>(json)

    override suspend fun createAccess(
        accessToken: String,
        macAddress: String,
        alias: String,
        startTime: String,
        endTime: String,
    ): ApiResponse {
        val request = AccessCreateRequestDto(startTime, endTime, alias)
        return client.post {
            header(HttpHeaders.Authorization, bearerToken(accessToken))
            url("$BOXES_URL/$BOXES/$ACCESS/$macAddress/$ACCESSES")
            setBody(request)
        }.toApiResponse<AccessDto>(json)
    }

    override suspend fun revokeAccess(
        accessToken: String,
        macAddress: String,
        tokenId: String,
    ): ApiResponse =
        client.delete {
            header(HttpHeaders.Authorization, bearerToken(accessToken))
            url("$BOXES_URL/$BOXES/$ACCESS/$macAddress/$ACCESSES/$tokenId")
        }.toApiResponse<AccessRevokeResponseDto>(json)

    override suspend fun createLinkingCode(accessToken: String): ApiResponse =
        client.post {
            url("$BOXES_URL/$SIGNUP")
            header(HttpHeaders.Authorization, bearerToken(accessToken))
        }.toApiResponse<CreateLinkingCodeDto>(json)

    override suspend fun physicalSignUp(
        accessToken: String,
        code: Int,
        macAddress: String
    ): ApiResponse {
        val request = PhysicalSignUpRequestDto(code, macAddress)
        return client.post {
            url("$BOXES_URL/$DEVICES/$BOXES/$SIGNUP")
            header(HttpHeaders.Authorization, bearerToken(accessToken))
            setBody(request)
        }.toApiResponse<PhysicalSignUpResponseDto>(json)
    }

    override suspend fun createTestDevice(accessToken: String): ApiResponse =
        client.post {
            url("$BOXES_URL/$DEVICES/$BOXES/$TEST")
            header(HttpHeaders.Authorization, bearerToken(accessToken))
        }.toApiResponse<BoxWithStatusResponseDto>(json)
}
