package org.homesharing.lockbox.domain.model.flat_list

import org.homesharing.lockbox.domain.model.flat.Flat

data class FlatList(
    val flats: List<Flat>,
    val nextCursor: String?,
)