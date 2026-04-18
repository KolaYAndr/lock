package org.homesharing.lockbox.data.network.dto.access_create

import kotlinx.serialization.Serializable

@Serializable
internal data class AccessCreateRequest(
    val startTime: String,
    val endTime: String,
    val alias: String,
)
