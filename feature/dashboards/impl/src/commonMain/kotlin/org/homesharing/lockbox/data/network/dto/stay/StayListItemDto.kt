package org.homesharing.lockbox.data.network.dto.stay

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class StayListItemDto(
    @SerialName("booking_id")
    val bookingId: String,
    @SerialName("contract_id")
    val contractId: String?,
    @SerialName("contract_status")
    val contractStatus: DocumentStatusDto,
    @SerialName("document_status")
    val documentStatus: DocumentStatusDto?,
    @SerialName("agreement_status")
    val agreementStatus: ContractStatusDto?,
    @SerialName("current_version_id")
    val currentVersionId: String?,
    @SerialName("planned_phase")
    val plannedPhase: PlannedPhaseDto?,
    @SerialName("flat_id")
    val flatId: String,
    @SerialName("flat_name")
    val flatName: String,
    @SerialName("flat_address")
    val flatAddress: String,
    @SerialName("guest_name")
    val guestName: String?,
    @SerialName("guest_email")
    val guestEmail: String?,
    @SerialName("start_date")
    val startDate: String,
    @SerialName("end_date")
    val endDate: String,
    @SerialName("number_of_guests")
    val numberOfGuests: Int,
    @SerialName("total_price")
    val totalPrice: Long,
)
