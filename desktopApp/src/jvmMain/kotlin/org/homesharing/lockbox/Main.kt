package org.homesharing.lockbox

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import org.homesharing.lockbox.di.initKoin
import org.homesharing.lockbox.presentation.LockboxApp

fun main() {
    initKoin()
    application {
        Window(onCloseRequest = ::exitApplication, title = "Lockbox") {
            LockboxApp()
        }
    }
}
