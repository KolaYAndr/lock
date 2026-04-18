package org.homesharing.lockbox.domain.model.update_box

import org.homesharing.lockbox.data.network.dto.update_box.UpdateBoxDto
import kotlin.test.Test
import kotlin.test.assertEquals

class UpdateBoxResponseDtoToDomainTest {

    @Test
    fun `maps correctly with appropriate data`() {
        val address = "Box Address 42"
        val alias = "Main Entrance"
        val macAddress = "AA:BB:CC:DD:EE:FF"
        val ownerSub = "123"

        val dto = UpdateBoxDto(
            address = address,
            alias = alias,
            macAddress = macAddress,
            ownerSub = ownerSub
        )

        val domain = dto.toDomain()

        assertEquals(alias, domain.alias)
        assertEquals(address, domain.address)
    }
}