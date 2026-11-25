package org.homesharing.lockbox.presentation.add_flat_flow.flat_rooms

import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.homesharing.lockbox.domain.model.room.RoomType

internal class FlatRoomsViewModel : ViewModel() {
    private val _chosenRooms = MutableStateFlow(FlatRoomsUiState.Initial)
    val uiState = _chosenRooms.asStateFlow()

    fun toggleRoom(room: RoomType) {
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
    val rooms: List<RoomType>,
    val chosenRooms: List<RoomType>,
) {
    companion object {
        val Initial = FlatRoomsUiState(
            rooms = RoomType.entries,
            chosenRooms = emptyList(),
        )
    }
}