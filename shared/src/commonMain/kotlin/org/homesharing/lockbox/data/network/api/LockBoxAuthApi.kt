package org.homesharing.lockbox.data.network.api

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import org.homesharing.lockbox.data.network.model.SignUpRequest
import org.homesharing.lockbox.data.network.model.SignUpResponse

private const val ACCOUNTS = "accounts"


class LockBoxApi(private val client: HttpClient) {
    suspend fun singUp(request: SignUpRequest): SignUpResponse {
        return client.post {
            url("signup")
            setBody(
                request
            )
        }.body()
    }
}