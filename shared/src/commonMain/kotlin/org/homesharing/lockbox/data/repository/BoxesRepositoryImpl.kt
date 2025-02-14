package org.homesharing.lockbox.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import org.homesharing.lockbox.data.network.model.BoxResponse
import org.homesharing.lockbox.domain.api.BoxesApi
import org.homesharing.lockbox.domain.repository.BoxesRepository


class BoxesRepositoryImpl(
    private val api: BoxesApi
) : BoxesRepository {

    override suspend fun getBoxByMac(address: String): BoxResponse = withContext(Dispatchers.IO) {
        api.getBoxByMac(address)
    }
}
