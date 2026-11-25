package org.homesharing.lockbox.domain.model.guest_dashboard

import org.homesharing.lockbox.data.network.dto.guest_dashboard.GuestDashboardDto
import org.homesharing.lockbox.data.network.dto.guest_dashboard.GuestNextActionDto
import org.homesharing.lockbox.data.network.dto.guest_dashboard.GuestStageDto
import org.homesharing.lockbox.data.network.dto.guest_dashboard.InviteStatusDto
import org.homesharing.lockbox.domain.model.stay.toDomain

internal fun GuestDashboardDto.toDomain() = GuestDashboard(
    stage = stage.toDomain(),
    nextAction = nextAction.toDomain(),
    selectedInviteId = selectedInviteId,
    agreementStatus = agreementStatus?.toDomain(),
    documentStatus = documentStatus?.toDomain(),
    contractStatus = contractStatus?.toDomain(),
    currentVersionId = currentVersionId,
    invite = invite?.toDomain(),
    inviteToken = inviteToken,
    stay = stay?.toDomain(),
    contractUrl = contractUrl,
)

internal fun GuestDashboardDto.GuestInviteDto.toDomain() = GuestDashboard.GuestInvite(
    termsVersion = termsVersion,
    hasDocuments = hasDocuments,
    guest = guest.toDomain(),
    checkIn = checkIn.toDomain(),
    settings = settings.toDomain(),
    status = status.toDomain(),
    expiresAt = expiresAt,
    flatName = flatName,
    flatAddress = flatAddress,
    landlordName = landlordName,
    photos = photos.map { it.toDomain() },
)

internal fun GuestDashboardDto.GuestInviteDto.GuestDto.toDomain() = GuestDashboard.GuestInvite.Guest(
    email = email,
    name = name,
)

internal fun GuestDashboardDto.GuestInviteDto.CheckInDetailsDto.toDomain() =
    GuestDashboard.GuestInvite.CheckInDetails(
        startDate = startDate,
        endDate = endDate,
        numberOfGuests = numberOfGuests,
        checkInTime = checkInTime,
        checkOutTime = checkOutTime,
        prepayment = prepayment,
        deposit = deposit,
        dayPrice = dayPrice,
        totalPrice = totalPrice,
        remainingPrice = remainingPrice,
        comment = comment,
    )

internal fun GuestDashboardDto.GuestInviteDto.CheckInSettingsDto.toDomain() =
    GuestDashboard.GuestInvite.CheckInSettings(
        requestPassport = requestPassport,
        signContractOnline = signContractOnline,
        roomReport = roomReport,
        exitReport = exitReport,
        intercomCode = intercomCode,
        comment = comment,
    )

internal fun GuestDashboardDto.GuestInviteDto.FlatPhotoDto.toDomain() =
    GuestDashboard.GuestInvite.FlatPhoto(
        id = id,
        url = url,
        position = position,
        fileName = fileName,
    )

internal fun GuestStageDto.toDomain(): GuestStage = when (this) {
    GuestStageDto.NoInvite -> GuestStage.NoInvite
    GuestStageDto.InvitePending -> GuestStage.InvitePending
    GuestStageDto.IdentityPending -> GuestStage.IdentityPending
    GuestStageDto.ContractPending -> GuestStage.ContractPending
    GuestStageDto.Ready -> GuestStage.Ready
    GuestStageDto.Living -> GuestStage.Living
    GuestStageDto.Completed -> GuestStage.Completed
    GuestStageDto.Declined -> GuestStage.Declined
    GuestStageDto.Cancelled -> GuestStage.Cancelled
    GuestStageDto.Rejected -> GuestStage.Rejected
}

internal fun GuestNextActionDto.toDomain(): GuestNextAction = when (this) {
    GuestNextActionDto.None -> GuestNextAction.None
    GuestNextActionDto.AnswerInvite -> GuestNextAction.AnswerInvite
    GuestNextActionDto.CompleteIdentity -> GuestNextAction.CompleteIdentity
    GuestNextActionDto.WaitForDocument -> GuestNextAction.WaitForDocument
    GuestNextActionDto.ConfirmContract -> GuestNextAction.ConfirmContract
    GuestNextActionDto.AwaitOtherParty -> GuestNextAction.AwaitOtherParty
}

internal fun InviteStatusDto.toDomain(): InviteStatus = when (this) {
    InviteStatusDto.Sent -> InviteStatus.Sent
    InviteStatusDto.Opened -> InviteStatus.Opened
    InviteStatusDto.Accepted -> InviteStatus.Accepted
    InviteStatusDto.Declined -> InviteStatus.Declined
    InviteStatusDto.Revoked -> InviteStatus.Revoked
    InviteStatusDto.Expired -> InviteStatus.Expired
}
