package org.homesharing.lockbox.domain.model.account_info

data class AccountInfo(
    val email: String,
    val fullName: String?,
    val givenName: String?,
    val familyName: String?,
    val sub: String,
    val sessionId: String,
)
