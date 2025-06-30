package org.homesharing.lockbox.data.network.dto.create_linking_code

import kotlinx.serialization.Serializable

@Serializable
internal data class CreateLinkingCodeDto(
    val code: Int
)
