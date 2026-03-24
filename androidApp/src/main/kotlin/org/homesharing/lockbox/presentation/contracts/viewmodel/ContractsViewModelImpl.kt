package org.homesharing.lockbox.presentation.contracts.viewmodel

import androidx.lifecycle.viewModelScope
import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.flow.flowOf
import kotlinx.datetime.LocalDateTime
import org.homesharing.lockbox.domain.model.contract.Contract

internal class ContractsViewModelImpl : ContractsViewModel() {
    override val contracts = flowOf(
        PagingData.from(
            listOf(
                Contract(
                    "0",
                    "New",
                    LocalDateTime(2025, 5, 5, 5, 5, 5, 5),
                    ""
                )
            ),
            LoadStates(
                LoadState.NotLoading(true),
                LoadState.NotLoading(true),
                LoadState.NotLoading(true)
            )
        )
    ).cachedIn(viewModelScope)
}
