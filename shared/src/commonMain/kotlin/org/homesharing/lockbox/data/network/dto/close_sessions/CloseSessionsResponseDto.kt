package org.homesharing.lockbox.data.network.dto.close_sessions

import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

@JvmInline
@Serializable
value class CloseSessionsResponseDto(val message: String)
