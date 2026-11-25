package org.homesharing.lockbox.data.network.dto.flat_detail

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.homesharing.lockbox.data.network.dto.premise_type.PremiseTypeDto
import org.homesharing.lockbox.data.network.dto.room.RoomTypeDto

@Serializable
internal data class FlatDetailDto(
    val id: String,
    val name: String,
    val description: String?,
    @SerialName("premise_type")
    val premiseType: PremiseTypeDto,
    val address: AddressDto,
    @SerialName("full_address")
    val fullAddress: String,
    @SerialName("living_area")
    val livingArea: Double?,
    val rooms: List<RoomDto>,
    val status: FlatStatusDto,
    val photos: List<PhotoDto>,
    @SerialName("invites_count")
    val invitesCount: Int,
    @SerialName("active_invites_count")
    val activeInvitesCount: Int,
    @SerialName("has_documents")
    val hasDocuments: Boolean,
    val invites: List<InviteListItemDto>? = null,
) {
    @Serializable
    internal data class AddressDto(
        val city: String,
        val street: String?,
        val house: String?,
        val entrance: String?,
        val apartment: String?,
        val block: String?,
        val building: String?,
        val litera: String?,
        val comment: String?,
    )

    @Serializable
    internal data class RoomDto(
        val id: String,
        val type: RoomTypeDto,
        val position: Int,
        val name: String?,
    )

    @Serializable
    internal data class PhotoDto(
        val id: String,
        val url: String,
        val position: Int,
        @SerialName("file_name")
        val fileName: String?,
    )

    @Serializable
    internal data class InviteListItemDto(
        val id: String,
        @SerialName("guest_name")
        val guestName: String,
        @SerialName("guest_email")
        val guestEmail: String,
        @SerialName("start_date")
        val startDate: String,
        @SerialName("end_date")
        val endDate: String,
        val status: String,
        @SerialName("email_status")
        val emailStatus: String,
        @SerialName("flat_id")
        val flatId: String?,
        @SerialName("flat_name")
        val flatName: String?,
        @SerialName("flat_address")
        val flatAddress: String?,
    )
}
