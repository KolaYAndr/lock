package org.homesharing.lockbox.domain.model.sign_up

data class SignUp(
    val id: String,
    val username: String,
    val email: String,
    val firstName: String? = null,
    val lastName: String? = null,
    val emailVerified: Boolean,
    val enabled: Boolean,
)
