package org.homesharing.lockbox.di

import kotlinx.cinterop.BetaInteropApi
import kotlinx.cinterop.ObjCClass
import kotlinx.cinterop.getOriginalKotlinClass
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

    @OptIn(BetaInteropApi::class)
    fun get(objCClass: ObjCClass): Any {
        val kClass = getOriginalKotlinClass(objCClass)
        requireNotNull(kClass)
        return koin.get(kClass, null, null)
    }
}
