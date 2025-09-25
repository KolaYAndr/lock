package org.homesharing.lockbox.di

import org.koin.core.Koin
import org.koin.core.context.startKoin

object DiHelper {
    val koin: Koin by lazy {
        initSharedKoin()
    }

    private fun initSharedKoin(): Koin = startKoin {
        printLogger()
        modules(sharedAppModules)
    }.koin
}
