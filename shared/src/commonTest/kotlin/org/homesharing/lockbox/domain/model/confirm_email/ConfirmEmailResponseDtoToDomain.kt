package org.homesharing.lockbox.domain.model.confirm_email

import kotlinx.coroutines.runBlocking
import org.homesharing.lockbox.data.network.dto.confirm_email.ConfirmEmailDto
import kotlin.test.Test
import kotlin.test.assertEquals

class ConfirmEmailResponseDtoToDomainTest {

    @Test
    fun `maps correctly with typical response`() = runBlocking {
        val responseText = "Sent"
        val dto = ConfirmEmailDto(responseText)

        val domain = dto.toDomain()

        assertEquals(responseText, domain.response)
    }

    @Test
    fun `maps correctly with empty response`() = runBlocking {
        val responseText = ""
        val dto = ConfirmEmailDto(responseText)

        val domain = dto.toDomain()

        assertEquals(responseText, domain.response)
    }
}
