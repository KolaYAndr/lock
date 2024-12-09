package org.homesharing.lockbox.di

import org.homesharing.lockbox.data.network.api.LockBoxApi
import org.homesharing.lockbox.data.repository.LockBoxRepositoryImpl
import org.homesharing.lockbox.domain.repository.LockBoxRepository
import org.koin.dsl.module

val provideRepositoryModule = module {
    single<LockBoxRepository> {
        LockBoxRepositoryImpl(
            get<LockBoxApi>()
        )
    }
}