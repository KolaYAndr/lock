package org.homesharing.lockbox.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import org.homesharing.lockbox.data.network.api.LockBoxApi
import org.homesharing.lockbox.data.network.model.SignUpRequest
import org.homesharing.lockbox.data.network.model.SignUpResponse
import org.homesharing.lockbox.domain.repository.LockBoxRepository

class LockBoxRepositoryImpl(private val api: LockBoxApi) : LockBoxRepository {
    override suspend fun signUp(request: SignUpRequest): SignUpResponse =
        withContext(Dispatchers.IO) {
            api.singUp(request)
        }
}
