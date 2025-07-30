package org.homesharing.lockbox.domain.model.mockbox

import kotlinx.coroutines.runBlocking
import org.homesharing.lockbox.data.network.dto.mockbox.MockboxDto
import kotlin.test.Test
import kotlin.test.assertEquals

class MockboxDtoToDomainTest {

    @Test
    fun `maps correctly with typical data`() = runBlocking {
        val mac = "AA:BB:CC:DD:EE:FF"
        val alias = "FrontDoor"

        val dto = MockboxDto(
            macAddress = mac,
            alias = alias
        )

        val domain = dto.toDomain()

        assertEquals(mac, domain.macAddress)
        assertEquals(alias, domain.alias)
    }

    @Test
    fun `maps correctly with empty strings`() = runBlocking {
        val mac = ""
        val alias = ""

        val dto = MockboxDto(
            macAddress = mac,
            alias = alias
        )

        val domain = dto.toDomain()

        assertEquals(mac, domain.macAddress)
        assertEquals(alias, domain.alias)
    }
}
