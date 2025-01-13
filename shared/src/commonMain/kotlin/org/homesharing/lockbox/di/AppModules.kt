package org.homesharing.lockbox.di

val provideAppModules = listOf(
    providePlatformModule,
    provideRepositoryModule,
    provideNetworkModule,
)