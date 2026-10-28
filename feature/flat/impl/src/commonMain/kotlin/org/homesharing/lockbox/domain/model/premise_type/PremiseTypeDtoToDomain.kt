package org.homesharing.lockbox.domain.model.premise_type

import org.homesharing.lockbox.data.network.dto.premise_type.PremiseTypeDto

fun PremiseTypeDto.toDomain() = when (this) {
    PremiseTypeDto.RESIDENTIAL -> PremiseType.RESIDENTIAL
    PremiseTypeDto.NON_RESIDENTIAL -> PremiseType.NON_RESIDENTIAL
}
