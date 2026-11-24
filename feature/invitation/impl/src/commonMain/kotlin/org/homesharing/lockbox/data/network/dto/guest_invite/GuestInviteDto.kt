package org.homesharing.lockbox.data.network.dto.guest_invite

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.homesharing.lockbox.data.network.dto.invite.InviteDto
import org.homesharing.lockbox.data.network.dto.invite.InviteStatusDto

@Serializable
internal data class GuestInviteDto(
    @SerialName("terms_version")
    val termsVersion: Long,
    val status: InviteStatusDto,
    val guest: InviteDto.GuestDto,
    @SerialName("check_in")
    val checkIn: InviteDto.CheckInDetailsDto,
    val settings: InviteDto.CheckInSettingsDto,
    @SerialName("has_documents")
    val hasDocuments: Boolean,
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
    internal data class FlatPhotoDto(
        val id: String,
        val url: String,
        val position: Int,
        @SerialName("file_name")
        val fileName: String?,
    )
}
