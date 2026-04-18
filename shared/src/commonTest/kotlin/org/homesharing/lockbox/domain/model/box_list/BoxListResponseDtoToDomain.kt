package org.homesharing.lockbox.domain.model.box_list

import kotlinx.coroutines.runBlocking
import kotlinx.datetime.LocalDateTime
import org.homesharing.lockbox.data.network.dto.box.BoxDto
import org.homesharing.lockbox.data.network.dto.box.BoxWithStatusDto
import org.homesharing.lockbox.data.network.dto.box.StatusDto
import org.homesharing.lockbox.data.network.dto.box_list.BoxListDto
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class BoxListResponseDtoToDomainTest {

    @Test
    fun `maps correctly with multiple boxes`() = runBlocking {
        val boxDto1 = BoxDto(
            address = "Addr1",
            alias = "Alias1",
            ownerSub = "owner1",
            macAddress = "MAC1",
        )
        val statusDto1 = StatusDto(
            macAddress = "MAC1",
            opened = true,
            batteryLevel = 50.0,
            signalStrength = -50.0,
            timestamp = "2025-06-10T10:00:00"
        )
        val boxWithStatusDto1 = BoxWithStatusDto(box = boxDto1, status = statusDto1)

        val boxDto2 = BoxDto(
            address = "Addr2",
            alias = "Alias2",
            ownerSub = "owner2",
            macAddress = "MAC2",
        )
        val statusDto2 = StatusDto(
            macAddress = "MAC2",
            opened = false,
            batteryLevel = 10.0,
            signalStrength = -70.0,
            timestamp = "2025-06-11T11:30:00"
        )
        val boxWithStatusDto2 = BoxWithStatusDto(box = boxDto2, status = statusDto2)

        val responseDto = BoxListDto(
            boxes = listOf(boxWithStatusDto1, boxWithStatusDto2),
            total = 2,
            page = 0,
            size = 10
        )

        val domain = responseDto.toDomain()

        assertNotNull(domain)
        assertEquals(2, domain.boxes.size)
        assertEquals(2, domain.total)
        assertEquals(0, domain.page)
        assertEquals(10, domain.size)

        val first = domain.boxes[0]
        assertEquals("Addr1", first.box.address)
        assertEquals("Alias1", first.box.alias)
        assertEquals("owner1", first.box.ownerSub)
        assertEquals("MAC1", first.box.macAddress)
        assertEquals(true, first.status.opened)
        assertEquals(50.0, first.status.batteryLevel)
        assertEquals(-50.0, first.status.signalStrength)
        assertEquals(LocalDateTime(2025, 6, 10, 10, 0, 0), first.status.timeStamp)

        val second = domain.boxes[1]
        assertEquals("Addr2", second.box.address)
        assertEquals("Alias2", second.box.alias)
        assertEquals("owner2", second.box.ownerSub)
        assertEquals("MAC2", second.box.macAddress)
        assertEquals(false, second.status.opened)
        assertEquals(10.0, second.status.batteryLevel)
        assertEquals(-70.0, second.status.signalStrength)
        assertEquals(LocalDateTime(2025, 6, 11, 11, 30, 0), second.status.timeStamp)
    }

    @Test
    fun `maps correctly with empty boxes list`() = runBlocking {
        val responseDto = BoxListDto(
            boxes = emptyList(),
            total = 0,
            page = 1,
            size = 0
        )

        val domain = responseDto.toDomain()

        assertNotNull(domain)
        assertEquals(0, domain.boxes.size)
        assertEquals(0, domain.total)
        assertEquals(1, domain.page)
        assertEquals(0, domain.size)
    }
}
