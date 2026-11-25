package org.homesharing.lockbox.data.network.dto.stay

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal enum class PlannedPhaseDto {
    @SerialName("upcoming")
    Upcoming,

    @SerialName("current")
    Current,

    @SerialName("past")
    Past,

    @SerialName("cancelled")
    Cancelled,
}
