package org.homesharing.lockbox.data.network.dto.open_box

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class OpenBoxResponseDto(
    val call: String,
    val id: Int,
    @SerialName("issuer_sub")
    val issuerSub: String,
    @SerialName("issuer_token")
    val issuerToken: String?,
    @SerialName("mac_address")
    val macAddress: String,
)
