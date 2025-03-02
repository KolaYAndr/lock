package org.homesharing.lockbox.data.network.dto.update_box

import kotlinx.serialization.Serializable

@Serializable
data class UpdateBoxRequestDto(
    val alias: String? = null,
    val address: String? = null
)
