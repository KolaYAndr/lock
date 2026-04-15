package org.homesharing.lockbox.presentation.lockboxes.viewmodel

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import org.homesharing.lockbox.domain.repository.BoxesRepository

internal class LockboxesViewModelImpl(
    boxesRepository: BoxesRepository,
) : LockboxesViewModel() {
    override val boxes = boxesRepository.getBoxesPagingFlow().cachedIn(viewModelScope)
}
