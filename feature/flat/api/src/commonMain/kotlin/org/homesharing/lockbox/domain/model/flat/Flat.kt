package org.homesharing.lockbox.domain.model.flat

import org.homesharing.lockbox.domain.model.premise_type.PremiseType

data class Flat(
    val id: String,
    val address: String,
    val name: String,
    val premiseType: PremiseType,
    val livingArea: Double?,
)
