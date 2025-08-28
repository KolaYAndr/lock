package org.homesharing.lockbox.domain.model.physical_sign_up

import org.homesharing.lockbox.data.network.dto.physical_sign_up.PhysicalSignUpResponseDto

internal fun PhysicalSignUpResponseDto.toDomain(): PhysicalSignUp =
    PhysicalSignUp(macAddress, ownerSub, address, alias)
