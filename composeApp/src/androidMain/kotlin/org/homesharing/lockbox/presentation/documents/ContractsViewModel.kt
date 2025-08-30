package org.homesharing.lockbox.presentation.documents

import androidx.lifecycle.ViewModel
import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.PagingData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.update
import kotlinx.datetime.LocalDateTime
import org.homesharing.lockbox.domain.model.contract.Contract

internal class ContractsViewModel : ViewModel() {
    private val _avatarLink: MutableStateFlow<String> = MutableStateFlow("")
    val avatarLink: StateFlow<String> = _avatarLink.asStateFlow()

    private val _contractSelected: MutableStateFlow<ContractSelected?> = MutableStateFlow(null)
    val contractSelected = _contractSelected.asStateFlow()

    val contracts =
        flowOf(
            PagingData.from(
                listOf(
                    Contract(
                        "0",
                        "New",
                        LocalDateTime(2025, 5, 5, 5, 5, 5, 5),
                        ""
                    )
                ),
                LoadStates(LoadState.NotLoading(true), LoadState.NotLoading(true), LoadState.NotLoading(true))
            )
        )

    fun updateContractSelected(state: ContractSelected?) {
        _contractSelected.update { state }
    }
}

internal sealed interface ContractSelected {
    data class CreateNew(val name: String = ""): ContractSelected
    data class Existing(val contract: Contract): ContractSelected
}
