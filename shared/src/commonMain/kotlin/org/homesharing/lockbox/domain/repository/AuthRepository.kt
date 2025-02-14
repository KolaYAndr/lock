package org.homesharing.lockbox.domain.repository

import org.homesharing.lockbox.data.network.model.InitialTokenLoadResponse
import org.homesharing.lockbox.data.network.model.SignUpResponse

interface AuthRepository {
    suspend fun signUp(username: String, password: String, passwordRepeated: String): SignUpResponse
    suspend fun loadTokensInitially(username: String, password: String): InitialTokenLoadResponse
}