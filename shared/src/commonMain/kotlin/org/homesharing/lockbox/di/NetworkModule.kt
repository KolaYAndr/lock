package org.homesharing.lockbox.di

import io.ktor.client.HttpClient
import org.homesharing.lockbox.data.network.api.LockBoxApi
import org.homesharing.lockbox.data.network.client.createNetworkClient
import org.homesharing.lockbox.data.network.client.networkEngine
import org.homesharing.lockbox.data.network.token.storage.TokenStorage
import org.koin.dsl.module

val provideNetworkModule = module {
    single<LockBoxApi> {
        LockBoxApi(
            get<HttpClient>()
        )
    }

    single<HttpClient> {
        createNetworkClient(networkEngine, get<TokenStorage>())
    }
}