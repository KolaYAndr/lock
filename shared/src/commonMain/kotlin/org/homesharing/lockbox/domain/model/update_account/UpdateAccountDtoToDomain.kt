package org.homesharing.lockbox.domain.model.update_account

import org.homesharing.lockbox.data.network.dto.update_account.UpdateAccountResponseDto

internal fun UpdateAccountResponseDto.toDomain(): UpdateAccount = UpdateAccount(
    email = email,
    emailVerified = emailVerified,
    enabled = enabled,
    firstName = firstName,
    lastName = lastName,
    username = username,
)
