package org.homesharing.lockbox.domain.model.box

import kotlinx.coroutines.runBlocking
import kotlinx.datetime.LocalDateTime
import org.homesharing.lockbox.data.network.dto.box.BoxDto
import org.homesharing.lockbox.data.network.dto.box.BoxWithStatusDto
import org.homesharing.lockbox.data.network.dto.box.StatusDto
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class BoxResponseDtoToDomainTest {

    @Test
    fun `maps BoxResponseDto correctly with typical data`() = runBlocking {
        val address = "123 Main St"
        val alias = "OfficeBox"
        val ownerSub = "owner-xyz"
        val mac = "AA:BB:CC:DD:EE:FF"

        val dto = BoxDto(
            address = address,
            alias = alias,
            ownerSub = ownerSub,
            macAddress = mac,
        )

        val domain = dto.toDomain()

        assertEquals(address, domain.address)
        assertEquals(alias, domain.alias)
        assertEquals(ownerSub, domain.ownerSub)
        assertEquals(mac, domain.macAddress)
    }
}

class StatusDtoToDomainTest {

    @Test
    fun `maps StatusDto and parses timestamp correctly`() = runBlocking {
        val mac = "AA:BB:CC:DD:EE:FF"
        val opened = false
        val battery = 20.23939334833422
        val signal = -61.65466085511442
        val ts = "2025-06-10T12:34:56"

        val dto = StatusDto(
            macAddress = mac,
            opened = opened,
            batteryLevel = battery,
            signalStrength = signal,
            timestamp = ts
        )

        val domain = dto.toDomain()

        val expectedTime = LocalDateTime(2025, 6, 10, 12, 34, 56)

        assertEquals(mac, domain.macAddress)
        assertEquals(opened, domain.opened)
        assertEquals(battery, domain.batteryLevel)
        assertEquals(signal, domain.signalStrength)
        assertEquals(expectedTime, domain.timeStamp)
    }
}

class BoxWithStatusResponseDtoToDomainTest {

    @Test
    fun `maps BoxWithStatusResponseDto combining box and status`() = runBlocking {
        val boxDto = BoxDto(
            address = "Addr",
            alias = "Alias",
            ownerSub = "owner",
            macAddress = "MAC1",
        )

        val statusDto = StatusDto(
            macAddress = "MAC1",
            opened = true,
            batteryLevel = 10.0,
            signalStrength = -10.0,
            timestamp = "2025-06-10T10:00:00"
        )

        val dto = BoxWithStatusDto(box = boxDto, status = statusDto)

        val domain = dto.toDomain()

        assertNotNull(domain.box)
        assertNotNull(domain.status)

        assertEquals("Addr", domain.box.address)
        assertEquals("Alias", domain.box.alias)
        assertEquals("owner", domain.box.ownerSub)
        assertEquals("MAC1", domain.box.macAddress)

        assertEquals("MAC1", domain.status.macAddress)
        assertEquals(true, domain.status.opened)
        assertEquals(10.0, domain.status.batteryLevel)
        assertEquals(-10.0, domain.status.signalStrength)
        assertEquals(LocalDateTime(2025, 6, 10, 10, 0, 0), domain.status.timeStamp)
    }
}
