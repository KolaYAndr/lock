package org.homesharing.lockbox.data.network.dto.contract_list

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal enum class ContractDocumentStatusDto {
    @SerialName("pending")
    Pending,

    @SerialName("created")
    Created,

    @SerialName("failed")
    Failed,

    @SerialName("none")
    None,
}
