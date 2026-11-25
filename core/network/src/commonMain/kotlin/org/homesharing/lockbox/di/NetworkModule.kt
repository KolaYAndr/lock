package org.homesharing.lockbox.di

import io.ktor.client.HttpClient
import kotlinx.serialization.json.Json
import org.homesharing.lockbox.data.network.client.createNetworkClient
import org.homesharing.lockbox.data.network.client.networkEngine
import org.homesharing.lockbox.data.network.token.TokenStorage
import org.koin.dsl.module

val coreNetworkModule = module {
    single<HttpClient> {
        createNetworkClient(
            engine = networkEngine,
            json = get<Json>(),
            tokenStorage = get<TokenStorage>(),
        )
    }

    single<Json> {
        Json {
            explicitNulls = false
            ignoreUnknownKeys = true
            prettyPrint = false
            isLenient = true
        }
    }
}
