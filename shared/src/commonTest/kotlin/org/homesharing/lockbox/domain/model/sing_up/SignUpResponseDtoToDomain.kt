package org.homesharing.lockbox.domain.model.sing_up

import org.homesharing.lockbox.data.network.dto.sign_up.SignUpResponseDto
import kotlinx.coroutines.runBlocking
import org.homesharing.lockbox.data.network.dto.sign_up.UserBody
import org.homesharing.lockbox.domain.model.sign_up.toDomain
import kotlin.test.Test
import kotlin.test.assertEquals

class SignUpResponseDtoToDomainTest {

    @Test
    fun `maps correctly with all fields present`() = runBlocking {
        val id = "87d644ba-22a9-406f-8d85-049b04309e31"
        val username = "aboba@mail.ru"
        val email = "aboba@mail.ru"
        val firstName = "John"
        val lastName = "Doe"
        val emailVerified = true
        val enabled = true

        val userDto = UserBody(
            id = id,
            username = username,
            email = email,
            firstName = firstName,
            lastName = lastName,
            emailVerified = emailVerified,
            enabled = enabled
        )

        val dto = SignUpResponseDto(user = userDto)

        val domain = dto.toDomain()

        assertEquals(id, domain.id)
        assertEquals(username, domain.username)
        assertEquals(email, domain.email)
        assertEquals(firstName, domain.firstName)
        assertEquals(lastName, domain.lastName)
        assertEquals(emailVerified, domain.emailVerified)
        assertEquals(enabled, domain.enabled)
    }

    @Test
    fun `maps correctly with nullable name fields null`() = runBlocking {
        val id = "87d644ba-22a9-406f-8d85-049b04309e31"
        val username = "aboba"
        val email = "aboba@mail.ru"
        val firstName: String? = null
        val lastName: String? = null
        val emailVerified = false
        val enabled = false

        val userDto = UserBody(
            id = id,
            username = username,
            email = email,
            firstName = firstName,
            lastName = lastName,
            emailVerified = emailVerified,
            enabled = enabled
        )


        val dto = SignUpResponseDto(user = userDto)

        val domain = dto.toDomain()

        assertEquals(id, domain.id)
        assertEquals(username, domain.username)
        assertEquals(email, domain.email)
        assertEquals(firstName, domain.firstName)
        assertEquals(lastName, domain.lastName)
        assertEquals(emailVerified, domain.emailVerified)
        assertEquals(enabled, domain.enabled)
    }
}
