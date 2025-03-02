package org.homesharing.lockbox.domain.repository

import org.homesharing.lockbox.data.network.dto.InitialTokenLoadResponseDto
import org.homesharing.lockbox.data.network.dto.sign_up.SignUpResponseDto

interface AuthRepository {
    suspend fun signUp(username: String, password: String, passwordRepeated: String): SignUpResponseDto
    suspend fun loadTokensInitially(username: String, password: String): InitialTokenLoadResponseDto
    suspend fun sendConfirmation(email: String)
    suspend fun checkIfConfirmed(email: String)
}