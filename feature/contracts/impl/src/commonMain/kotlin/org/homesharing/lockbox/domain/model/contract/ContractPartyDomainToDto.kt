package org.homesharing.lockbox.domain.model.contract

import org.homesharing.lockbox.data.network.dto.contract.ContractPartyDto

internal fun ContractParty.toDto(): ContractPartyDto = when (this) {
    ContractParty.Landlord -> ContractPartyDto.Landlord
    ContractParty.Guest -> ContractPartyDto.Guest
}
