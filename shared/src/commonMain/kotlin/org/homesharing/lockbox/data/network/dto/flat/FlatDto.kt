package org.homesharing.lockbox.data.network.dto.flat

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class FlatDto(
    val address: String,
    val id: String,
    @SerialName("living_area")
    val livingArea: Double
)