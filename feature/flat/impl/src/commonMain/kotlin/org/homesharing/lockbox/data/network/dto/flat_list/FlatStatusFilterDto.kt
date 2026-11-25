package org.homesharing.lockbox.data.network.dto.flat_list

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal enum class FlatStatusFilterDto {
    @SerialName("active")
    Active,

    @SerialName("archived")
    Archived,

    @SerialName("all")
    All,
}

internal val FlatStatusFilterDto.apiValue: String
    get() = FlatStatusFilterDto.serializer().descriptor.getElementName(ordinal)
