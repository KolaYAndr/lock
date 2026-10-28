package org.homesharing.lockbox.data.network.client

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.darwin.Darwin
import io.ktor.client.plugins.logging.Logger
import platform.Foundation.NSLog

actual val networkEngine: HttpClientEngine = Darwin.create()

actual val myLogger: Logger = object : Logger {
    override fun log(message: String) {
        NSLog(message)
    }
}
