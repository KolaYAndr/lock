package org.homesharing.lockbox.domain.model.session

data class Session(
    val ipAddress: String,
    val lastAccessed: Long
)
