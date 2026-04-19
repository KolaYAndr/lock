package org.homesharing.lockbox.domain.model.contract

import kotlinx.datetime.LocalDate
import org.homesharing.lockbox.data.network.dto.contract_list.ContractDto

internal fun ContractDto.toDomain() = Contract(
    city = city,
    date = LocalDate.parse(date),
    flatAddress = flatAddress,
    id = id
)