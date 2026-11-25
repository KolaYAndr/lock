package org.homesharing.lockbox.data.network.dto.contract

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal enum class ContractPartyDto {
    @SerialName("landlord")
    Landlord,

    @SerialName("guest")
    Guest,
}
