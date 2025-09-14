package org.homesharing.lockbox.di

import org.koin.core.context.startKoin

val sharedAppModules = listOf(
    platformModule,
    repositoryModule,
    networkModule,
    viewModelModule,
)

fun initSharedKoin() {
    startKoin {
        printLogger()
        modules(sharedAppModules)
    }
}
