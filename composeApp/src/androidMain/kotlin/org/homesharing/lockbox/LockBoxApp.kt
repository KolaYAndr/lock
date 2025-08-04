package org.homesharing.lockbox

import android.app.Application
import android.app.UiModeManager
import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatDelegate
import org.homesharing.lockbox.di.koinAndroidConfiguration
import org.koin.core.context.startKoin

class LockBoxApp: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin(koinAndroidConfiguration(this))
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val uiModeManager = getSystemService(UI_MODE_SERVICE) as UiModeManager
            uiModeManager.setApplicationNightMode(UiModeManager.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
    }
}
