package org.homesharing.lockbox.domain.model.physical_sign_up

import kotlinx.coroutines.runBlocking
import org.homesharing.lockbox.data.network.dto.physical_sign_up.PhysicalSignUpResponseDto
import kotlin.test.Test
import kotlin.test.assertEquals

class PhysicalSignUpResponseDtoToDomainTest {

    @Test
    fun `maps correctly with typical data`() = runBlocking {
        val macAddress = "AA:BB:CC:DD:EE:FF"
        val ownerSub = "owner-123"
        val address = "123 Main St"
        val alias = "Warehouse-1"

        val dto = PhysicalSignUpResponseDto(
            macAddress = macAddress,
            ownerSub = ownerSub,
            address = address,
            alias = alias
        )

        val domain = dto.toDomain()

        assertEquals(macAddress, domain.macAddress)
        assertEquals(ownerSub, domain.ownerSub)
        assertEquals(address, domain.address)
        assertEquals(alias, domain.alias)
    }

    @Test
    fun `maps correctly with empty strings`() = runBlocking {
        val macAddress = ""
        val ownerSub = ""
        val address = ""
        val alias = ""

        val dto = PhysicalSignUpResponseDto(
            macAddress = macAddress,
            ownerSub = ownerSub,
            address = address,
            alias = alias
        )

        val domain = dto.toDomain()

        assertEquals(macAddress, domain.macAddress)
        assertEquals(ownerSub, domain.ownerSub)
        assertEquals(address, domain.address)
        assertEquals(alias, domain.alias)
    }
}
