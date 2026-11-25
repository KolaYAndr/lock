package org.homesharing.lockbox.data.network.dto.invitation

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GuestDto(
    val email: String,
    val name: String,
)

@Serializable
data class CheckInDetailsDto(
    @SerialName("end_date")
    val endDate: String,
    @SerialName("number_of_guests")
    val numberOfGuests: Int,
    @SerialName("start_date")
    val startDate: String,
    @SerialName("check_in_time")
    val checkInTime: String? = null,
    @SerialName("check_out_time")
    val checkOutTime: String? = null,
    val comment: String? = null,
    @SerialName("day_price")
    val dayPrice: Int? = null,
    val deposit: Int? = null,
    @SerialName("total_price")
    val totalPrice: Int? = null,
)

@Serializable
data class CheckInSettingsDto(
    val comment: String? = null,
    @SerialName("exit_report")
    val exitReport: Boolean? = null,
    @SerialName("intercom_code")
    val intercomCode: String? = null,
    @SerialName("request_passport")
    val requestPassport: Boolean? = null,
    @SerialName("room_report")
    val roomReport: Boolean? = null,
    @SerialName("sign_contract_online")
    val signContractOnline: Boolean? = null,
)

@Serializable
internal data class InviteDraftDto(
    @SerialName("check_in")
    val checkIn: CheckInDetailsDto,
    val guest: GuestDto,
    @SerialName("flat_id")
    val flatId: String? = null,
    val settings: CheckInSettingsDto? = null,
)

@Serializable
internal data class InviteDto(
    @SerialName("check_in")
    val checkIn: CheckInDetailsDto,
    @SerialName("email_status")
    val emailStatus: String,
    @SerialName("expires_at")
    val expiresAt: String,
    val guest: GuestDto,
    val id: String,
    @SerialName("invite_url")
    val inviteUrl: String,
    val settings: CheckInSettingsDto,
    val status: String,
    @SerialName("accepted_at")
    val acceptedAt: String? = null,
    @SerialName("booking_id")
    val bookingId: String? = null,
    @SerialName("declined_at")
    val declinedAt: String? = null,
    @SerialName("flat_id")
    val flatId: String? = null,
)

@Serializable
internal data class InviteSummaryDto(
    @SerialName("email_status")
    val emailStatus: String,
    @SerialName("end_date")
    val endDate: String,
    @SerialName("guest_email")
    val guestEmail: String,
    @SerialName("guest_name")
    val guestName: String,
    val id: String,
    @SerialName("start_date")
    val startDate: String,
    val status: String,
)

@Serializable
internal data class InvitePageDto(
    val items: List<InviteSummaryDto>,
    @SerialName("next_cursor")
    val nextCursor: String? = null,
)

@Serializable
data class GuestInvitePhotoDto(
    val id: String,
    val position: Int,
    val url: String,
    @SerialName("file_name")
    val fileName: String? = null,
)

@Serializable
data class GuestInviteDto(
    @SerialName("check_in")
    val checkIn: CheckInDetailsDto,
    @SerialName("expires_at")
    val expiresAt: String,
    val guest: GuestDto,
    val photos: List<GuestInvitePhotoDto>,
    val settings: CheckInSettingsDto,
    val status: String,
    @SerialName("flat_address")
    val flatAddress: String? = null,
    @SerialName("flat_name")
    val flatName: String? = null,
    @SerialName("landlord_name")
    val landlordName: String? = null,
)

@Serializable
internal data class InviteAcceptanceDto(
    @SerialName("booking_id")
    val bookingId: String,
    @SerialName("contract_id")
    val contractId: String,
    val invite: GuestInviteDto,
)
