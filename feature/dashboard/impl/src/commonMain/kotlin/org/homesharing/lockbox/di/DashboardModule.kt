package org.homesharing.lockbox.di

import org.homesharing.lockbox.data.network.api.DashboardApi
import org.homesharing.lockbox.data.network.api.DashboardApiImpl
import org.homesharing.lockbox.data.repository.DashboardRepositoryImpl
import org.homesharing.lockbox.domain.repository.DashboardRepository
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val dashboardModule = module {
    singleOf(::DashboardApiImpl) { bind<DashboardApi>() }
    singleOf(::DashboardRepositoryImpl) { bind<DashboardRepository>() }
}
