package org.homesharing.lockbox.data.network.dto.create_flat

import kotlinx.serialization.Serializable
import org.homesharing.lockbox.data.network.dto.premise_type.PremiseTypeDto
import org.homesharing.lockbox.data.network.dto.room.RoomDto

@Serializable
internal data class CreateFlatRequestDto(
    val name: String,
    val premiseType: PremiseTypeDto,
    val address: AddressRequestDto,
    val livingArea: Double?,
    val rooms: List<RoomDto>
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
}
