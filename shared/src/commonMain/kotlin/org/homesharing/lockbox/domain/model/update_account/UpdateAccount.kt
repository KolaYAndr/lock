package org.homesharing.lockbox.domain.model.update_account

data class UpdateAccount(
    val email: String,
    val emailVerified: Boolean,
    val enabled: Boolean,
    val firstName: String?,
    val lastName: String?,
    val username: String,
)
