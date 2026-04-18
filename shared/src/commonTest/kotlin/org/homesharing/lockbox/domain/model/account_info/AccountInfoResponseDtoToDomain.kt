package org.homesharing.lockbox.domain.model.account_info

import org.homesharing.lockbox.data.network.dto.account_info.AccountDto
import org.homesharing.lockbox.data.network.dto.account_info.AccountInfoDto
import org.homesharing.lockbox.data.network.dto.account_info.RealmAccessDto
import org.homesharing.lockbox.data.network.dto.account_info.ResourceAccessDto
import kotlin.test.Test
import kotlin.test.assertEquals

class AccountInfoResponseDtoToDomainTest {

    @Test
    fun `maps correctly when all optional fields are non-null`() {
        val acr = "acr-value"
        val allowedOrigins = listOf("https://example.com", "https://another.com")
        val aud = "aud-value"
        val azp = "azp-value"
        val email = "user@example.com"
        val emailVerified = true
        val exp = 9999
        val familyName = "Doe"
        val givenName = "John"
        val iat = 1111
        val iss = "issuer"
        val jti = "jti-value"
        val name = "John Doe"
        val preferredUsername = "johndoe"
        val realmRoles = listOf("role1", "role2")
        val resourceRoles = listOf("resRole1")
        val scope = "scope1 scope2"
        val sid = "session-id-123"
        val sub = "subject-456"
        val typ = "typ-value"

        val realmAccess = RealmAccessDto(roles = realmRoles)
        val account = AccountDto(roles = resourceRoles)
        val resourceAccess = ResourceAccessDto(account = account)

        val dto = AccountInfoDto(
            acr = acr,
            allowedOrigins = allowedOrigins,
            aud = aud,
            azp = azp,
            email = email,
            emailVerified = emailVerified,
            exp = exp,
            familyName = familyName,
            givenName = givenName,
            iat = iat,
            iss = iss,
            jti = jti,
            name = name,
            preferredUsername = preferredUsername,
            realmAccess = realmAccess,
            resourceAccess = resourceAccess,
            scope = scope,
            sid = sid,
            sub = sub,
            typ = typ
        )

        val domain = dto.toDomain()

        assertEquals(email, domain.email)
        assertEquals(name, domain.fullName)
        assertEquals(givenName, domain.givenName)
        assertEquals(familyName, domain.familyName)
        assertEquals(sub, domain.sub)
        assertEquals(sid, domain.sessionId)
    }

    @Test
    fun `maps correctly when optional name fields are null`() {
        val acr = "acr-value"
        val allowedOrigins = emptyList<String>()
        val aud = "aud"
        val azp = "azp"
        val email = "user2@example.com"
        val emailVerified = false
        val exp = 0
        val familyName: String? = null
        val givenName: String? = null
        val iat = 0
        val iss = "issuer2"
        val jti = "jti2"
        val name: String? = null
        val preferredUsername = "user2"
        val realmAccess = RealmAccessDto(roles = emptyList())
        val resourceAccess = ResourceAccessDto(account = AccountDto(roles = emptyList()))
        val scope = ""
        val sid = "session-456"
        val sub = "subject-789"
        val typ = "typ2"

        val dto = AccountInfoDto(
            acr = acr,
            allowedOrigins = allowedOrigins,
            aud = aud,
            azp = azp,
            email = email,
            emailVerified = emailVerified,
            exp = exp,
            familyName = familyName,
            givenName = givenName,
            iat = iat,
            iss = iss,
            jti = jti,
            name = name,
            preferredUsername = preferredUsername,
            realmAccess = realmAccess,
            resourceAccess = resourceAccess,
            scope = scope,
            sid = sid,
            sub = sub,
            typ = typ
        )

        val domain = dto.toDomain()

        assertEquals(email, domain.email)
        assertEquals(null, domain.fullName)
        assertEquals(null, domain.givenName)
        assertEquals(null, domain.familyName)
        assertEquals(sub, domain.sub)
        assertEquals(sid, domain.sessionId)
    }
}







