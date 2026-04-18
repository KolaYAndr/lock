package org.homesharing.lockbox.data.network.dto.flat_list

import kotlinx.serialization.Serializable
import org.homesharing.lockbox.data.network.dto.flat.FlatDto

@Serializable
internal data class FlatListDto(
    val items: List<FlatDto>,
    val nextCursor: String,
)