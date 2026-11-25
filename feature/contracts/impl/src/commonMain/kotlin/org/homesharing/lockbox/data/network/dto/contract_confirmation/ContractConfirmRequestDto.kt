package org.homesharing.lockbox.data.network.dto.contract_confirmation

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.homesharing.lockbox.data.network.dto.contract.ContractPartyDto

@Serializable
internal data class ContractConfirmRequestDto(
    @SerialName("version_id")
    val versionId: String,
    val party: ContractPartyDto,
    val code: String,
)
