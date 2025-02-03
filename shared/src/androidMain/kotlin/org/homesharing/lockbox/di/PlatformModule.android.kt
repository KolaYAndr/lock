package org.homesharing.lockbox.di

import org.homesharing.lockbox.data.network.token.storage.TokenStorage
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module = module {
    single<TokenStorage> {
        TokenStorage(get())
    }
}