package org.homesharing.lockbox.di

import org.homesharing.lockbox.data.network.api.FlatApi
import org.homesharing.lockbox.data.network.api.FlatApiImpl
import org.homesharing.lockbox.data.repository.FlatRepositoryImpl
import org.homesharing.lockbox.domain.repository.FlatRepository
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val flatModule = module {
    singleOf(::FlatApiImpl) {
        bind<FlatApi>()
    }
    singleOf(::FlatRepositoryImpl) {
        bind<FlatRepository>()
    }
}
