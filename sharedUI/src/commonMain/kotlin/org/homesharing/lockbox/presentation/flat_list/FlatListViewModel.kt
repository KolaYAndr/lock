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

    private val _search = MutableStateFlow("")
    val search = _search.asStateFlow()

    private val _type = MutableStateFlow<FlatTypeFilter>(FlatTypeFilter.All)
    val type = _type.asStateFlow()

    val flats: Flow<PagingData<Flat>> =
        combine(rawFlats, type, search) { data, flatType, searchString ->
            data.filter { flat ->
                val matchType = when (flatType) {
                    FlatTypeFilter.Residental -> flat.premiseType == PremiseType.RESIDENTIAL
                    FlatTypeFilter.NonResidental -> flat.premiseType == PremiseType.NON_RESIDENTIAL
                    FlatTypeFilter.All -> true
                }

                val matchSearch = if (searchString.isNotBlank()) {
                    flat.name.contains(searchString, ignoreCase = true) ||
                            flat.address.contains(searchString, ignoreCase = true)
                } else true

                matchType && matchSearch
            }
        }

    fun onSearchChanged(newSearch: String) = _search.update { newSearch }
    fun onChangeFilter(newFilter: FlatTypeFilter) = _type.update { newFilter }
}

internal sealed interface FlatTypeFilter {
    data object Residental : FlatTypeFilter
    data object NonResidental : FlatTypeFilter
    data object All : FlatTypeFilter

    companion object {
        val entries = listOf(
            All,
            Residental,
            NonResidental,
        )
    }
}