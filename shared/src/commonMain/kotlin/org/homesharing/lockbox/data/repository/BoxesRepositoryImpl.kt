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
import org.homesharing.lockbox.data.network.model.BoxResponse
import org.homesharing.lockbox.data.network.paging.BoxesPagingSource
import org.homesharing.lockbox.domain.api.BoxesApi
import org.homesharing.lockbox.domain.repository.BoxesRepository

private const val PAGE_SIZE = 10

class BoxesRepositoryImpl(
    private val api: BoxesApi,
    private val pagingSourceFactory: () -> BoxesPagingSource
) : BoxesRepository {
    override fun getBoxesPagingFlow(): Flow<PagingData<BoxDto>> =
        Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = false,
                initialLoadSize = PAGE_SIZE
            ),
            pagingSourceFactory = { pagingSourceFactory() }
        ).flow.flowOn(Dispatchers.IO)


    override suspend fun getBoxByMac(address: String): BoxResponse = withContext(Dispatchers.IO) {
        api.getBoxByMac(address)
    }
}
