package org.homesharing.lockbox.data.network.dto.physical_sign_up

import kotlinx.serialization.Serializable

@Serializable
internal data class PhysicalSignUpRequest(
    val code: Int,
    val macAddress: String,
)
