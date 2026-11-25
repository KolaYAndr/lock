package org.homesharing.lockbox.di

import org.homesharing.lockbox.data.network.api.ContractApi
import org.homesharing.lockbox.data.network.api.ContractApiImpl
import org.homesharing.lockbox.data.repository.ContractRepositoryImpl
import org.homesharing.lockbox.domain.repository.ContractRepository
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val contractModule = module {
    singleOf(::ContractApiImpl) { bind<ContractApi>() }
    singleOf(::ContractRepositoryImpl) { bind<ContractRepository>() }
}
