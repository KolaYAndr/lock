package org.homesharing.lockbox.domain.model.invite

import org.homesharing.lockbox.domain.model.InviteStatus

data class Invite(
    val id: String,
    val status: InviteStatus,
    val emailStatus: InviteEmailStatus,
    val termsVersion: Long,
    val guest: Guest,
    val checkIn: CheckInDetails,
    val settings: CheckInSettings,
    val flatId: String?,
    val flatName: String?,
    val flatAddress: String?,
    val availableActions: List<InviteAvailableAction>,
    val events: List<InviteEvent>,
    val inviteUrl: String,
    val expiresAt: String,
    val openedAt: String?,
    val acceptedAt: String?,
    val declinedAt: String?,
    val bookingId: String?,
) {
    data class Guest(
        val email: String,
        val name: String,
    )

    data class CheckInDetails(
        val startDate: String,
        val endDate: String,
        val numberOfGuests: Int,
        val checkInTime: String?,
        val checkOutTime: String?,
        val prepayment: Int?,
        val deposit: Int?,
        val dayPrice: Int?,
        val totalPrice: Int?,
        val remainingPrice: Int?,
        val comment: String?,
    )

    data class CheckInSettings(
        val requestPassport: Boolean?,
        val signContractOnline: Boolean?,
        val roomReport: Boolean?,
        val exitReport: Boolean?,
        val intercomCode: String?,
        val comment: String?,
    )

    data class InviteEvent(
        val id: Long,
        val type: InviteEventType,
        val actorType: InviteEventActorType,
        val actorId: String?,
        val termsVersion: Long,
        val changedFields: Map<String, ChangedField>,
        val createdAt: String,
    ) {
        data class ChangedField(
            val old: String?,
            val new: String?,
        )
    }
}
