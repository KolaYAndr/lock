package org.homesharing.lockbox.domain.model.account_info

import org.homesharing.lockbox.data.network.dto.account_info.AccountInfoDto

internal fun AccountInfoDto.toDomain() = AccountInfo(
    email = email,
    fullName = name,
    givenName = givenName,
    familyName = familyName,
    sub = sub,
    sessionId = sid
)
