package org.homesharing.lockbox.di

import org.homesharing.lockbox.data.network.api.AuthApi
import org.homesharing.lockbox.data.network.api.AuthApiImpl
import org.homesharing.lockbox.data.repository.AuthRepositoryImpl
import org.homesharing.lockbox.domain.repository.AuthRepository
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val authModule = module {
    singleOf(::AuthApiImpl) {
        bind<AuthApi>()
    }
    singleOf(::AuthRepositoryImpl) {
        bind<AuthRepository>()
    }
}
