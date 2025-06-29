package org.homesharing.lockbox.data.network.dto.reset_password

import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

@Serializable
@JvmInline
internal value class ResetPasswordResponseDto(val message: String)
