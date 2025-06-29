package org.homesharing.lockbox.domain.model.access

import kotlinx.datetime.LocalDateTime
import org.homesharing.lockbox.data.network.dto.accessed.AccessDto
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class AccessDtoToDomainTest {

    @Test
    fun `maps correctly with appropriate data`() {
        val alias = "TestAlias"
        val startTime = "2025-06-10T10:15:30"
        val endTime = "2025-06-10T12:45:00"
        val macAddress = "1A:2C:3D:4C"
        val token = "tokenFor123"
        val id = "123"

        val dto = AccessDto(
            alias = alias,
            startTime = startTime,
            endTime = endTime,
            macAddress = macAddress,
            token = token,
            uuid = id,
        )

        val domain = dto.toDomain()

        val expectedStart = LocalDateTime(2025, 6, 10, 10, 15, 30)
        val expectedEnd = LocalDateTime(2025, 6, 10, 12, 45, 0)

        assertEquals(alias, domain.name)
        assertEquals(expectedStart, domain.startTime)
        assertEquals(expectedEnd, domain.endTime)
        assertEquals(macAddress, domain.macAddress)
        assertEquals(token, domain.token)
        assertEquals(id, domain.id)
    }

    @Test
    fun `maps correctly with null alias and appropriate data`() {
        val alias: String? = null
        val startTime = "2025-06-10T10:15:30"
        val endTime = "2025-06-10T12:45:00"
        val macAddress = "1A:2C:3D:4C"
        val token = "tokenFor123"
        val id = "123"

        val dto = AccessDto(
            alias = alias,
            startTime = startTime,
            endTime = endTime,
            macAddress = macAddress,
            token = token,
            uuid = id,
        )

        val domain = dto.toDomain()

        val expectedStart = LocalDateTime(2025, 6, 10, 10, 15, 30)
        val expectedEnd = LocalDateTime(2025, 6, 10, 12, 45, 0)

        assertEquals(alias, domain.name)
        assertEquals(expectedStart, domain.startTime)
        assertEquals(expectedEnd, domain.endTime)
        assertEquals(macAddress, domain.macAddress)
        assertEquals(token, domain.token)
        assertEquals(id, domain.id)
    }

    @Test
    fun `throws with inappropriate time data`() {
        val alias = "TestAlias"
        val startTime = "not-a-date"
        val endTime = "not-a-date"
        val macAddress = "1A:2C:3D:4C"
        val token = "tokenFor123"
        val id = "123"

        val dto = AccessDto(
            alias = alias,
            startTime = startTime,
            endTime = endTime,
            macAddress = macAddress,
            token = token,
            uuid = id,
        )

        assertFailsWith<IllegalArgumentException> {
            dto.toDomain()
        }
    }
}
