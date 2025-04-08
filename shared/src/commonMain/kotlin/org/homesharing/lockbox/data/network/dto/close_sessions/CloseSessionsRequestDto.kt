package org.homesharing.lockbox.data.network.dto.close_sessions

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CloseSessionsRequestDto(
    @SerialName("session_id")
    val sessionId: String
)
