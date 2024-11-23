package org.homesharing.lockbox

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform