package org.homesharing.lockbox.di

import org.homesharing.lockbox.data.network.token.refresh_manager.RefreshManager
import org.homesharing.lockbox.data.network.token.storage.TOKEN_STORAGE_NAME
import org.homesharing.lockbox.data.network.token.storage.TokenStorage
import org.homesharing.lockbox.data.network.token.storage.TokenStorageImpl
import org.koin.core.module.Module
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import platform.Foundation.NSUserDefaults

actual val platformModule: Module = module {
    singleOf(::TokenStorageImpl) {
        bind<TokenStorage>()
    }
    singleOf(::RefreshManager)
    single<NSUserDefaults> {
        NSUserDefaults(suiteName = TOKEN_STORAGE_NAME)
    }
}