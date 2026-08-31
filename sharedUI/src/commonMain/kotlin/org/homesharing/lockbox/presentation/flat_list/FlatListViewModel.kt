package org.homesharing.lockbox.presentation.flat_list

import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import androidx.paging.filter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import org.homesharing.lockbox.domain.model.flat.Flat
import org.homesharing.lockbox.domain.model.premise_type.PremiseType
import org.homesharing.lockbox.domain.repository.FlatRepository

internal class FlatListViewModel(
    flatRepository: FlatRepository,
) : ViewModel() {
    private val rawFlats = flatRepository.getFlatsFlow()

    private val _type = MutableStateFlow<FlatTypeFilter>(FlatTypeFilter.All)
    val type = _type.asStateFlow()

    val flats: Flow<PagingData<Flat>> =
        combine(rawFlats, type) { data, flatType ->
            data.filter { flat ->
                val matchType = when (flatType) {
                    FlatTypeFilter.Residental -> flat.premiseType == PremiseType.RESIDENTIAL
                    FlatTypeFilter.NonResidental -> flat.premiseType == PremiseType.NON_RESIDENTIAL
                    FlatTypeFilter.All -> true
                }

                matchType
            }
        }
    fun onChangeFilter(newFilter: FlatTypeFilter) = _type.update { newFilter }
}

internal enum class FlatTypeFilter {
    All,
    Residental,
    NonResidental,
}