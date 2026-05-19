package org.homesharing.lockbox.di

import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

val sharedUIModules = sharedAppModules + sharedUIViewModelModule

fun initKoin(appDeclaration: KoinAppDeclaration = {}): KoinApplication =
    startKoin {
        appDeclaration()
        modules(sharedUIModules)
    }
