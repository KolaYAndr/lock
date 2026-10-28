package org.homesharing.lockbox.data.network.api

import io.ktor.client.HttpClient
import io.ktor.client.request.forms.formData
import io.ktor.client.request.forms.submitFormWithBinaryData
import io.ktor.client.request.get
import io.ktor.client.request.patch
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import kotlinx.serialization.json.Json
import org.homesharing.lockbox.data.network.api.response.ApiResponse
import org.homesharing.lockbox.data.network.api.response.toApiResponse
import org.homesharing.lockbox.data.network.client.GO_BASE_ENDPOINT
import org.homesharing.lockbox.data.network.client.KEYCLOACK_BASE_ENDPOINT
import org.homesharing.lockbox.data.network.dto.avatar.AvatarDto
import org.homesharing.lockbox.data.network.dto.profile_info.ContactInfoRequest
import org.homesharing.lockbox.data.network.dto.profile_info.LegalInfoRequest
import org.homesharing.lockbox.data.network.dto.profile_info.PassportRequest
import org.homesharing.lockbox.data.network.dto.profile_info.ProfileInfoDto
import org.homesharing.lockbox.data.network.dto.profile_info.ProfileInfoRequest
import org.homesharing.lockbox.data.network.dto.sessions.SessionsDto
import org.homesharing.lockbox.data.network.dto.update_account.UpdateAccountDto
import org.homesharing.lockbox.data.network.dto.update_account.UpdateAccountRequest

private const val ACCOUNTS = "accounts"
private const val AVATARS = "avatars"
private const val SESSIONS = "sessions"
private const val LOGOUT = "logout"
private const val UPLOAD = "upload"
private const val DOWNLOAD = "download"
private const val ME = "me"
private const val PROFILE_INFO = "profile-info"

private const val SESSIONS_URL = "$GO_BASE_ENDPOINT/$SESSIONS"
private const val AVATARS_POST_URL = "$GO_BASE_ENDPOINT/$AVATARS/$UPLOAD"
private const val AVATARS_URL = "$GO_BASE_ENDPOINT/$AVATARS/$DOWNLOAD"
private const val UPDATE_ACCOUNT_URL = "$KEYCLOACK_BASE_ENDPOINT:8445/$ACCOUNTS/$ME"
private const val PROFILE_INFO_URL = "$GO_BASE_ENDPOINT/$PROFILE_INFO"

internal class AccountApiImpl(
    private val client: HttpClient,
    private val json: Json,
) : AccountApi {
    override suspend fun getAvatar(): ApiResponse<AvatarDto> {
        return client.get {
            url(AVATARS_URL)
        }.toApiResponse<AvatarDto>(json)
    }

    override suspend fun sendImage(data: ByteArray): ApiResponse<Unit> {
        return client.submitFormWithBinaryData(
            url = AVATARS_POST_URL,
            formData = formData {
                append(
                    key = "avatar",
                    value = data,
                    headers = Headers.build {
                        append(HttpHeaders.ContentType, "image/png")
                        append(HttpHeaders.ContentDisposition, "filename=\"avatar.png\"")
                    }
                )
            }
        ).toApiResponse<Unit>(json)
    }

    override suspend fun getSessions(): ApiResponse<SessionsDto> =
        client.get {
            url(SESSIONS_URL)
        }.toApiResponse<SessionsDto>(json)

    override suspend fun deactivateUser(): ApiResponse<UpdateAccountDto> {
        val request = UpdateAccountRequest(null, false, null, null)
        return client.patch {
            url(UPDATE_ACCOUNT_URL)
            setBody(request)
        }.toApiResponse<UpdateAccountDto>(json)
    }

    override suspend fun closeOtherSessions(): ApiResponse<Unit> {
        return client.post {
            url("$SESSIONS_URL/$LOGOUT")
        }.toApiResponse<Unit>(json)
    }

    override suspend fun getProfileInfo(): ApiResponse<ProfileInfoDto> {
        return client.get {
            url(PROFILE_INFO_URL)
        }.toApiResponse<ProfileInfoDto>(json)
    }

    override suspend fun updateProfileInfo(
        email: String?,
        phoneNumber: String?,
        fullName: String?,
        livingAddress: String?,
        passportIssuer: String?,
        passportNumber: String?,
        passportSeries: String?
    ): ApiResponse<Unit> {
        val request = ProfileInfoRequest(
            contactInfo = ContactInfoRequest(
                email = email,
                phoneNumber = phoneNumber
            ),
            legalInfo = LegalInfoRequest(
                fullName = fullName,
                livingAddress = livingAddress,
                passport = PassportRequest(
                    issuer = passportIssuer,
                    passportNumber = passportNumber,
                    passportSeries = passportSeries
                )
            )
        )
        return client.patch {
            url(PROFILE_INFO_URL)
            setBody(request)
        }.toApiResponse<Unit>(json)
    }
}
