package org.homesharing.lockbox.data.network.model

data class InitialTokenLoadResponse(
    val access_token: String,
    val expires_in: Int,
    val id_token: String,
    val not-before-policy: Int,
    val refresh_expires_in: Int,
    val refresh_token: String,
    val scope: String,
    val session_state: String,
    val token_type: String
)