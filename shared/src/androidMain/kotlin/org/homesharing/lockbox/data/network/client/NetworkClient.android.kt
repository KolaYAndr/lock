package org.homesharing.lockbox.data.network.client

import android.util.Log
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.logging.Logger

internal actual val networkEngine: HttpClientEngine
    get() = OkHttp.create()

internal actual val myLogger: Logger
    get() = object : Logger {
        override fun log(message: String) {
            Log.d("logger", message)
        }
    }
