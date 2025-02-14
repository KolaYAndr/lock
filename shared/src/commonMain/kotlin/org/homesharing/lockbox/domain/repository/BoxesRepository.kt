package org.homesharing.lockbox.domain.repository

import org.homesharing.lockbox.data.network.model.BoxResponse

interface BoxesRepository {
    suspend fun getBoxByMac(address: String): BoxResponse
}