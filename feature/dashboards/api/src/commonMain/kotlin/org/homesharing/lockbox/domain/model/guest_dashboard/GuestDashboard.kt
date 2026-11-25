package org.homesharing.lockbox.domain.model.guest_dashboard

import org.homesharing.lockbox.domain.model.ContractStatus
import org.homesharing.lockbox.domain.model.DocumentStatus
import org.homesharing.lockbox.domain.model.GuestStage
import org.homesharing.lockbox.domain.model.InviteStatus
import org.homesharing.lockbox.domain.model.stay.StayListItem

data class GuestDashboard(
    val stage: GuestStage,
    val nextAction: GuestNextAction,
    val selectedInviteId: String?,
    val agreementStatus: ContractStatus?,
    val documentStatus: DocumentStatus?,
    val contractStatus: DocumentStatus?,
    val currentVersionId: String?,
    val invite: GuestInvite?,
    val inviteToken: String?,
    val stay: StayListItem?,
    val contractUrl: String?,
) {
    data class GuestInvite(
        val termsVersion: Long,
        val hasDocuments: Boolean,
        val guest: Guest,
        val checkIn: CheckInDetails,
        val settings: CheckInSettings,
        val status: InviteStatus,
        val expiresAt: String,
        val flatName: String?,
        val flatAddress: String?,
        val landlordName: String?,
        val photos: List<FlatPhoto>,
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

        data class FlatPhoto(
            val id: String,
            val url: String,
            val position: Int,
            val fileName: String?,
        )
    }
}
