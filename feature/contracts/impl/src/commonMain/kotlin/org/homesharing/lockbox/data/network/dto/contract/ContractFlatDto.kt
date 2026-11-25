package org.homesharing.lockbox.data.network.dto.contract

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class ContractFlatDto(
    val id: String,
    val address: String,
    @SerialName("living_area")
    val livingArea: Double,
)
