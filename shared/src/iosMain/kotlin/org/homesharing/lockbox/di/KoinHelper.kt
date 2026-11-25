package org.homesharing.lockbox.di

import org.homesharing.lockbox.domain.repository.AccountRepository
import org.homesharing.lockbox.domain.repository.AuthRepository
import org.homesharing.lockbox.domain.repository.ContractRepository
import org.homesharing.lockbox.domain.repository.DashboardRepository
import org.homesharing.lockbox.domain.repository.FlatRepository
import org.homesharing.lockbox.domain.repository.InvitationRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.context.startKoin

fun initKoin() {
    startKoin {
        modules(sharedAppModules)
    }
}

object KoinHelper : KoinComponent {
    fun getAuthRepository(): AuthRepository = get()
    fun getAccountRepository(): AccountRepository = get()
    fun getFlatRepository(): FlatRepository = get()
    fun getInvitationRepository(): InvitationRepository = get()
    fun getContractRepository(): ContractRepository = get()
    fun getDashboardRepository(): DashboardRepository = get()
}
