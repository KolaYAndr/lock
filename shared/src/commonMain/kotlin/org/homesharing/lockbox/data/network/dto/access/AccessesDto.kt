package org.homesharing.lockbox.data.network.dto.access

import kotlinx.serialization.Serializable

@Serializable
internal data class AccessesDto(
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
