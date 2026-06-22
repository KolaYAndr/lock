package org.homesharing.lockbox.di

import org.homesharing.lockbox.domain.repository.AccountRepository
import org.homesharing.lockbox.domain.repository.AuthRepository
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
    fun getBoxesRepository(): BoxesRepository = get()
}
