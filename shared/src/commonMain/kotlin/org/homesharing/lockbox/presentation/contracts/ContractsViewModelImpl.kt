package org.homesharing.lockbox.presentation.contracts

import androidx.lifecycle.viewModelScope
import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.PagingData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDateTime
import org.homesharing.lockbox.domain.api.response.Response
import org.homesharing.lockbox.domain.model.contract.Contract
import org.homesharing.lockbox.domain.repository.AccountRepository
import org.homesharing.lockbox.presentation.snackbar_controller.SnackbarController
import org.homesharing.lockbox.presentation.snackbar_controller.SnackbarDefaultEvents.couldNotLoadAccountInfo
import org.homesharing.lockbox.presentation.snackbar_controller.SnackbarDefaultEvents.couldNotRefreshAccountInfo

internal class ContractsViewModelImpl(
    private val accountRepository: AccountRepository,
) : ContractsViewModel() {
    override val avatarLink = MutableStateFlow("")

    override val contractSelected = MutableStateFlow<ContractSelected?>(null)

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
    )

    init {
        fetchAccountInfo()
    }

    override fun updateContractSelected(selected: ContractSelected?) =
        contractSelected.update { selected }

    private fun fetchAccountInfo() {
        viewModelScope.launch {
            when (val info = accountRepository.parseAccountInfo()) {
                is Response.Success ->
                    avatarLink.update { accountRepository.getImageLink(info.data.sub) }

                is Response.Failure ->
                    SnackbarController.receiveEvent(couldNotLoadAccountInfo { retryLoadingAccountInfo() })
            }
        }
    }

    private suspend fun retryLoadingAccountInfo() {
        when (val info = accountRepository.refreshAccount()) {
            is Response.Success ->
                avatarLink.update { accountRepository.getImageLink(info.data.sub) }

            is Response.Failure -> SnackbarController.receiveEvent(couldNotRefreshAccountInfo { retryLoadingAccountInfo() })
        }
    }
}
