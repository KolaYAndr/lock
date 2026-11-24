package org.homesharing.lockbox.data.network.dto.invite

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal enum class InviteEventActorTypeDto {
    @SerialName("landlord")
    Landlord,

    @SerialName("guest")
    Guest,

    @SerialName("system")
    System,
}
