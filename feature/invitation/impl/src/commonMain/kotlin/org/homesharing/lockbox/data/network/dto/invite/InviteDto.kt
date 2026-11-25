package org.homesharing.lockbox.data.network.dto.invite

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
internal data class InviteDto(
    val id: String,
    val status: InviteStatusDto,
    @SerialName("email_status")
    val emailStatus: InviteEmailStatusDto,
    @SerialName("terms_version")
    val termsVersion: Long,
    val guest: GuestDto,
    @SerialName("check_in")
    val checkIn: CheckInDetailsDto,
    val settings: CheckInSettingsDto,
    @SerialName("flat_id")
    val flatId: String?,
    @SerialName("flat_name")
    val flatName: String?,
    @SerialName("flat_address")
    val flatAddress: String?,
    @SerialName("available_actions")
    val availableActions: List<InviteAvailableActionDto>,
    val events: List<InviteEventDto>,
    @SerialName("invite_url")
    val inviteUrl: String,
    @SerialName("expires_at")
    val expiresAt: String,
    @SerialName("opened_at")
    val openedAt: String?,
    @SerialName("accepted_at")
    val acceptedAt: String?,
    @SerialName("declined_at")
    val declinedAt: String?,
    @SerialName("booking_id")
    val bookingId: String?,
) {
    @Serializable
    internal data class GuestDto(
        val email: String,
        val name: String,
    )

    @Serializable
    internal data class CheckInDetailsDto(
        @SerialName("start_date")
        val startDate: String,
        @SerialName("end_date")
        val endDate: String,
        @SerialName("number_of_guests")
        val numberOfGuests: Int,
        @SerialName("check_in_time")
        val checkInTime: String?,
        @SerialName("check_out_time")
        val checkOutTime: String?,
        val prepayment: Int?,
        val deposit: Int?,
        @SerialName("day_price")
        val dayPrice: Int?,
        @SerialName("total_price")
        val totalPrice: Int?,
        @SerialName("remaining_price")
        val remainingPrice: Int?,
        val comment: String?,
    )

    @Serializable
    internal data class CheckInSettingsDto(
        @SerialName("request_passport")
        val requestPassport: Boolean?,
        @SerialName("sign_contract_online")
        val signContractOnline: Boolean?,
        @SerialName("room_report")
        val roomReport: Boolean?,
        @SerialName("exit_report")
        val exitReport: Boolean?,
        @SerialName("intercom_code")
        val intercomCode: String?,
        val comment: String?,
    )

    @Serializable
    internal data class InviteEventDto(
        val id: Long,
        val type: InviteEventTypeDto,
        @SerialName("actor_type")
        val actorType: InviteEventActorTypeDto,
        @SerialName("actor_id")
        val actorId: String?,
        @SerialName("terms_version")
        val termsVersion: Long,
        @SerialName("changed_fields")
        val changedFields: Map<String, ChangedFieldDto>,
        @SerialName("created_at")
        val createdAt: String,
    ) {
        @Serializable
        internal data class ChangedFieldDto(
            val old: JsonElement?,
            val new: JsonElement?,
        )
    }
}
