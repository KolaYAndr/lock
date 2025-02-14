package org.homesharing.lockbox.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import org.homesharing.lockbox.data.network.model.BoxDto
import org.homesharing.lockbox.data.network.model.BoxListResponse
import org.homesharing.lockbox.data.network.model.BoxResponse
import org.homesharing.lockbox.data.network.paging.BoxesPagingSource
import org.homesharing.lockbox.domain.api.BoxesApi
import org.homesharing.lockbox.domain.repository.BoxesRepository


class BoxesRepositoryImpl(
    private val api: BoxesApi
) : BoxesRepository {

    override suspend fun getBoxByMac(address: String): BoxResponse = withContext(Dispatchers.IO) {
        api.getBoxByMac(address)
    }
}
