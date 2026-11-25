package org.homesharing.lockbox.domain.model.invitation

data class Guest(
    val email: String,
    val name: String,
)

data class CheckInDetails(
    val endDate: String,
    val numberOfGuests: Int,
    val startDate: String,
    val checkInTime: String? = null,
    val checkOutTime: String? = null,
    val comment: String? = null,
    val dayPrice: Int? = null,
    val deposit: Int? = null,
    val totalPrice: Int? = null,
)

data class CheckInSettings(
    val comment: String? = null,
    val exitReport: Boolean? = null,
    val intercomCode: String? = null,
    val requestPassport: Boolean? = null,
    val roomReport: Boolean? = null,
    val signContractOnline: Boolean? = null,
)

data class InviteDraft(
    val checkIn: CheckInDetails,
    val guest: Guest,
    val flatId: String? = null,
    val settings: CheckInSettings? = null,
)

data class Invite(
    val checkIn: CheckInDetails,
    val emailStatus: String,
    val expiresAt: String,
    val guest: Guest,
    val id: String,
    val inviteUrl: String,
    val settings: CheckInSettings,
    val status: String,
    val acceptedAt: String? = null,
    val bookingId: String? = null,
    val declinedAt: String? = null,
    val flatId: String? = null,
)

data class InviteSummary(
    val emailStatus: String,
    val endDate: String,
    val guestEmail: String,
    val guestName: String,
    val id: String,
    val startDate: String,
    val status: String,
)

data class InvitePage(
    val items: List<InviteSummary>,
    val nextCursor: String? = null,
)

data class GuestInvitePhoto(
    val id: String,
    val position: Int,
    val url: String,
    val fileName: String? = null,
)

data class GuestInvite(
    val checkIn: CheckInDetails,
    val expiresAt: String,
    val guest: Guest,
    val photos: List<GuestInvitePhoto>,
    val settings: CheckInSettings,
    val status: String,
    val flatAddress: String? = null,
    val flatName: String? = null,
    val landlordName: String? = null,
)

data class InviteAcceptance(
    val bookingId: String,
    val contractId: String,
    val invite: GuestInvite,
)
