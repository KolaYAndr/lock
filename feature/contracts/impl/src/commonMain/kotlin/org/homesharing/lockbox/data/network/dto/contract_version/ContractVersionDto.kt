package org.homesharing.lockbox.data.network.dto.contract_version

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.homesharing.lockbox.data.network.dto.contract.ContractDto
import org.homesharing.lockbox.data.network.dto.contract_list.ContractDocumentStatusDto

@Serializable
internal data class ContractVersionDto(
    val id: String,
    @SerialName("contract_id")
    val contractId: String,
    val number: Int,
    val snapshot: ContractDto,
    @SerialName("snapshot_sha256")
    val snapshotSha256: String,
    @SerialName("document_status")
    val documentStatus: ContractDocumentStatusDto,
    @SerialName("pdf_sha256")
    val pdfSha256: String?,
    @SerialName("download_url")
    val downloadUrl: String?,
    val confirmations: List<ContractDto.ContractConfirmationDto>,
    @SerialName("created_at")
    val createdAt: String,
)
