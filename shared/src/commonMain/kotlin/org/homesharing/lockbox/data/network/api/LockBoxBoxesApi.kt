package org.homesharing.lockbox.data.network.api

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.patch
import io.ktor.client.request.url
import io.ktor.client.statement.bodyAsText
import io.ktor.http.parameters
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import org.homesharing.lockbox.domain.api.BoxesApi
import org.homesharing.lockbox.data.network.model.BoxListResponse
import org.homesharing.lockbox.data.network.model.BoxResponse
import org.homesharing.lockbox.data.network.model.BoxStatusResponse

private const val BASE_URL = "https://api.lockboxes.ru:8445/boxes"
private const val WEB = "web"
private const val ACCESS = "access"
private const val MAC = "mac"
private const val STATUS = "status"

private const val PAGE_PARAMETER = "page"
private const val SIZE_PARAMETER = "size"

class LockBoxBoxesApi(private val client: HttpClient) : BoxesApi {
    override suspend fun createCode() {
        TODO("Not yet implemented")
    }

    override suspend fun generate() {
        TODO("Not yet implemented")
    }

    override suspend fun getBoxes(page: Int, size: Int): BoxListResponse {
        return client.get {
            url(BASE_URL)
            parameters {
                parameter(SIZE_PARAMETER, size)
                parameter(PAGE_PARAMETER, page)
            }
        }.body()
    }

    override suspend fun getBoxByMac(address: String): BoxResponse {
        val response = client.get {
            url("$BASE_URL/$WEB/$MAC/$address")
        }.bodyAsText()

        return try {
            Json.decodeFromString<BoxResponse.Success>(response)
        } catch (e: SerializationException) {
            Json.decodeFromString<BoxResponse.Error>(response)
        }
    }

    override suspend fun getBoxStatusByMac(address: String): BoxStatusResponse {
        return client.get {
            url("$BASE_URL/$WEB/$MAC/$address/$STATUS")
        }.body()
    }

    override suspend fun updateBoxByMac(address: String): BoxResponse {
        val response =  client.patch {
            url("$BASE_URL/$WEB/$MAC/$address")
        }.bodyAsText()

        return try {
            Json.decodeFromString<BoxResponse.Success>(response)
        } catch (e: SerializationException) {
            Json.decodeFromString<BoxResponse.Error>(response)
        }
    }

    override suspend fun openBox(mac: String) {
        TODO("Not yet implemented")
    }

    override suspend fun createShareToken() {
        TODO("Not yet implemented")
    }
}
