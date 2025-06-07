package org.homesharing.lockbox.data.network.dto.access_revoke

import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

@Serializable
@JvmInline
internal value class AccessRevokeResponseDto(val result: String)
