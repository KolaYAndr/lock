package org.homesharing.lockbox.domain.model.flat_rooms

sealed interface FlatRoom {
    data object Kitchen: FlatRoom
    data object Bathroom: FlatRoom
    data object Bedroom: FlatRoom
    data object LivingRoom: FlatRoom
    data object Toilet: FlatRoom
    data object Shower: FlatRoom

    companion object {
        val entries = listOf(
            Kitchen,
            Bathroom,
            Bedroom,
            LivingRoom,
            Toilet,
            Shower,
        )
    }
}