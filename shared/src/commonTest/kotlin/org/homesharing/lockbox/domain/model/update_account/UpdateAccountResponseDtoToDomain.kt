package org.homesharing.lockbox.domain.model.update_account

import org.homesharing.lockbox.data.network.dto.update_account.UpdateAccountResponseDto
import kotlin.test.Test
import kotlin.test.assertEquals

class UpdateAccountResponseDtoToDomainTest {

    @Test
    fun `maps correctly with appropriate data`() {
        val email = "user@example.com"
        val emailVerified = true
        val enabled = true
        val firstName = "John"
        val lastName = "Doe"
        val username = "johndoe"
        val id = "user-id-123"

        val dto = UpdateAccountResponseDto(
            email = email,
            emailVerified = emailVerified,
            enabled = enabled,
            firstName = firstName,
            id = id,
            lastName = lastName,
            username = username,
        )

        val domain = dto.toDomain()

        assertEquals(email, domain.email)
        assertEquals(emailVerified, domain.emailVerified)
        assertEquals(enabled, domain.enabled)
        assertEquals(firstName, domain.firstName)
        assertEquals(lastName, domain.lastName)
        assertEquals(username, domain.username)
    }

    @Test
    fun `maps correctly with nullable fields as null`() {
        val dto = UpdateAccountResponseDto(
            email = "another@example.com",
            emailVerified = false,
            enabled = false,
            firstName = null,
            id = "user-456",
            lastName = null,
            username = "anotheruser"
        )

        val domain = dto.toDomain()

        assertEquals("another@example.com", domain.email)
        assertEquals(false, domain.emailVerified)
        assertEquals(false, domain.enabled)
        assertEquals(null, domain.firstName)
        assertEquals(null, domain.lastName)
        assertEquals("anotheruser", domain.username)
    }
}