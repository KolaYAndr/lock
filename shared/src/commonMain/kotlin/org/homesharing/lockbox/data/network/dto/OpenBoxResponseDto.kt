package org.homesharing.lockbox.data.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OpenBoxResponseDto(
    val body: OpenBoxBody,
    val error: Boolean,
    @SerialName("error_text")
    val errorText: String?
)

@Serializable
data class OpenBoxBody(
    val call: String,
    val id: Int,
    @SerialName("issuer_sub")
    val issuerSub: String,
    @SerialName("issuer_token")
    val issuerToken: String?,
    @SerialName("mac_address")
    val macAddress: String
)
