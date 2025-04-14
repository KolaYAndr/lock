package org.homesharing.lockbox.di

import io.ktor.client.HttpClient
import org.homesharing.lockbox.data.network.api.LockBoxAccountApi
import org.homesharing.lockbox.data.network.api.LockBoxBoxesApi
import org.homesharing.lockbox.data.network.client.createNetworkClient
import org.homesharing.lockbox.data.network.client.installAuth
import org.homesharing.lockbox.data.network.client.networkEngine
import org.homesharing.lockbox.data.network.paging.BoxesPagingSource
import org.homesharing.lockbox.data.network.token.refresh_manager.RefreshManager
import org.homesharing.lockbox.data.network.token.storage.TokenStorage
import org.homesharing.lockbox.domain.api.AccountApi
import org.homesharing.lockbox.domain.api.BoxesApi
import org.koin.core.qualifier.named
import org.koin.dsl.module

private const val WITH_AUTH_FEATURE = "auth"
private const val NO_AUTH_FEATURE = "no auth"

val networkModule = module {
    single<AccountApi> {
        LockBoxAccountApi(
            get<HttpClient>(named(NO_AUTH_FEATURE)),
            get<HttpClient>(named(WITH_AUTH_FEATURE))
        )
    }

    single<BoxesApi> {
        LockBoxBoxesApi(
            get<HttpClient>(named(WITH_AUTH_FEATURE))
        )
    }

    factory<() -> BoxesPagingSource> {
        {
            BoxesPagingSource(
                get<BoxesApi>()
            )
        }
    }

    single<HttpClient>(
        named(NO_AUTH_FEATURE)
    ) {
        createNetworkClient(networkEngine)
    }

    single<HttpClient>(
        named(WITH_AUTH_FEATURE)
    ) {
        createNetworkClient(networkEngine)
            .installAuth(get<TokenStorage>(), get<RefreshManager>())
    }
}
