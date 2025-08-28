package org.homesharing.lockbox.domain.model.close_sessions

import org.homesharing.lockbox.data.network.dto.close_sessions.CloseSessionsResponseDto
import kotlin.test.Test
import kotlin.test.assertEquals

class CloseSessionsResponseDtoToDomainTest {

    @Test
    fun `maps correctly with non-empty message`() {
        val message = "sessions closed"
        val responseDto = CloseSessionsResponseDto(message = message)

        val domain = responseDto.toDomain()

        assertEquals(message, domain.result)
    }

    @Test
    fun `maps correctly with empty message`() {
        val message = ""
        val responseDto = CloseSessionsResponseDto(message = message)

        val domain = responseDto.toDomain()

        assertEquals(message, domain.result)
    }

    @Test
    fun `maps correctly with whitespace message`() {
        val message = "   "
        val responseDto = CloseSessionsResponseDto(message = message)

        val domain = responseDto.toDomain()

        assertEquals(message, domain.result)
    }

    @Test
    fun `maps correctly with special characters in message`() {
        val message = "!@#$%^&*()_+"
        val responseDto = CloseSessionsResponseDto(message = message)

        val domain = responseDto.toDomain()

        assertEquals(message, domain.result)
    }
}
