package org.homesharing.lockbox.domain.model.create_linking_code

import kotlinx.coroutines.runBlocking
import org.homesharing.lockbox.data.network.dto.create_linking_code.CreateLinkingCodeDto
import kotlin.test.Test
import kotlin.test.assertEquals

class CreateLinkingCodeDtoToDomainTest {

    @Test
    fun `maps correctly with positive code`() = runBlocking {
        val dtoCode = 12345
        val dto = CreateLinkingCodeDto(code = dtoCode)

        val domain = dto.toDomain()

        assertEquals(dtoCode, domain.code)
    }

    @Test
    fun `maps correctly with zero code`() = runBlocking {
        val dtoCode = 0
        val dto = CreateLinkingCodeDto(code = dtoCode)

        val domain = dto.toDomain()

        assertEquals(dtoCode, domain.code)
    }
}
