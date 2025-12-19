package org.homesharing.lockbox.data.network.dto.open_box

import kotlinx.serialization.Serializable

@Serializable
internal data class OpenBoxResponseDto(
    val call: String,
    val id: Int,
    val issuerSub: String,
    val issuerToken: String?,
    val macAddress: String,
    val timestamp: Double,
)
