package org.homesharing.lockbox.di

import org.homesharing.lockbox.data.network.api.ContractsApi
import org.homesharing.lockbox.data.network.api.ContractsApiImpl
import org.homesharing.lockbox.data.repository.ContractsRepositoryImpl
import org.homesharing.lockbox.domain.repository.ContractsRepository
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val contractsModule = module {
    singleOf(::ContractsApiImpl) {
        bind<ContractsApi>()
    }
    singleOf(::ContractsRepositoryImpl) {
        bind<ContractsRepository>()
    }
}
