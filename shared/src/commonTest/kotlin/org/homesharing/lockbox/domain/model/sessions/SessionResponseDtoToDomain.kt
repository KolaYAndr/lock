package org.homesharing.lockbox.domain.model.sessions

import org.homesharing.lockbox.data.network.dto.sessions.SessionDto
import org.homesharing.lockbox.data.network.dto.sessions.SessionsResponseDto
import kotlin.test.Test
import kotlin.test.assertEquals

class SessionsResponseDtoToDomainTest {
    @Test
    fun `maps correctly with empty sessions list`() {
        val responseDto = SessionsResponseDto(
            credentials = emptyList(),
            sessions = emptyList()
        )

        val domain = responseDto.toDomain()

        assertEquals(0, domain.sessions.size)
    }

    @Test
    fun `maps correctly with multiple sessions`() {
        val dto1 = SessionDto(
            id = "s1",
            ipAddress = "10.0.0.1",
            lastAccess = 1000L,
            rememberMe = false,
            start = 500L,
            transientUser = false,
            userId = "u1",
            username = "user1",
            clients = mapOf("c1" to "v1")
        )
        val dto2 = SessionDto(
            id = "s2",
            ipAddress = "10.0.0.2",
            lastAccess = 2000L,
            rememberMe = true,
            start = 1500L,
            transientUser = true,
            userId = "u2",
            username = "user2",
            clients = mapOf("c2" to "v2")
        )
        val responseDto = SessionsResponseDto(
            credentials = emptyList(),
            sessions = listOf(dto1, dto2)
        )

        val domain = responseDto.toDomain()

        assertEquals(2, domain.sessions.size)

        val session1 = domain.sessions[0]
        assertEquals("10.0.0.1", session1.ipAddress)
        assertEquals(1000L, session1.lastAccessed)
        assertEquals("s1", session1.sessionId)

        val session2 = domain.sessions[1]
        assertEquals("10.0.0.2", session2.ipAddress)
        assertEquals(2000L, session2.lastAccessed)
        assertEquals("s2", session2.sessionId)
    }
}
