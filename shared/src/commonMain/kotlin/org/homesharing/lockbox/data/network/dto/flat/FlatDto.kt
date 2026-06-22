package org.homesharing.lockbox.data.network.dto.flat

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.homesharing.lockbox.data.network.dto.premise_type.PremiseTypeDto

@Serializable
internal data class FlatDto(
    val id: String,
    @SerialName("full_address")
    val fullAddress: String,
    val name: String,
    @SerialName("premise_type")
    val premiseType: PremiseTypeDto,
    @SerialName("living_area")
    val livingArea: Double?
)