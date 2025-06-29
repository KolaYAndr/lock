package org.homesharing.lockbox.domain.model.box

import org.homesharing.lockbox.data.network.dto.box.BoxResponseDto
import kotlin.test.Test
import kotlin.test.assertEquals

class BoxResponseDtoToDomainTest {

    @Test
    fun `maps correctly with typical data`() {
        val address = "123 Main St"
        val alias = "HomeBox"
        val ownerSub = "owner-xyz"
        val batteryLevel = 78.5
        val macAddress = "AA:BB:CC:DD:EE:FF"
        val opened = true
        val signalStrength = -45.0
        val timestamp = "2025-06-10T12:00:00Z"

        val dto = BoxResponseDto(
            address = address,
            alias = alias,
            ownerSub = ownerSub,
            batteryLevel = batteryLevel,
            macAddress = macAddress,
            opened = opened,
            signalStrength = signalStrength,
            timestamp = timestamp
        )

        val domain = dto.toDomain()

        assertEquals(address, domain.address)
        assertEquals(alias, domain.alias)
        assertEquals(batteryLevel, domain.batteryLevel)
        assertEquals(macAddress, domain.macAddress)
        assertEquals(opened, domain.opened)
        assertEquals(signalStrength, domain.signalStrength)
    }

    @Test
    fun `maps correctly with empty strings`() {
        val address = ""
        val alias = ""
        val ownerSub = ""
        val batteryLevel = 0.0
        val macAddress = ""
        val opened = false
        val signalStrength = 0.0
        val timestamp = ""

        val dto = BoxResponseDto(
            address = address,
            alias = alias,
            ownerSub = ownerSub,
            batteryLevel = batteryLevel,
            macAddress = macAddress,
            opened = opened,
            signalStrength = signalStrength,
            timestamp = timestamp
        )

        val domain = dto.toDomain()

        assertEquals(address, domain.address)
        assertEquals(alias, domain.alias)
        assertEquals(batteryLevel, domain.batteryLevel)
        assertEquals(macAddress, domain.macAddress)
        assertEquals(opened, domain.opened)
        assertEquals(signalStrength, domain.signalStrength)
    }
}
