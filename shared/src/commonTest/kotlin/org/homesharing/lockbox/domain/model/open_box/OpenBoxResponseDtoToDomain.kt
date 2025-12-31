package org.homesharing.lockbox.domain.model.open_box

import kotlin.test.Test
import kotlin.test.assertEquals
import org.homesharing.lockbox.data.network.dto.open_box.OpenBoxResponseDto

class OpenBoxResponseDtoToDomainTest {

    @Test
    fun `maps correctly with typical call value`() {
        val dto = OpenBoxResponseDto(
            call = "CALL_123",
            id = 42,
            issuerSub = "issuer-xyz",
            issuerToken = "token-abc",
            macAddress = "AA:BB:CC:DD:EE:FF",
            timestamp = 0.0
        )

        val domain = dto.toDomain()

        assertEquals("CALL_123", domain.call)
    }

    @Test
    fun `maps correctly with empty call`() {
        val dto = OpenBoxResponseDto(
            call = "",
            id = 1,
            issuerSub = "issuer-1",
            issuerToken = null,
            macAddress = "11:22:33:44:55:66",
            timestamp = 0.0
        )

        val domain = dto.toDomain()

        assertEquals("", domain.call)
    }

    @Test
    fun `maps correctly with whitespace call`() {
        val dto = OpenBoxResponseDto(
            call = "   ",
            id = 2,
            issuerSub = "issuer-2",
            issuerToken = "tok",
            macAddress = "22:33:44:55:66:77",
            timestamp = 0.0
        )

        val domain = dto.toDomain()

        assertEquals("   ", domain.call)
    }
}
