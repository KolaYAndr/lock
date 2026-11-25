package org.homesharing.lockbox.domain.model.flat

import org.homesharing.lockbox.domain.model.flat_detail.FlatStatus
import org.homesharing.lockbox.domain.model.premise_type.PremiseType

data class Flat(
    val id: String,
    val address: String,
    val name: String,
    val premiseType: PremiseType,
    val livingArea: Double?,
    val description: String? = null,
    val status: FlatStatus = FlatStatus.Active,
    val coverPhotoUrl: String? = null,
    val activeInvitesCount: Int = 0,
)
