package org.homesharing.lockbox.data.network.dto.contact_file

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class ContractFileDto(
    @SerialName("document_status")
    val documentStatus: String,
    @SerialName("download_url")
    val downloadUrl: String,
    val id: String
)