package org.homesharing.lockbox.di

import kotlinx.cinterop.BetaInteropApi
import kotlinx.cinterop.ObjCClass
import kotlinx.cinterop.getOriginalKotlinClass
import org.koin.core.Koin

@OptIn(BetaInteropApi::class)
fun Koin.get(objCClass: ObjCClass): Any {
    val kClass = getOriginalKotlinClass(objCClass)
    requireNotNull(kClass)
    return get(kClass, null, null)
}