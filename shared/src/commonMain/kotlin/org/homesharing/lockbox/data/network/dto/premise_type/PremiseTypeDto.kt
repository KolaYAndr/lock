package org.homesharing.lockbox.data.network.dto.premise_type

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class PremiseTypeDto {
    @SerialName("residential")
    RESIDENTIAL,
    @SerialName("non_residential")
    NON_RESIDENTIAL,
}