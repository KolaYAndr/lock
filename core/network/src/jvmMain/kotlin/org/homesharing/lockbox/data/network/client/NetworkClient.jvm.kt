package org.homesharing.lockbox.data.network.client

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.logging.Logger

actual val networkEngine: HttpClientEngine = OkHttp.create()

actual val myLogger: Logger = object : Logger {
    override fun log(message: String) {
        println("logger: $message")
    }
}
