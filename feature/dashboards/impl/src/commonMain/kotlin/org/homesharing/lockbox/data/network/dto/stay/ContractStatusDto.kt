package org.homesharing.lockbox.data.network.dto.stay

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal enum class ContractStatusDto {
    @SerialName("draft")
    Draft,

    @SerialName("signing")
    Signing,

    @SerialName("signed")
    Signed,

    @SerialName("rejected")
    Rejected,

    @SerialName("cancelled")
    Cancelled,
}
