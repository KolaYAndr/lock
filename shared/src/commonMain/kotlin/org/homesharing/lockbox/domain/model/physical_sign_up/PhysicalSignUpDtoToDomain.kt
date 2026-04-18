package org.homesharing.lockbox.domain.model.physical_sign_up

import org.homesharing.lockbox.data.network.dto.physical_sign_up.PhysicalSignUpDto

internal fun PhysicalSignUpDto.toDomain() = PhysicalSignUp(macAddress, ownerSub, address, alias)
