package org.homesharing.lockbox.data.network.api

import io.ktor.client.HttpClient
import io.ktor.client.request.forms.formData
import io.ktor.client.request.forms.submitFormWithBinaryData
import io.ktor.client.request.get
import io.ktor.client.request.patch
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import kotlinx.serialization.json.Json
import org.homesharing.lockbox.data.network.api.response.ApiResponse
import org.homesharing.lockbox.data.network.api.response.toApiResponse
import org.homesharing.lockbox.data.network.client.GO_BASE_ENDPOINT
import org.homesharing.lockbox.data.network.client.KEYCLOACK_BASE_ENDPOINT
import org.homesharing.lockbox.data.network.dto.active_role.UpdateActiveRoleRequestDto
import org.homesharing.lockbox.data.network.dto.avatar.AvatarDto
import org.homesharing.lockbox.data.network.dto.profile_info.ProfileInfoDto
import org.homesharing.lockbox.data.network.dto.profile_info.ProfileInfoRequestDto
import org.homesharing.lockbox.data.network.dto.role.UserRoleDto
import org.homesharing.lockbox.data.network.dto.update_account.UpdateAccountDto
import org.homesharing.lockbox.data.network.dto.update_account.UpdateAccountRequestDto

private const val ACCOUNTS = "accounts"
private const val AVATARS = "avatars"
private const val UPLOAD = "upload"
private const val DOWNLOAD = "download"
private const val ME = "me"
private const val PROFILE_INFO = "profile-info"
private const val LANDLORD = "landlord"
private const val ACTIVE_ROLE = "active-role"

private const val AVATARS_POST_URL = "$GO_BASE_ENDPOINT/$AVATARS/$UPLOAD"
private const val AVATARS_URL = "$GO_BASE_ENDPOINT/$AVATARS/$DOWNLOAD"
private const val UPDATE_ACCOUNT_URL = "$KEYCLOACK_BASE_ENDPOINT:8445/$ACCOUNTS/$ME"
private const val PROFILE_INFO_URL = "$GO_BASE_ENDPOINT/$PROFILE_INFO"
private const val PROFILE_INFO_LANDLORD_URL = "$PROFILE_INFO_URL/$LANDLORD"
private const val PROFILE_INFO_ACTIVE_ROLE_URL = "$PROFILE_INFO_URL/$ACTIVE_ROLE"

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

    override suspend fun deactivateUser(): ApiResponse<UpdateAccountDto> {
        val request = UpdateAccountRequestDto(null, false, null, null)
        return client.patch {
            url(UPDATE_ACCOUNT_URL)
            setBody(request)
        }.toApiResponse<UpdateAccountDto>(json)
    }

    override suspend fun getProfileInfo(): ApiResponse<ProfileInfoDto> {
        return client.get {
            url(PROFILE_INFO_URL)
        }.toApiResponse<ProfileInfoDto>(json)
    }

    override suspend fun updateProfileInfo(
        request: ProfileInfoRequestDto
    ): ApiResponse<ProfileInfoDto> {
        return client.post {
            url(PROFILE_INFO_URL)
            setBody(request)
        }.toApiResponse<ProfileInfoDto>(json)
    }

    override suspend fun becomeLandlord(): ApiResponse<ProfileInfoDto> {
        return client.post {
            url(PROFILE_INFO_LANDLORD_URL)
        }.toApiResponse<ProfileInfoDto>(json)
    }

    override suspend fun updateActiveRole(
        role: UserRoleDto
    ): ApiResponse<ProfileInfoDto> {
        val request = UpdateActiveRoleRequestDto(role = role)
        return client.put {
            url(PROFILE_INFO_ACTIVE_ROLE_URL)
            setBody(request)
        }.toApiResponse<ProfileInfoDto>(json)
    }
}
