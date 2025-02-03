package org.homesharing.lockbox.di

import io.ktor.client.HttpClient
import org.homesharing.lockbox.data.network.api.LockBoxAuthApi
import org.homesharing.lockbox.data.network.client.createNetworkClient
import org.homesharing.lockbox.data.network.client.networkEngine
import org.homesharing.lockbox.data.network.token.storage.TokenStorage
import org.homesharing.lockbox.domain.api.AuthApi
import org.koin.dsl.module

val networkModule = module {
    single<AuthApi> {
        LockBoxAuthApi(
            get<HttpClient>()
        )
    }

    single<HttpClient> {
        createNetworkClient(networkEngine)
    }
}