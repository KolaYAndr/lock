package org.homesharing.lockbox.presentation_v2.flats_flow.add_or_import_flat

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.homesharing.lockbox.domain.model.add_flat.AddFlatType

internal class AddFlatViewModel: ViewModel() {
    private val _addType = MutableStateFlow<AddFlatType?>(null)
    val addType = _addType.asStateFlow()

    fun changeFlatType(newType: AddFlatType) {
        _addType.update { newType }
    }
}
