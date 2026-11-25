package org.homesharing.lockbox.data.network.dto.guest_dashboard

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal enum class GuestNextActionDto {
    @SerialName("none")
    None,

    @SerialName("answer_invite")
    AnswerInvite,

    @SerialName("complete_identity")
    CompleteIdentity,

    @SerialName("wait_for_document")
    WaitForDocument,

    @SerialName("confirm_contract")
    ConfirmContract,

    @SerialName("await_other_party")
    AwaitOtherParty,
}
