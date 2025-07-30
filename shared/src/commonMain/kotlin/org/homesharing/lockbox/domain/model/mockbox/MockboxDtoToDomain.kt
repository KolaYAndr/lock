package org.homesharing.lockbox.domain.model.mockbox

import org.homesharing.lockbox.data.network.dto.mockbox.MockboxDto

internal fun MockboxDto.toDomain(): Mockbox = Mockbox(macAddress = macAddress, alias = alias)
