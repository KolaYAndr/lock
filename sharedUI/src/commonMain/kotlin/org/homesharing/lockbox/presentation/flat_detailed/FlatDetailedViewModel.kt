package org.homesharing.lockbox.presentation.flat_detailed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.homesharing.lockbox.domain.api.response.Response
import org.homesharing.lockbox.domain.model.premise_type.PremiseType
import org.homesharing.lockbox.domain.repository.FlatRepository

private const val SUBSCRIPTION_THRESHOLD = 5000L

internal class FlatDetailedViewModel(
    private val id: String,
    private val flatRepository: FlatRepository,
) : ViewModel() {
    val flat = flow {
        val state = loadState()
        emit(state)

        refreshListener.collect {
            emit(FlatDetailedUiState.Initial)
            emit(loadState())
        }
    }
        .distinctUntilChanged()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(SUBSCRIPTION_THRESHOLD),
            FlatDetailedUiState.Initial
        )

    private suspend fun loadState(): FlatDetailedUiState =
        when (val response = flatRepository.getFlat(id = id, includeInvites = false)) {
            is Response.Failure -> {
                FlatDetailedUiState.Failed
            }

            is Response.Success -> {
                FlatDetailedUiState(
                    name = response.data.name,
                    address = response.data.fullAddress,
                    premiseType = response.data.premiseType,
                    loading = false,
                    error = false,
                )
            }
        }

    private val refreshListener = MutableSharedFlow<Unit>()

    fun onIntent(intent: FlatDetailedIntent) {
        when (intent) {
            FlatDetailedIntent.Refresh -> {
                viewModelScope.launch {
                    refreshListener.emit(Unit)
                }
            }
        }
    }
}

internal sealed interface FlatDetailedIntent {
    object Refresh : FlatDetailedIntent
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

        val Failed = FlatDetailedUiState(
            name = "",
            address = "",
            premiseType = PremiseType.RESIDENTIAL,
            loading = false,
            error = true,
        )
    }
}