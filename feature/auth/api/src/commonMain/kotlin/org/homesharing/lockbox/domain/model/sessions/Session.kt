package org.homesharing.lockbox.domain.model.sessions

data class Sessions(
    val sessions: List<Session>,
) {
    data class Session(
        val id: String,
        val ip: String,
        val lastActive: String,
    )
}
