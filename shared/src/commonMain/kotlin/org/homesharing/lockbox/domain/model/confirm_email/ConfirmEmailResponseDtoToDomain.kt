package org.homesharing.lockbox.domain.model.confirm_email

import org.homesharing.lockbox.data.network.dto.confirm_email.ConfirmEmailResponseDto

internal fun ConfirmEmailResponseDto.toDomain(): ConfirmEmail = ConfirmEmail(this.response)
