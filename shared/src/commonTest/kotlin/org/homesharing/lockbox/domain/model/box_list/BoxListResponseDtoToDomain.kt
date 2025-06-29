package org.homesharing.lockbox.domain.model.box_list

import org.homesharing.lockbox.data.network.dto.box.BoxResponseDto
import org.homesharing.lockbox.data.network.dto.box_list.BoxListResponseDto
import kotlin.test.Test
import kotlin.test.assertEquals

class BoxListResponseDtoToDomainTest {

    @Test
    fun `maps correctly with empty boxes list`() {
        val responseDto = BoxListResponseDto(
            boxes = emptyList(),
            totalBoxes = 0,
            currentPage = 1,
            boxesOnPage = 0
        )

        val domain = responseDto.toDomain()

        assertEquals(0, domain.boxes.size)
        assertEquals(0, domain.totalBoxes)
        assertEquals(1, domain.currentPage)
        assertEquals(0, domain.boxesOnPage)
    }

    @Test
    fun `maps correctly with multiple boxes`() {
        val dto1 = BoxResponseDto(
            address = "123 Main St",
            alias = "Box1",
            ownerSub = "owner1",
            batteryLevel = 50.0,
            macAddress = "AA:BB:CC:DD:EE:01",
            opened = true,
            signalStrength = -40.0,
            timestamp = "2025-06-10T12:00:00Z"
        )
        val dto2 = BoxResponseDto(
            address = "456 Elm St",
            alias = "Box2",
            ownerSub = "owner2",
            batteryLevel = 75.5,
            macAddress = "AA:BB:CC:DD:EE:02",
            opened = false,
            signalStrength = -60.5,
            timestamp = "2025-06-11T08:30:00Z"
        )
        val responseDto = BoxListResponseDto(
            boxes = listOf(dto1, dto2),
            totalBoxes = 2,
            currentPage = 1,
            boxesOnPage = 2
        )

        val domain = responseDto.toDomain()

        assertEquals(2, domain.boxes.size)
        val box1 = domain.boxes[0]
        assertEquals("123 Main St", box1.address)
        assertEquals("Box1", box1.alias)
        assertEquals(50.0, box1.batteryLevel)
        assertEquals("AA:BB:CC:DD:EE:01", box1.macAddress)
        assertEquals(true, box1.opened)
        assertEquals(-40.0, box1.signalStrength)

        val box2 = domain.boxes[1]
        assertEquals("456 Elm St", box2.address)
        assertEquals("Box2", box2.alias)
        assertEquals(75.5, box2.batteryLevel)
        assertEquals("AA:BB:CC:DD:EE:02", box2.macAddress)
        assertEquals(false, box2.opened)
        assertEquals(-60.5, box2.signalStrength)

        assertEquals(2, domain.totalBoxes)
        assertEquals(1, domain.currentPage)
        assertEquals(2, domain.boxesOnPage)
    }
}
