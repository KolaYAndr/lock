package org.homesharing.lockbox.domain.model.flat_list

import org.homesharing.lockbox.data.network.dto.flat_list.FlatListDto
import org.homesharing.lockbox.domain.model.flat.toDomain

internal fun FlatListDto.toDomain(): FlatList = FlatList(
    flats = items.map { it.toDomain() },
    nextCursor = nextCursor
)