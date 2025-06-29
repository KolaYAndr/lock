package org.homesharing.lockbox.domain.model.access_revoke

import org.homesharing.lockbox.data.network.dto.access_revoke.AccessRevokeResponseDto
import kotlin.test.Test
import kotlin.test.assertEquals

class AccessRevokeResponseDtoToDomainTest {

    @Test
    fun `maps correctly with non-empty result`() {
        val resultValue = "revoked"
        val responseDto = AccessRevokeResponseDto(result = resultValue)

        val domain = responseDto.toDomain()

        assertEquals(resultValue, domain.result)
    }

    @Test
    fun `maps correctly with empty result`() {
        val resultValue = ""
        val responseDto = AccessRevokeResponseDto(result = resultValue)

        val domain = responseDto.toDomain()

        assertEquals(resultValue, domain.result)
    }

    @Test
    fun `maps correctly with whitespace result`() {
        val resultValue = "   "
        val responseDto = AccessRevokeResponseDto(result = resultValue)

        val domain = responseDto.toDomain()

        assertEquals(resultValue, domain.result)
    }

    @Test
    fun `maps correctly with special characters in result`() {
        val resultValue = "!@#\$%^&*()_+"
        val responseDto = AccessRevokeResponseDto(result = resultValue)

        val domain = responseDto.toDomain()

        assertEquals(resultValue, domain.result)
    }
}
