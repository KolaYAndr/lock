package org.homesharing.lockbox.domain.model.reset_password

import org.homesharing.lockbox.data.network.dto.reset_password.ResetPasswordDto

internal fun ResetPasswordDto.toDomain(): ResetPassword = ResetPassword(this.message)
