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

private const val BOXES = "https://api.lockboxes.ru:8445/boxes"
private const val STATUS = "status"
private const val AUTH = "auth"
private const val OPEN = "open"
private const val ACCESS = "access"
private const val ACCESSES = "accesses"

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
            url(BOXES)
            parameters {
                parameter(SIZE_PARAMETER, size)
                parameter(PAGE_PARAMETER, page)
            }
        }.body()

    override suspend fun getBoxByMac(
        accessToken: String,
        address: String,
    ): ApiResponse<BoxResponseDto> =
        client.get {
            header(HttpHeaders.Authorization, bearerToken(accessToken))
            url("$BOXES/$address")
        }.body()

    override suspend fun getBoxStatusByMac(
        accessToken: String,
        address: String,
    ): ApiResponse<BoxStatusResponseDto> =
        client.get {
            header(HttpHeaders.Authorization, bearerToken(accessToken))
            url("$BOXES/$address/$STATUS")
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
            url("$BOXES/$macAddress")
            setBody(request)
        }.body()
    }

    override suspend fun openBox(
        accessToken: String,
        mac: String,
    ): ApiResponse<OpenBoxResponseDto> =
        client.post {
            header(HttpHeaders.Authorization, bearerToken(accessToken))
            url("$BOXES/$AUTH/$OPEN/$mac")
        }.body()

    override suspend fun deleteBox(
        accessToken: String,
        mac: String,
    ): ApiResponse<UpdateBoxResponseDto> =
        client.delete {
            header(HttpHeaders.Authorization, bearerToken(accessToken))
            url("$BOXES/$mac")
        }.body()

    override suspend fun getAccessedList(
        accessToken: String,
        mac: String,
    ): ApiResponse<AccessResponseDto> =
        client.get {
            header(HttpHeaders.Authorization, bearerToken(accessToken))
            url("$BOXES/$ACCESS/$mac/$ACCESSES")
        }.body()

    override suspend fun createAccess(
        accessToken: String,
        mac: String,
        alias: String?,
        startTime: String,
        endTime: String,
    ): ApiResponse<AccessDto> {
        val request = AccessCreateRequestDto(startTime, endTime, alias)
        return client.post {
            header(HttpHeaders.Authorization, bearerToken(accessToken))
            url("$BOXES/$ACCESS/$mac/$ACCESSES")
            setBody(request)
        }.body()
    }

    override suspend fun revokeAccess(
        accessToken: String,
        mac: String,
        tokenId: String,
    ): ApiResponse<AccessRevokeResponseDto> =
        client.delete {
            header(HttpHeaders.Authorization, bearerToken(accessToken))
            url("$BOXES/$ACCESS/$mac/$ACCESSES/$tokenId")
        }.body()
}
