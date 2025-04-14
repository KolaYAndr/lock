package org.homesharing.lockbox.data.network.dto.confirm_email

import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

@JvmInline
@Serializable
value class ConfirmEmailResponseDto(
    val response: String,
)
