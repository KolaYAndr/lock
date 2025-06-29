package org.homesharing.lockbox.domain.model.update_box

import org.homesharing.lockbox.data.network.dto.update_box.UpdateBoxResponseDto

internal fun UpdateBoxResponseDto.toDomain(): UpdateBox = UpdateBox(alias = alias, address = address)
