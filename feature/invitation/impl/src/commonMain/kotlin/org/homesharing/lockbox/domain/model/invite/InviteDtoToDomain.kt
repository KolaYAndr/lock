package org.homesharing.lockbox.domain.model.invite

import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonNull
import kotlinx.serialization.json.JsonPrimitive
import org.homesharing.lockbox.data.network.dto.invite.InviteAvailableActionDto
import org.homesharing.lockbox.data.network.dto.invite.InviteDto
import org.homesharing.lockbox.data.network.dto.invite.InviteEmailStatusDto
import org.homesharing.lockbox.data.network.dto.invite.InviteEventActorTypeDto
import org.homesharing.lockbox.data.network.dto.invite.InviteEventTypeDto
import org.homesharing.lockbox.data.network.dto.invite.InviteStatusDto

internal fun InviteDto.toDomain() = Invite(
    id = id,
    status = status.toDomain(),
    emailStatus = emailStatus.toDomain(),
    termsVersion = termsVersion,
    guest = guest.toDomain(),
    checkIn = checkIn.toDomain(),
    settings = settings.toDomain(),
    flatId = flatId,
    flatName = flatName,
    flatAddress = flatAddress,
    availableActions = availableActions.map { it.toDomain() },
    events = events.map { it.toDomain() },
    inviteUrl = inviteUrl,
    expiresAt = expiresAt,
    openedAt = openedAt,
    acceptedAt = acceptedAt,
    declinedAt = declinedAt,
    bookingId = bookingId,
)

internal fun InviteDto.GuestDto.toDomain() = Invite.Guest(
    email = email,
    name = name,
)

internal fun InviteDto.CheckInDetailsDto.toDomain() = Invite.CheckInDetails(
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

internal fun InviteDto.CheckInSettingsDto.toDomain() = Invite.CheckInSettings(
    requestPassport = requestPassport,
    signContractOnline = signContractOnline,
    roomReport = roomReport,
    exitReport = exitReport,
    intercomCode = intercomCode,
    comment = comment,
)

internal fun InviteDto.InviteEventDto.toDomain() = Invite.InviteEvent(
    id = id,
    type = type.toDomain(),
    actorType = actorType.toDomain(),
    actorId = actorId,
    termsVersion = termsVersion,
    changedFields = changedFields.mapValues { (_, value) -> value.toDomain() },
    createdAt = createdAt,
)

internal fun InviteDto.InviteEventDto.ChangedFieldDto.toDomain() =
    Invite.InviteEvent.ChangedField(
        old = old.toFieldValue(),
        new = new.toFieldValue(),
    )

internal fun InviteStatusDto.toDomain(): InviteStatus = when (this) {
    InviteStatusDto.Sent -> InviteStatus.Sent
    InviteStatusDto.Accepted -> InviteStatus.Accepted
    InviteStatusDto.Declined -> InviteStatus.Declined
    InviteStatusDto.Revoked -> InviteStatus.Revoked
    InviteStatusDto.Expired -> InviteStatus.Expired
}

internal fun InviteEmailStatusDto.toDomain(): InviteEmailStatus = when (this) {
    InviteEmailStatusDto.Pending -> InviteEmailStatus.Pending
    InviteEmailStatusDto.Sent -> InviteEmailStatus.Sent
    InviteEmailStatusDto.Failed -> InviteEmailStatus.Failed
}

internal fun InviteAvailableActionDto.toDomain(): InviteAvailableAction = when (this) {
    InviteAvailableActionDto.Update -> InviteAvailableAction.Update
    InviteAvailableActionDto.Resend -> InviteAvailableAction.Resend
    InviteAvailableActionDto.Revoke -> InviteAvailableAction.Revoke
}

internal fun InviteEventTypeDto.toDomain(): InviteEventType = when (this) {
    InviteEventTypeDto.Created -> InviteEventType.Created
    InviteEventTypeDto.Updated -> InviteEventType.Updated
    InviteEventTypeDto.Resent -> InviteEventType.Resent
    InviteEventTypeDto.Opened -> InviteEventType.Opened
    InviteEventTypeDto.Accepted -> InviteEventType.Accepted
    InviteEventTypeDto.Declined -> InviteEventType.Declined
    InviteEventTypeDto.Revoked -> InviteEventType.Revoked
    InviteEventTypeDto.Expired -> InviteEventType.Expired
}

internal fun InviteEventActorTypeDto.toDomain(): InviteEventActorType = when (this) {
    InviteEventActorTypeDto.Landlord -> InviteEventActorType.Landlord
    InviteEventActorTypeDto.Guest -> InviteEventActorType.Guest
    InviteEventActorTypeDto.System -> InviteEventActorType.System
}

private fun JsonElement?.toFieldValue(): String? = when (this) {
    null, JsonNull -> null
    is JsonPrimitive -> content
    else -> toString()
}
