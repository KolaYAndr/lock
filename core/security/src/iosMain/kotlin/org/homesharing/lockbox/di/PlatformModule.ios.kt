package org.homesharing.lockbox.di

import org.homesharing.lockbox.data.network.token.Keychain
import org.homesharing.lockbox.data.network.token.TOKEN_STORAGE_NAME
import org.homesharing.lockbox.data.network.token.TokenStorage
import org.homesharing.lockbox.data.network.token.TokenStorageImpl
import org.koin.core.module.Module
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

actual val platformModule: Module = module {
    single {
        Keychain(service = TOKEN_STORAGE_NAME)
    }
    singleOf(::TokenStorageImpl) {
        bind<TokenStorage>()
    }
}
