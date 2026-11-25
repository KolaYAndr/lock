package org.homesharing.lockbox.domain.model.flat_detail

import org.homesharing.lockbox.domain.model.premise_type.PremiseType
import org.homesharing.lockbox.domain.model.room.RoomType

data class FlatDetail(
    val id: String,
    val name: String,
    val description: String?,
    val premiseType: PremiseType,
    val address: Address,
    val fullAddress: String,
    val livingArea: Double?,
    val rooms: List<Room>,
    val status: FlatStatus,
    val photos: List<Photo>,
    val invitesCount: Int,
    val activeInvitesCount: Int,
    val hasDocuments: Boolean,
    val invites: List<InviteListItem>?,
) {
    data class Address(
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

    data class Room(
        val id: String,
        val type: RoomType,
        val position: Int,
        val name: String?,
    )

    data class Photo(
        val id: String,
        val url: String,
        val position: Int,
        val fileName: String?,
    )

    data class InviteListItem(
        val id: String,
        val guestName: String,
        val guestEmail: String,
        val startDate: String,
        val endDate: String,
        val status: String,
        val emailStatus: String,
        val flatId: String?,
        val flatName: String?,
        val flatAddress: String?,
    )
}
