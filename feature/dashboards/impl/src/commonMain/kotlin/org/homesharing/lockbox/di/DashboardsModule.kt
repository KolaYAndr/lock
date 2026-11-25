package org.homesharing.lockbox.di

import org.homesharing.lockbox.data.network.api.DashboardsApi
import org.homesharing.lockbox.data.network.api.DashboardsApiImpl
import org.homesharing.lockbox.data.repository.DashboardsRepositoryImpl
import org.homesharing.lockbox.domain.repository.DashboardsRepository
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val dashboardsModule = module {
    singleOf(::DashboardsApiImpl) {
        bind<DashboardsApi>()
    }
    singleOf(::DashboardsRepositoryImpl) {
        bind<DashboardsRepository>()
    }
}
