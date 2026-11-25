package org.homesharing.lockbox.domain.model.guest_stage

import org.homesharing.lockbox.data.network.dto.guest_stage.GuestStageDto
import org.homesharing.lockbox.domain.model.GuestStage

internal fun GuestStageDto.toDomain(): GuestStage = when (this) {
    GuestStageDto.NoInvite -> GuestStage.NoInvite
    GuestStageDto.InvitePending -> GuestStage.InvitePending
    GuestStageDto.IdentityPending -> GuestStage.IdentityPending
    GuestStageDto.ContractPending -> GuestStage.ContractPending
    GuestStageDto.Ready -> GuestStage.Ready
    GuestStageDto.Living -> GuestStage.Living
    GuestStageDto.Completed -> GuestStage.Completed
    GuestStageDto.Declined -> GuestStage.Declined
    GuestStageDto.Cancelled -> GuestStage.Cancelled
    GuestStageDto.Rejected -> GuestStage.Rejected
}
