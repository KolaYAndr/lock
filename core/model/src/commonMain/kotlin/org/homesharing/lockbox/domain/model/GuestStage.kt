package org.homesharing.lockbox.domain.model

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
