package org.homesharing.lockbox.di

import io.ktor.client.HttpClient
import kotlinx.serialization.json.Json
import org.homesharing.lockbox.data.network.api.AccountApi
import org.homesharing.lockbox.data.network.api.AccountApiImpl
import org.homesharing.lockbox.data.network.api.FlatApi
import org.homesharing.lockbox.data.network.api.FlatApiImpl
import org.homesharing.lockbox.data.network.client.createNetworkClient
import org.homesharing.lockbox.data.network.client.networkEngine
import org.homesharing.lockbox.data.network.token.TokenStorage
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val networkModule = module {
    singleOf(::AccountApiImpl) {
        bind<AccountApi>()
    }

    singleOf(::FlatApiImpl) {
        bind<FlatApi>()
    }

    single<HttpClient> {
        createNetworkClient(
            engine = networkEngine,
            json = get<Json>(),
            tokenStorage = get<TokenStorage>(),
        )
    }

    single<Json> {
        Json {
            ignoreUnknownKeys = true
            prettyPrint = true
            isLenient = true
        }
    }
}
