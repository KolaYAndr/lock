package org.homesharing.lockbox.data.network.dto.account_info

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class AccountInfoDto(
    val acr: String,
    @SerialName("allowed-origins")
    val allowedOrigins: List<String>,
    val aud: String,
    val azp: String,
    val email: String,
    @SerialName("email_verified")
    val emailVerified: Boolean,
    val exp: Int,
    @SerialName("family_name")
    val familyName: String? = null,
    @SerialName("given_name")
    val givenName: String? = null,
    val iat: Int,
    val iss: String,
    val jti: String,
    val name: String? = null,
    @SerialName("preferred_username")
    val preferredUsername: String,
    @SerialName("realm_access")
    val realmAccess: RealmAccess,
    @SerialName("resource_access")
    val resourceAccess: ResourceAccess,
    val scope: String,
    val sid: String,
    val sub: String,
    val typ: String,
)

@Serializable
internal data class RealmAccess(
    val roles: List<String>,
)

@Serializable
internal data class Account(
    val roles: List<String>,
)

@Serializable
internal data class ResourceAccess(
    val account: Account,
)
