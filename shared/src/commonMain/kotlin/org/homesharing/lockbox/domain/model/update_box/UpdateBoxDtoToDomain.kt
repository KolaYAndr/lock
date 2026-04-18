package org.homesharing.lockbox.domain.model.update_box

import org.homesharing.lockbox.data.network.dto.update_box.UpdateBoxDto

internal fun UpdateBoxDto.toDomain() = UpdateBox(
    alias = alias,
    address = address,
    macAddress = macAddress,
    ownerSub = ownerSub
)
