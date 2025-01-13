package org.homesharing.lockbox.data.network.client

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.engine.okhttp.OkHttpEngine

actual val networkEngine: HttpClientEngine
    get() = OkHttp.create()