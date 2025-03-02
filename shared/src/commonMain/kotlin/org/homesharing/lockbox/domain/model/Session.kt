package org.homesharing.lockbox.domain.model

data class Session(
    val ipAddress: String,
    val lastAccessed: Long
)
