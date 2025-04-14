package org.homesharing.lockbox.di

import org.homesharing.lockbox.data.network.paging.BoxesPagingSource
import org.homesharing.lockbox.data.network.token.refresh_manager.RefreshManager
import org.homesharing.lockbox.data.network.token.storage.TokenStorage
import org.homesharing.lockbox.data.repository.AccountRepositoryImpl
import org.homesharing.lockbox.data.repository.AuthRepositoryImpl
import org.homesharing.lockbox.data.repository.BoxesRepositoryImpl
import org.homesharing.lockbox.domain.api.AccountApi
import org.homesharing.lockbox.domain.api.BoxesApi
import org.homesharing.lockbox.domain.repository.AccountRepository
import org.homesharing.lockbox.domain.repository.AuthRepository
import org.homesharing.lockbox.domain.repository.BoxesRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<AuthRepository> {
        AuthRepositoryImpl(
            get<AccountApi>(),
            get<TokenStorage>(),
            get<RefreshManager>()
        )
    }

    single<AccountRepository> {
        AccountRepositoryImpl(
            get<AccountApi>(),
            get<TokenStorage>()
        )
    }

    single<BoxesRepository> {
        BoxesRepositoryImpl(
            get<BoxesApi>(),
            get<() -> BoxesPagingSource>()
        )
    }
}
