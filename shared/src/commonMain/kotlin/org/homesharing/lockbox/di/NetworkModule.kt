package org.homesharing.lockbox.di

import io.ktor.client.HttpClient
import org.homesharing.lockbox.data.network.api.LockBoxAccountApi
import org.homesharing.lockbox.data.network.api.LockBoxBoxesApi
import org.homesharing.lockbox.data.network.client.createNetworkClient
import org.homesharing.lockbox.data.network.client.networkEngine
import org.homesharing.lockbox.data.network.paging.BoxesPagingSource
import org.homesharing.lockbox.data.network.token.refresh_manager.RefreshManager
import org.homesharing.lockbox.data.network.token.storage.TokenStorage
import org.homesharing.lockbox.domain.api.AccountApi
import org.homesharing.lockbox.domain.api.BoxesApi
import org.koin.dsl.module

val networkModule = module {
    single<AccountApi> {
        LockBoxAccountApi(
            get<HttpClient>()
        )
    }

    single<BoxesApi> {
        LockBoxBoxesApi(
            get<HttpClient>()
        )
    }

    factory<() -> BoxesPagingSource> {
        {
            BoxesPagingSource(
                get<BoxesApi>(),
                get<AccountApi>(),
                get<TokenStorage>(),
                get<RefreshManager>()
            )
        }
    }

    single<HttpClient> {
        createNetworkClient(networkEngine)
    }
}
