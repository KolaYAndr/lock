package org.homesharing.lockbox.domain.model.access

import kotlinx.datetime.LocalDateTime
import org.homesharing.lockbox.data.network.dto.access.AccessDto
import org.homesharing.lockbox.data.network.dto.access.AccessResponseDto
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class AccessResponseMapperTest {

    @Test
    fun `toDomain returns empty list when receiving empty list`() {
        val responseDto = AccessResponseDto(listOfAccesses = emptyList())

        val domain = responseDto.toDomain()

        assertTrue(domain.accessList.isEmpty())
    }

    @Test
    fun `properly maps with appropriate data`() {
        val mac1 = "AA:AA:AA:AA:AA:AA"
        val alias1 = "Alias1"
        val id1 = "id-1"
        val dto1 = AccessDto(
            alias = alias1,
            startTime = "2025-06-10T08:00:00",
            endTime = "2025-06-10T09:00:00",
            macAddress = mac1,
            uuid = id1
        )
        val responseDto = AccessResponseDto(listOfAccesses = listOf(dto1))

        val domainWrapper = responseDto.toDomain()

        assertEquals(1, domainWrapper.accessList.size)

        val expected1 = Access(
            name = alias1,
            startTime = LocalDateTime(2025, 6, 10, 8, 0, 0),
            endTime = LocalDateTime(2025, 6, 10, 9, 0, 0),
            macAddress = mac1,
            id = id1
        )

        assertEquals(expected1, domainWrapper.accessList[0])
    }


    @Test
    fun `milliseconds parse correctly`() {
        val dto = AccessDto(
            alias = "ms",
            startTime = "2025-06-10T10:15:30.123",
            endTime = "2025-06-10T10:16:30.456",
            macAddress = "EE:EE:EE:EE:EE:EE",
            uuid = "id-ms"
        )
        val responseDto = AccessResponseDto(listOfAccesses = listOf(dto))

        val domainWrapper = responseDto.toDomain()
        val item = domainWrapper.accessList.single()

        assertEquals(LocalDateTime.parse("2025-06-10T10:15:30.123"), item.startTime)
        assertEquals(LocalDateTime.parse("2025-06-10T10:16:30.456"), item.endTime)
    }
}
