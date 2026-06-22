package org.homesharing.lockbox.presentation.flat_detailed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import org.homesharing.lockbox.domain.api.response.Response
import org.homesharing.lockbox.domain.model.premise_type.PremiseType
import org.homesharing.lockbox.domain.repository.FlatRepository

private const val SUBSCRIPTION_THRESHOLD = 5000L

internal class FlatDetailedViewModel(
    private val id: String,
    private val flatRepository: FlatRepository,
) : ViewModel() {
    val flat = flow {
        val state = when (val response = flatRepository.getFlat(id)) {
            is Response.Failure -> {
                FlatDetailedUiState(
                    name = "",
                    address = "",
                    premiseType = PremiseType.RESIDENTIAL,
                    loading = false,
                    error = true,
                )
            }

            is Response.Success -> {
                FlatDetailedUiState(
                    name = response.data.name,
                    address = response.data.address,
                    premiseType = response.data.premiseType,
                    loading = false,
                    error = false,
                )
            }
        }
        emit(state)
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(SUBSCRIPTION_THRESHOLD),
        FlatDetailedUiState.Initial
    )
}

internal data class FlatDetailedUiState(
    val name: String,
    val address: String,
    val premiseType: PremiseType,
    val loading: Boolean,
    val error: Boolean,
) {
    companion object {
        val Initial = FlatDetailedUiState(
            name = "",
            address = "",
            premiseType = PremiseType.RESIDENTIAL,
            loading = true,
            error = false,
        )
    }
}