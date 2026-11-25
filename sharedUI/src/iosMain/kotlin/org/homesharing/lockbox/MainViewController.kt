package org.homesharing.lockbox

import androidx.compose.ui.window.ComposeUIViewController
import org.homesharing.lockbox.di.sharedUiModule
import org.homesharing.lockbox.presentation.LockboxApp
import org.koin.core.context.startKoin
import org.koin.mp.KoinPlatformTools
import platform.UIKit.UIViewController

fun MainViewController(): UIViewController = ComposeUIViewController(
    configure = { initKoinIfNeeded() },
) {
    LockboxApp()
}

private fun initKoinIfNeeded() {
    if (KoinPlatformTools.defaultContext().getOrNull() == null) {
        startKoin {
            modules(sharedUiModule)
        }
    }
}
