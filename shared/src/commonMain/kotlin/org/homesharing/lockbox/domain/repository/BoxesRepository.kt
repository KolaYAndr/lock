package org.homesharing.lockbox.domain.repository

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import org.homesharing.lockbox.data.network.model.BoxDto
import org.homesharing.lockbox.data.network.model.BoxResponse

interface BoxesRepository {
    fun getBoxesPagingFlow(): Flow<PagingData<BoxDto>>
    suspend fun getBoxByMac(address: String): BoxResponse
}