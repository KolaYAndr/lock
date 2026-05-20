package org.homesharing.lockbox

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import org.homesharing.lockbox.di.sharedUiModule
import org.homesharing.lockbox.presentation.LockboxApp
import org.koin.core.context.startKoin

fun main() {
    startKoin {
        modules(sharedUiModule)
    }
    application {
        Window(onCloseRequest = ::exitApplication, title = "Lockbox") {
            LockboxApp()
        }
    }
}
