package org.homesharing.lockbox.data.network.dto.accessed

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

@JvmInline
@Serializable
internal value class AccessedResponseDto(
    val listOfAccesses: List<AccessedDto>
)

@Serializable
internal data class AccessedDto(
    val alias: String?,
    @SerialName("end_time")
    val endTime: String,
    @SerialName("mac_address")
    val macAddress: String,
    @SerialName("start_time")
    val startTime: String,
    val token: String,
    val uuid: String
)
