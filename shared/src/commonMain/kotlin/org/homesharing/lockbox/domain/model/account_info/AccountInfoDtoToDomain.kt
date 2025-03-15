package org.homesharing.lockbox.domain.model.account_info

import org.homesharing.lockbox.data.network.dto.account_info.AccountInfoDto

fun AccountInfoDto.toDomain(): AccountInfo = AccountInfo(
    email = email,
    fullName = name,
    givenName = givenName,
    familyName = familyName,
    sub = sub,
    sessionId = jti
)
