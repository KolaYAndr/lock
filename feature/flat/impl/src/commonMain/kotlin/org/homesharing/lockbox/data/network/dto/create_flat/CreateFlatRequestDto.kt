package org.homesharing.lockbox.data.network.dto.create_flat

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.homesharing.lockbox.data.network.dto.premise_type.PremiseTypeDto
import org.homesharing.lockbox.data.network.dto.room.RoomTypeDto

@Serializable
internal data class CreateFlatRequestDto(
    val address: AddressRequestDto,
    val name: String?,
    val description: String?,
    @SerialName("premise_type")
    val premiseType: PremiseTypeDto?,
    @SerialName("living_area")
    val livingArea: Double?,
    val rooms: List<RoomInputDto>?,
) {
    @Serializable
    internal data class AddressRequestDto(
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
    internal data class RoomInputDto(
        val type: RoomTypeDto,
        val name: String?,
        val position: Int?,
    )
}
