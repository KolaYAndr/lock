package org.homesharing.lockbox.domain.model.flat

import org.homesharing.lockbox.data.network.dto.flat_list.FlatSummaryListDto
import org.homesharing.lockbox.domain.model.flat_detail.toDomain
import org.homesharing.lockbox.domain.model.premise_type.toDomain

internal fun FlatSummaryListDto.FlatSummaryDto.toDomain() = Flat(
    id = id,
    address = fullAddress,
    name = name,
    premiseType = premiseType.toDomain(),
    livingArea = livingArea,
    description = description,
    status = status.toDomain(),
    coverPhotoUrl = coverPhotoUrl,
    activeInvitesCount = activeInvitesCount,
)
