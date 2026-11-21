package org.homesharing.lockbox.data.network.dto.flat_detail

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal enum class FlatStatusDto {
    @SerialName("active")
    Active,

    @SerialName("archived")
    Archived,
}
