package org.homesharing.lockbox.di

import org.homesharing.lockbox.data.repository.AccountRepositoryImpl
import org.homesharing.lockbox.data.repository.AuthRepositoryImpl
import org.homesharing.lockbox.data.repository.FlatRepositoryImpl
import org.homesharing.lockbox.domain.repository.AccountRepository
import org.homesharing.lockbox.domain.repository.AuthRepository
import org.homesharing.lockbox.domain.repository.FlatRepository
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val repositoryModule = module {
    singleOf(::AuthRepositoryImpl) {
        bind<AuthRepository>()
    }

    singleOf(::AccountRepositoryImpl) {
        bind<AccountRepository>()
    }

    singleOf(::FlatRepositoryImpl) {
        bind<FlatRepository>()
    }
}
