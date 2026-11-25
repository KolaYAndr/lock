package org.homesharing.lockbox.data.network.dto.contract

import kotlinx.serialization.Serializable

@Serializable
internal data class ContractCancelRequestDto(
    val reason: String?,
)
