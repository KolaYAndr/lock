package org.homesharing.lockbox.domain.model.confirm_email

import org.homesharing.lockbox.data.network.dto.confirm_email.ConfirmEmailDto

internal fun ConfirmEmailDto.toDomain() = ConfirmEmail(response)
