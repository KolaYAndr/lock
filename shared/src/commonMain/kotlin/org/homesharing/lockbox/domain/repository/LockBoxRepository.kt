package org.homesharing.lockbox.domain.repository

import org.homesharing.lockbox.data.network.model.SignUpRequest
import org.homesharing.lockbox.data.network.model.SignUpResponse

interface LockBoxRepository {
    suspend fun signUp(request: SignUpRequest): SignUpResponse
}