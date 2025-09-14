package org.homesharing.lockbox.di

import org.koin.core.Koin
import org.koin.core.context.startKoin
import kotlin.properties.Delegates

object DiHelper {
    var koin: Koin by Delegates.notNull()

    fun initSharedKoin() {
        val instance = startKoin {
            printLogger()
            modules(sharedAppModules)
        }
        koin = instance.koin
    }
}