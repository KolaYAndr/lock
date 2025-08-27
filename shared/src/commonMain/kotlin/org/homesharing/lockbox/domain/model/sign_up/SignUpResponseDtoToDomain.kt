package org.homesharing.lockbox.domain.model.sign_up

import org.homesharing.lockbox.data.network.dto.sign_up.SignUpResponseDto

internal fun SignUpResponseDto.toDomain(): SignUp = SignUp(
    id = this.user.id,
    username = this.user.username,
    email = this.user.email,
    firstName = this.user.firstName,
    lastName = this.user.lastName,
    emailVerified = this.user.emailVerified,
    enabled = this.user.enabled
)
