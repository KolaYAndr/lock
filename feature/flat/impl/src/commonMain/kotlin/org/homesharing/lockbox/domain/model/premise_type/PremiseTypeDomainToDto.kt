package org.homesharing.lockbox.domain.model.premise_type

import org.homesharing.lockbox.data.network.dto.premise_type.PremiseTypeDto

fun PremiseType.toDto(): PremiseTypeDto = when (this) {
    PremiseType.RESIDENTIAL -> PremiseTypeDto.RESIDENTIAL
    PremiseType.NON_RESIDENTIAL -> PremiseTypeDto.NON_RESIDENTIAL
}
