package org.homesharing.lockbox.domain.model.guest_dashboard

enum class GuestStage {
    NoInvite,
    InvitePending,
    IdentityPending,
    ContractPending,
    Ready,
    Living,
    Completed,
    Declined,
    Cancelled,
    Rejected,
}
