package org.homesharing.lockbox.domain.model.open_box

import org.homesharing.lockbox.data.network.dto.open_box.OpenBoxResponseDto

fun OpenBoxResponseDto.toDomain(): OpenBox = OpenBox(call = call)
