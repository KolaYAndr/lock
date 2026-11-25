package org.homesharing.lockbox.data.network.client

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp

actual val networkEngine: HttpClientEngine = OkHttp.create()
