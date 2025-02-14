package org.homesharing.lockbox.domain.api

import org.homesharing.lockbox.data.network.model.AvatarResponse
import org.homesharing.lockbox.data.network.model.InitialTokenLoadResponse
import org.homesharing.lockbox.data.network.model.SignUpResponse

interface AccountApi {
    suspend fun signUp(username: String, password: String, passwordRepeated: String): SignUpResponse
    suspend fun loadTokensInitially(username: String, password: String): InitialTokenLoadResponse
    suspend fun getAvatarLink(sub: String): AvatarResponse
    suspend fun sendImage(data: ByteArray)
}