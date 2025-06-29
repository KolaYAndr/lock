package org.homesharing.lockbox.data.network.api

import io.ktor.client.HttpClient
import io.ktor.client.call.body
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
import org.homesharing.lockbox.data.network.client.bearerToken
import org.homesharing.lockbox.data.network.dto.access_revoke.AccessRevokeResponseDto
import org.homesharing.lockbox.data.network.dto.accessed.AccessDto
import org.homesharing.lockbox.data.network.dto.accessed.AccessResponseDto
import org.homesharing.lockbox.data.network.dto.box_list.BoxListResponseDto
import org.homesharing.lockbox.data.network.dto.box.BoxResponseDto
import org.homesharing.lockbox.data.network.dto.box_status.BoxStatusResponseDto
import org.homesharing.lockbox.data.network.dto.open_box.OpenBoxResponseDto
import org.homesharing.lockbox.data.network.dto.update_box.UpdateBoxRequestDto
import org.homesharing.lockbox.data.network.dto.update_box.UpdateBoxResponseDto
import org.homesharing.lockbox.domain.api.BoxesApi
import org.homesharing.lockbox.data.network.dto.access_create.AccessCreateRequestDto

internal const val BOXES_URL = "https://api.lockboxes.ru:8445/boxes"
internal const val STATUS = "status"
internal const val AUTH = "auth"
internal const val OPEN = "open"
internal const val ACCESS = "access"
internal const val ACCESSES = "accesses"

private const val PAGE_PARAMETER = "page"
private const val SIZE_PARAMETER = "size"

internal class LockBoxBoxesApi(private val client: HttpClient) : BoxesApi {

    override suspend fun getBoxes(
        accessToken: String,
        page: Int,
        size: Int,
    ): ApiResponse<BoxListResponseDto> =
        client.get {
            header(HttpHeaders.Authorization, bearerToken(accessToken))
            url(BOXES_URL)
            parameters {
                parameter(SIZE_PARAMETER, size)
                parameter(PAGE_PARAMETER, page)
            }
        }.body()

    override suspend fun getBoxByMac(
        accessToken: String,
        macAddress: String,
    ): ApiResponse<BoxResponseDto> =
        client.get {
            header(HttpHeaders.Authorization, bearerToken(accessToken))
            url("$BOXES_URL/$macAddress")
        }.body()

    override suspend fun getBoxStatusByMac(
        accessToken: String,
        macAddress: String,
    ): ApiResponse<BoxStatusResponseDto> =
        client.get {
            header(HttpHeaders.Authorization, bearerToken(accessToken))
            url("$BOXES_URL/$macAddress/$STATUS")
        }.body()

    override suspend fun updateBoxByMac(
        accessToken: String,
        macAddress: String,
        alias: String?,
        address: String?,
    ): ApiResponse<UpdateBoxResponseDto> {
        val request = UpdateBoxRequestDto(alias, address)
        return client.patch {
            header(HttpHeaders.Authorization, bearerToken(accessToken))
            url("$BOXES_URL/$macAddress")
            setBody(request)
        }.body()
    }

    override suspend fun openBox(
        accessToken: String,
        macAddress: String,
    ): ApiResponse<OpenBoxResponseDto> =
        client.post {
            header(HttpHeaders.Authorization, bearerToken(accessToken))
            url("$BOXES_URL/$AUTH/$OPEN/$macAddress")
        }.body()

    override suspend fun deleteBox(
        accessToken: String,
        macAddress: String,
    ): ApiResponse<UpdateBoxResponseDto> =
        client.delete {
            header(HttpHeaders.Authorization, bearerToken(accessToken))
            url("$BOXES_URL/$macAddress")
        }.body()

    override suspend fun getAccessedList(
        accessToken: String,
        macAddress: String,
    ): ApiResponse<AccessResponseDto> =
        client.get {
            header(HttpHeaders.Authorization, bearerToken(accessToken))
            url("$BOXES_URL/$ACCESS/$macAddress/$ACCESSES")
        }.body()

    override suspend fun createAccess(
        accessToken: String,
        macAddress: String,
        alias: String?,
        startTime: String,
        endTime: String,
    ): ApiResponse<AccessDto> {
        val request = AccessCreateRequestDto(startTime, endTime, alias)
        return client.post {
            header(HttpHeaders.Authorization, bearerToken(accessToken))
            url("$BOXES_URL/$ACCESS/$macAddress/$ACCESSES")
            setBody(request)
        }.body()
    }

    override suspend fun revokeAccess(
        accessToken: String,
        macAddress: String,
        tokenId: String,
    ): ApiResponse<AccessRevokeResponseDto> =
        client.delete {
            header(HttpHeaders.Authorization, bearerToken(accessToken))
            url("$BOXES_URL/$ACCESS/$macAddress/$ACCESSES/$tokenId")
        }.body()
}
