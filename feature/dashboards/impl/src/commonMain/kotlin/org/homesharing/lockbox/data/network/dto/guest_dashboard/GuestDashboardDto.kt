package org.homesharing.lockbox.data.network.dto.guest_dashboard

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.homesharing.lockbox.data.network.dto.stay.ContractStatusDto
import org.homesharing.lockbox.data.network.dto.stay.DocumentStatusDto
import org.homesharing.lockbox.data.network.dto.stay.StayListItemDto

@Serializable
internal data class GuestDashboardDto(
    val stage: GuestStageDto,
    @SerialName("next_action")
    val nextAction: GuestNextActionDto,
    @SerialName("selected_invite_id")
    val selectedInviteId: String?,
    @SerialName("agreement_status")
    val agreementStatus: ContractStatusDto?,
    @SerialName("document_status")
    val documentStatus: DocumentStatusDto?,
    @SerialName("contract_status")
    val contractStatus: DocumentStatusDto?,
    @SerialName("current_version_id")
    val currentVersionId: String?,
    val invite: GuestInviteDto?,
    @SerialName("invite_token")
    val inviteToken: String?,
    val stay: StayListItemDto?,
    @SerialName("contract_url")
    val contractUrl: String?,
) {
    @Serializable
    internal data class GuestInviteDto(
        @SerialName("terms_version")
        val termsVersion: Long,
        @SerialName("has_documents")
        val hasDocuments: Boolean,
        val guest: GuestDto,
        @SerialName("check_in")
        val checkIn: CheckInDetailsDto,
        val settings: CheckInSettingsDto,
        val status: InviteStatusDto,
        @SerialName("expires_at")
        val expiresAt: String,
        @SerialName("flat_name")
        val flatName: String?,
        @SerialName("flat_address")
        val flatAddress: String?,
        @SerialName("landlord_name")
        val landlordName: String?,
        val photos: List<FlatPhotoDto>,
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
        internal data class FlatPhotoDto(
            val id: String,
            val url: String,
            val position: Int,
            @SerialName("file_name")
            val fileName: String?,
        )
    }
}
