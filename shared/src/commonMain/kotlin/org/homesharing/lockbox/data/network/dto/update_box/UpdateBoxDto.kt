package org.homesharing.lockbox.data.network.dto.update_box

import kotlinx.serialization.Serializable

@Serializable
internal data class UpdateBoxDto(
    val address: String,
    val alias: String,
    val macAddress: String,
    val ownerSub: String
)
