package org.homesharing.lockbox.data.network.dto.access

import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

@JvmInline
@Serializable
internal value class AccessResponseDto(
    val listOfAccesses: List<AccessDto>
)

@Serializable
internal data class AccessDto(
    val alias: String,
    val endTime: String,
    val macAddress: String,
    val startTime: String,
    val uuid: String
)
