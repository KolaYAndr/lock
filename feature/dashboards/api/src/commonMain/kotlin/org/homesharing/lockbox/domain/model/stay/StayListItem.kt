package org.homesharing.lockbox.domain.model.stay

import org.homesharing.lockbox.domain.model.ContractStatus
import org.homesharing.lockbox.domain.model.DocumentStatus

data class StayListItem(
    val bookingId: String,
    val contractId: String?,
    val contractStatus: DocumentStatus,
    val documentStatus: DocumentStatus?,
    val agreementStatus: ContractStatus?,
    val currentVersionId: String?,
    val plannedPhase: PlannedPhase?,
    val flatId: String,
    val flatName: String,
    val flatAddress: String,
    val guestName: String?,
    val guestEmail: String?,
    val startDate: String,
    val endDate: String,
    val numberOfGuests: Int,
    val totalPrice: Long,
)
