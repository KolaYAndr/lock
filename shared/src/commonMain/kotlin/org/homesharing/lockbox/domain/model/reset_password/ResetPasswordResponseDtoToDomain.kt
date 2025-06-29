package org.homesharing.lockbox.domain.model.reset_password

import org.homesharing.lockbox.data.network.dto.reset_password.ResetPasswordResponseDto

internal fun ResetPasswordResponseDto.toDomain(): ResetPassword = ResetPassword(result = message)
