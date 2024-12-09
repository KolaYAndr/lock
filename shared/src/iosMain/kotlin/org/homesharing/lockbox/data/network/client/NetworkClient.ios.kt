package org.homesharing.lockbox.data.network.client

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.darwin.Darwin

actual val networkEngine: HttpClientEngine
    get() = Darwin.create()