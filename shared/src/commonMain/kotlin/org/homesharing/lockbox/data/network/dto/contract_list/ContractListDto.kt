package org.homesharing.lockbox.data.network.dto.contract_list

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class ContractListDto(
    @SerialName("items")
    val contracts: List<ContractDto>,
    @SerialName("next_cursor")
    val nextCursor: String
)

@Serializable
internal data class ContractDto(
    val city: String,
    val date: String,
    @SerialName("flat_address")
    val flatAddress: String,
    val id: String
)