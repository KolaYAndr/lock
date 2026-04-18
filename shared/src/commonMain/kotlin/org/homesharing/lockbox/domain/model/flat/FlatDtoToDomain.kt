package org.homesharing.lockbox.domain.model.flat

import org.homesharing.lockbox.data.network.dto.flat.FlatDto

internal fun FlatDto.toDomain() = Flat(
    id = id,
    address = address,
    livingArea = livingArea
)