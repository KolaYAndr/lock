package org.homesharing.lockbox.presentation.add_flat_flow.flat_data

import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.homesharing.lockbox.domain.model.flat_rooms.FlatRoom

internal class FlatDataViewModel: ViewModel() {
    private val _flatData = MutableStateFlow(FlatDataUiState.Initial)
    val flatData = _flatData.asStateFlow()
}

@Immutable
internal data class FlatDataUiState(
    val flatAddress: String,
    val commercial: Boolean,
    val chosenRooms: List<FlatRoom>,
) {
    companion object {
        val Initial = FlatDataUiState(
            flatAddress = "",
            commercial = false,
            chosenRooms = FlatRoom.entries
        )
    }
}