package org.homesharing.lockbox.domain.model.open_box

import org.homesharing.lockbox.data.network.dto.open_box.OpenBoxDto

internal fun OpenBoxDto.toDomain() = OpenBox(call = call)
