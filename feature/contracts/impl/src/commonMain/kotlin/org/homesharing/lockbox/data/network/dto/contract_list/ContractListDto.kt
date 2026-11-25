package org.homesharing.lockbox.data.network.dto.contract_list

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class ContractListDto(
    val items: List<ContractListItemDto>,
    @SerialName("next_cursor")
    val nextCursor: String?,
) {
    @Serializable
    internal data class ContractListItemDto(
        val id: String,
        val status: ContractStatusDto?,
        @SerialName("current_version_id")
        val currentVersionId: String?,
        val city: String,
        val date: String,
        @SerialName("document_status")
        val documentStatus: ContractDocumentStatusDto,
        @SerialName("flat_address")
        val flatAddress: String,
    )
}
