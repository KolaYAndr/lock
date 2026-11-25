package org.homesharing.lockbox.di

import org.homesharing.lockbox.domain.repository.AccountRepository
import org.homesharing.lockbox.domain.repository.AuthRepository
import org.homesharing.lockbox.domain.repository.ContractsRepository
import org.homesharing.lockbox.domain.repository.DashboardsRepository
import org.homesharing.lockbox.domain.repository.FlatRepository
import org.homesharing.lockbox.domain.repository.InvitationRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.context.startKoin
import org.koin.mp.KoinPlatformTools

fun initKoin() {
    if (KoinPlatformTools.defaultContext().getOrNull() == null) {
        startKoin {
            modules(sharedAppModules)
        }
    }
}

object KoinHelper : KoinComponent {
    fun getAuthRepository(): AuthRepository = get()
    fun getAccountRepository(): AccountRepository = get()
    fun getFlatRepository(): FlatRepository = get()
    fun getInvitationRepository(): InvitationRepository = get()
    fun getContractsRepository(): ContractsRepository = get()
    fun getDashboardsRepository(): DashboardsRepository = get()
}
