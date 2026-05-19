package org.homesharing.lockbox.presentation.flats_flow.flat_rooms

import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.homesharing.lockbox.domain.model.flat_rooms.FlatRoom

internal class FlatRoomsViewModel : ViewModel() {
    private val _chosenRooms = MutableStateFlow(FlatRoomsUiState.Initial)
    val uiState = _chosenRooms.asStateFlow()

    fun toggleRoom(room: FlatRoom) {
        _chosenRooms.update { state ->
            val chosen = state.chosenRooms
            state.copy(
                chosenRooms = if (room in chosen) chosen - room else chosen + room
            )
        }
    }
}

@Immutable
internal data class FlatRoomsUiState(
    val rooms: List<FlatRoom>,
    val chosenRooms: List<FlatRoom>,
) {
    companion object {
        val Initial = FlatRoomsUiState(
            rooms = FlatRoom.entries,
            chosenRooms = emptyList(),
        )
    }
}