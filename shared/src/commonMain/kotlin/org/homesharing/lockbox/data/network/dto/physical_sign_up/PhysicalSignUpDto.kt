package org.homesharing.lockbox.data.network.dto.physical_sign_up

import kotlinx.serialization.Serializable

@Serializable
internal data class PhysicalSignUpDto(
    val macAddress: String,
    val ownerSub: String,
    val address: String,
    val alias: String,
)
