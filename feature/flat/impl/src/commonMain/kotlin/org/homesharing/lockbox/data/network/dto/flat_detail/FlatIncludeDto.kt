package org.homesharing.lockbox.data.network.dto.flat_detail

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal enum class FlatIncludeDto {
    @SerialName("invites")
    Invites,
}

internal val FlatIncludeDto.apiValue: String
    get() = FlatIncludeDto.serializer().descriptor.getElementName(ordinal)
