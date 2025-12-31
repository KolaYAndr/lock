package org.homesharing.lockbox.domain.model.reset_password

import org.homesharing.lockbox.data.network.dto.reset_password.ResetPasswordResponseDto
import kotlin.test.Test
import kotlin.test.assertEquals

class ResetPasswordResponseDtoToDomainTest {

    @Test
    fun `maps correctly with non-empty message`() {
        val dto = ResetPasswordResponseDto(message = "password reset")
        val domain = dto.toDomain()
        assertEquals("password reset", domain.result)
    }

    @Test
    fun `maps correctly with empty message`() {
        val dto = ResetPasswordResponseDto(message = "")
        val domain = dto.toDomain()
        assertEquals("", domain.result)
    }

    @Test
    fun `maps correctly with whitespace message`() {
        val dto = ResetPasswordResponseDto(message = "   ")
        val domain = dto.toDomain()
        assertEquals("   ", domain.result)
    }
}
