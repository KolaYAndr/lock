package org.homesharing.lockbox.domain.model.create_linking_code

import org.homesharing.lockbox.data.network.dto.create_linking_code.CreateLinkingCodeDto

internal fun CreateLinkingCodeDto.toDomain() = CreateLinkingCode(code = code)
