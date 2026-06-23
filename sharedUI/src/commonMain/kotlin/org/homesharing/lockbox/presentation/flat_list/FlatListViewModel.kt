package org.homesharing.lockbox.presentation.flat_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import org.homesharing.lockbox.domain.repository.FlatRepository

internal class FlatListViewModel(
    flatRepository: FlatRepository,
): ViewModel() {
    val flats = flatRepository.getFlatsFlow().cachedIn(viewModelScope)
}