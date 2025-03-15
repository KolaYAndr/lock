package org.homesharing.lockbox.data.network.api

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.patch
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.parameters
import org.homesharing.lockbox.data.network.dto.box_list.BoxListResponseDto
import org.homesharing.lockbox.data.network.dto.box.BoxResponseDto
import org.homesharing.lockbox.data.network.dto.box_status.BoxStatusResponseDto
import org.homesharing.lockbox.data.network.dto.open_box.OpenBoxResponseDto
import org.homesharing.lockbox.data.network.dto.update_box.UpdateBoxRequestDto
import org.homesharing.lockbox.data.network.dto.update_box.UpdateBoxResponseDto
import org.homesharing.lockbox.domain.api.BoxesApi

private const val BOXES = "https://api.lockboxes.ru:8445/boxes"
private const val STATUS = "status"
private const val AUTH = "auth"
private const val OPEN = "open"

private const val PAGE_PARAMETER = "page"
private const val SIZE_PARAMETER = "size"

class LockBoxBoxesApi(private val client: HttpClient) : BoxesApi {
    override suspend fun getBoxes(page: Int, size: Int): ApiResponse<BoxListResponseDto> {
        return client.get {
            url(BOXES)
            parameters {
                parameter(SIZE_PARAMETER, size)
                parameter(PAGE_PARAMETER, page)
            }
        }.body()
    }

    override suspend fun getBoxByMac(address: String): ApiResponse<BoxResponseDto> {
        return client.get {
            url("$BOXES/$address")
        }.body()
    }

    override suspend fun getBoxStatusByMac(address: String): ApiResponse<BoxStatusResponseDto> {
        return client.get {
            url("$BOXES/$address/$STATUS")
        }.body()
    }

    override suspend fun updateBoxByMac(
        address: String,
        request: UpdateBoxRequestDto
    ): ApiResponse<UpdateBoxResponseDto> {
        return client.patch {
            url("$BOXES/$address")
            setBody(request)
        }.body()
    }

    override suspend fun openBox(mac: String): ApiResponse<OpenBoxResponseDto> {
        return client.post {
            url("$BOXES/$AUTH/$OPEN/$mac")
        }.body()
    }

    override suspend fun deleteBox(mac: String): ApiResponse<UpdateBoxResponseDto> {
        return client.delete {
            url("$BOXES/$mac")
        }.body()
    }
}
