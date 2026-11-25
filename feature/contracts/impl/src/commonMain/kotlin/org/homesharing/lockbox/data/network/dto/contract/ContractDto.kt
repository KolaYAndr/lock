package org.homesharing.lockbox.data.network.dto.contract

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.homesharing.lockbox.data.network.dto.contract_list.ContractDocumentStatusDto
import org.homesharing.lockbox.data.network.dto.contract_list.ContractStatusDto

@Serializable
internal data class ContractDto(
    val id: String,
    val status: ContractStatusDto,
    @SerialName("document_status")
    val documentStatus: ContractDocumentStatusDto,
    @SerialName("current_version_id")
    val currentVersionId: String?,
    val confirmations: List<ContractConfirmationDto>,
    val events: List<ContractEventDto>,
    @SerialName("contract_city")
    val contractCity: String,
    @SerialName("contract_date")
    val contractDate: String,
    @SerialName("landlord_info")
    val landlordInfo: ContractProfileDto,
    @SerialName("renter_info")
    val renterInfo: ContractProfileDto,
    val flat: ContractFlatDto,
    val booking: BookingDto,
) {
    @Serializable
    internal data class BookingDto(
        @SerialName("start_time")
        val startTime: String,
        @SerialName("end_time")
        val endTime: String,
        @SerialName("day_price")
        val dayPrice: Int,
        @SerialName("total_price")
        val totalPrice: Int,
        @SerialName("number_of_people")
        val numberOfPeople: Int,
        val prepayment: Int?,
        @SerialName("remaining_price")
        val remainingPrice: Int?,
        val deposit: Int,
    )

    @Serializable
    internal data class ContractConfirmationDto(
        val id: String,
        @SerialName("version_id")
        val versionId: String,
        val party: ContractPartyDto,
        @SerialName("actor_id")
        val actorId: String,
        val email: String,
        @SerialName("snapshot_sha256")
        val snapshotSha256: String,
        @SerialName("pdf_sha256")
        val pdfSha256: String,
        @SerialName("confirmed_at")
        val confirmedAt: String,
    )

    @Serializable
    internal data class ContractEventDto(
        val id: Long,
        val type: String,
        @SerialName("actor_id")
        val actorId: String?,
        @SerialName("version_id")
        val versionId: String?,
        val reason: String,
        @SerialName("created_at")
        val createdAt: String,
    )
}
