package org.homesharing.lockbox.domain.model.access

import kotlinx.datetime.LocalDateTime
import org.homesharing.lockbox.data.network.dto.accessed.AccessDto
import org.homesharing.lockbox.data.network.dto.accessed.AccessResponseDto
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
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
        val token1 = "token1"
        val mac1 = "AA:AA:AA:AA:AA:AA"
        val alias1 = "Alias1"
        val id1 = "id-1"
        val dto1 = AccessDto(
            alias = alias1,
            startTime = "2025-06-10T08:00:00",
            endTime = "2025-06-10T09:00:00",
            macAddress = mac1,
            token = token1,
            uuid = id1
        )

        val alias2: String? = null
        val mac2 = "BB:BB:BB:BB:BB:BB"
        val id2 = "id-2"
        val token2 = "token2"
        val dto2 = AccessDto(
            alias = alias2,
            startTime = "2025-07-01T10:15:30",
            endTime = "2025-07-01T11:45:00",
            macAddress = mac2,
            token = token2,
            uuid = id2
        )
        val responseDto = AccessResponseDto(listOfAccesses = listOf(dto1, dto2))

        val domainWrapper = responseDto.toDomain()

        assertEquals(2, domainWrapper.accessList.size)

        val expected1 = Access(
            name = alias1,
            startTime = LocalDateTime(2025, 6, 10, 8, 0, 0),
            endTime = LocalDateTime(2025, 6, 10, 9, 0, 0),
            macAddress = mac1,
            token = token1,
            id = id1
        )
        val expected2 = Access(
            name = alias2,
            startTime = LocalDateTime(2025, 7, 1, 10, 15, 30),
            endTime = LocalDateTime(2025, 7, 1, 11, 45, 0),
            macAddress = mac2,
            token = token2,
            id = id2
        )

        assertEquals(expected1, domainWrapper.accessList[0])
        assertEquals(expected2, domainWrapper.accessList[1])
    }

    @Test
    fun `throws with inappropriate time data`() {
        val invalidDto = AccessDto(
            alias = "Bad",
            startTime = "not-a-date",
            endTime = "2025-06-10T09:00:00",
            macAddress = "DD:DD:DD:DD:DD:DD",
            token = "tok-bad",
            uuid = "id-bad"
        )
        val responseDto = AccessResponseDto(listOfAccesses = listOf(invalidDto))

        assertFailsWith<IllegalArgumentException> {
            responseDto.toDomain()
        }
    }


    @Test
    fun `milliseconds parse correctly`() {
        val dto = AccessDto(
            alias = "ms",
            startTime = "2025-06-10T10:15:30.123",
            endTime = "2025-06-10T10:16:30.456",
            macAddress = "EE:EE:EE:EE:EE:EE",
            token = "tok-ms",
            uuid = "id-ms"
        )
        val responseDto = AccessResponseDto(listOfAccesses = listOf(dto))

        val domainWrapper = responseDto.toDomain()
        val item = domainWrapper.accessList.single()

        assertEquals(LocalDateTime.parse("2025-06-10T10:15:30.123"), item.startTime)
        assertEquals(LocalDateTime.parse("2025-06-10T10:16:30.456"), item.endTime)
    }
}
