package org.homesharing.lockbox.data.network.dto.update_box

import kotlinx.serialization.Serializable

@Serializable
internal data class UpdateBoxRequestDto(
    val alias: String? = null,
    val address: String? = null,
    val macAddress: String? = null,
    val ownerSub: String? = null
)
