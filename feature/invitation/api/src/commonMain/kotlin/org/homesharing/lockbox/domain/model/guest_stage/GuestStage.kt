package org.homesharing.lockbox.domain.model.guest_stage

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
