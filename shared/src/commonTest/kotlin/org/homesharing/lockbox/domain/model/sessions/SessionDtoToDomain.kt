package org.homesharing.lockbox.domain.model.sessions

import org.homesharing.lockbox.data.network.dto.sessions.SessionDto
import kotlin.test.Test
import kotlin.test.assertEquals

class SessionDtoToDomainTest {

    @Test
    fun `maps correctly with typical data`() {
        val id = "sess-1"
        val ipAddress = "192.168.0.1"
        val lastAccess = 1625077800000L
        val rememberMe = true
        val start = 1625070000000L
        val transientUser = false
        val userId = "user-123"
        val username = "johndoe"
        val clients = mapOf("app" to "1.0")

        val dto = SessionDto(
            id = id,
            ipAddress = ipAddress,
            lastAccess = lastAccess,
            rememberMe = rememberMe,
            start = start,
            transientUser = transientUser,
            userId = userId,
            username = username,
            clients = clients
        )

        val domain: Session = dto.toDomain()

        assertEquals(ipAddress, domain.ipAddress)
        assertEquals(lastAccess, domain.lastAccessed)
        assertEquals(id, domain.sessionId)
    }

    @Test
    fun `maps correctly with empty fields`() {
        val dto = SessionDto(
            id = "",
            ipAddress = "",
            lastAccess = 0L,
            rememberMe = false,
            start = 0L,
            transientUser = true,
            userId = "",
            username = "",
            clients = emptyMap()
        )

        val domain = dto.toDomain()

        assertEquals("", domain.ipAddress)
        assertEquals(0L, domain.lastAccessed)
        assertEquals("", domain.sessionId)
    }
}