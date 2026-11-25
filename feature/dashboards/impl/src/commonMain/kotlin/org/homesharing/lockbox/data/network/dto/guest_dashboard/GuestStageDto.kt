package org.homesharing.lockbox.data.network.dto.guest_dashboard

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal enum class GuestStageDto {
    @SerialName("no_invite")
    NoInvite,

    @SerialName("invite_pending")
    InvitePending,

    @SerialName("identity_pending")
    IdentityPending,

    @SerialName("contract_pending")
    ContractPending,

    @SerialName("ready")
    Ready,

    @SerialName("living")
    Living,

    @SerialName("completed")
    Completed,

    @SerialName("declined")
    Declined,

    @SerialName("cancelled")
    Cancelled,

    @SerialName("rejected")
    Rejected,
}
