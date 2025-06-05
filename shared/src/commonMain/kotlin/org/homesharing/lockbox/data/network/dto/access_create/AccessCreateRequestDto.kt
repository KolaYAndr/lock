package org.homesharing.lockbox.data.network.dto.access_create

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class AccessCreateRequestDto(
    @SerialName("start_time")
    val startTime: String,
    @SerialName("end_time")
    val endTime: String,
    val alias: String?,
)
