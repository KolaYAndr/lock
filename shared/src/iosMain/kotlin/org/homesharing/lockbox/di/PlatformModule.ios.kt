package org.homesharing.lockbox.di

import org.homesharing.lockbox.data.network.token.storage.TokenStorage
import org.homesharing.lockbox.data.network.token.refresh_manager.RefreshManager
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

actual val platformModule: Module = module {
    singleOf(::TokenStorage)

    singleOf(::RefreshManager)
}
