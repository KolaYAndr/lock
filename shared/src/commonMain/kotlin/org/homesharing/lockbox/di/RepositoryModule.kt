package org.homesharing.lockbox.di

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
            get<TokenStorage>()
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
            get<BoxesApi>()
        )
    }
}
