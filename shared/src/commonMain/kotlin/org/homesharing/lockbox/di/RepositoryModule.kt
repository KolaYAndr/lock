package org.homesharing.lockbox.di

import org.homesharing.lockbox.data.network.token.storage.TokenStorage
import org.homesharing.lockbox.data.repository.AccountRepositoryImpl
import org.homesharing.lockbox.data.repository.AuthRepositoryImpl
import org.homesharing.lockbox.domain.api.AuthApi
import org.homesharing.lockbox.domain.repository.AccountRepository
import org.homesharing.lockbox.domain.repository.AuthRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<AuthRepository> {
        AuthRepositoryImpl(
            get<AuthApi>(),
            get<TokenStorage>()
        )
    }

    single<AccountRepository> {
        AccountRepositoryImpl(
            get<TokenStorage>()
        )
    }
}
