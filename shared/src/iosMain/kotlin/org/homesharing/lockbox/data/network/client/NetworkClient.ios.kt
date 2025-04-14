package org.homesharing.lockbox.data.network.client

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.darwin.Darwin
import io.ktor.client.plugins.logging.Logger

actual val networkEngine: HttpClientEngine
    get() = Darwin.create()
actual val myLogger: Logger
    get() = TODO("Not yet implemented")