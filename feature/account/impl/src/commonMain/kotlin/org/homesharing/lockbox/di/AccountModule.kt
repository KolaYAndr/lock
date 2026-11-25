package org.homesharing.lockbox.di

import org.homesharing.lockbox.data.network.api.AccountApi
import org.homesharing.lockbox.data.network.api.AccountApiImpl
import org.homesharing.lockbox.data.repository.AccountRepositoryImpl
import org.homesharing.lockbox.domain.repository.AccountRepository
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val accountModule = module {
    singleOf(::AccountApiImpl) {
        bind<AccountApi>()
    }
    singleOf(::AccountRepositoryImpl) {
        bind<AccountRepository>()
    }
}
