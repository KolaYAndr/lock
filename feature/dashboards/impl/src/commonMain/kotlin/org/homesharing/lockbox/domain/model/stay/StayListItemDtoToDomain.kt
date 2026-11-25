package org.homesharing.lockbox.domain.model.stay

import org.homesharing.lockbox.data.network.dto.stay.ContractStatusDto
import org.homesharing.lockbox.data.network.dto.stay.DocumentStatusDto
import org.homesharing.lockbox.data.network.dto.stay.PlannedPhaseDto
import org.homesharing.lockbox.data.network.dto.stay.StayListItemDto

internal fun StayListItemDto.toDomain() = StayListItem(
    bookingId = bookingId,
    contractId = contractId,
    contractStatus = contractStatus.toDomain(),
    documentStatus = documentStatus?.toDomain(),
    agreementStatus = agreementStatus?.toDomain(),
    currentVersionId = currentVersionId,
    plannedPhase = plannedPhase?.toDomain(),
    flatId = flatId,
    flatName = flatName,
    flatAddress = flatAddress,
    guestName = guestName,
    guestEmail = guestEmail,
    startDate = startDate,
    endDate = endDate,
    numberOfGuests = numberOfGuests,
    totalPrice = totalPrice,
)

internal fun DocumentStatusDto.toDomain(): DocumentStatus = when (this) {
    DocumentStatusDto.Pending -> DocumentStatus.Pending
    DocumentStatusDto.Created -> DocumentStatus.Created
    DocumentStatusDto.Failed -> DocumentStatus.Failed
    DocumentStatusDto.None -> DocumentStatus.None
}

internal fun ContractStatusDto.toDomain(): ContractStatus = when (this) {
    ContractStatusDto.Draft -> ContractStatus.Draft
    ContractStatusDto.Signing -> ContractStatus.Signing
    ContractStatusDto.Signed -> ContractStatus.Signed
    ContractStatusDto.Rejected -> ContractStatus.Rejected
    ContractStatusDto.Cancelled -> ContractStatus.Cancelled
}

internal fun PlannedPhaseDto.toDomain(): PlannedPhase = when (this) {
    PlannedPhaseDto.Upcoming -> PlannedPhase.Upcoming
    PlannedPhaseDto.Current -> PlannedPhase.Current
    PlannedPhaseDto.Past -> PlannedPhase.Past
    PlannedPhaseDto.Cancelled -> PlannedPhase.Cancelled
}
