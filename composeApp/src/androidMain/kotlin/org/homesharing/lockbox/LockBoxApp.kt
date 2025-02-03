package org.homesharing.lockbox

import android.app.Application
import org.homesharing.lockbox.di.koinAndroidConfiguration
import org.koin.core.context.startKoin

class LockBoxApp: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin(koinAndroidConfiguration(this))
    }
}