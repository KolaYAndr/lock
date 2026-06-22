package org.homesharing.lockbox.domain.model.flat

import org.homesharing.lockbox.data.network.dto.flat.FlatDto
import org.homesharing.lockbox.domain.model.premise_type.toDomain

internal fun FlatDto.toDomain() = Flat(
    id = id,
    address = fullAddress,
    name = name,
    premiseType = premiseType.toDomain(),
    livingArea = livingArea
)