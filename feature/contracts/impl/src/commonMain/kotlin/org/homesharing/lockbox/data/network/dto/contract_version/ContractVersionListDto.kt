package org.homesharing.lockbox.data.network.dto.contract_version

import kotlinx.serialization.Serializable

@Serializable
internal data class ContractVersionListDto(
    val items: List<ContractVersionDto>,
)
