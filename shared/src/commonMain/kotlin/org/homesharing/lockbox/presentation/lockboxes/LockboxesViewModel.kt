package org.homesharing.lockbox.presentation.lockboxes

import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import org.homesharing.lockbox.domain.model.box.BoxWithStatus

abstract class LockboxesViewModel : ViewModel() {
    abstract val avatarLink: StateFlow<String>
    abstract val boxes: Flow<PagingData<BoxWithStatus>>
}
