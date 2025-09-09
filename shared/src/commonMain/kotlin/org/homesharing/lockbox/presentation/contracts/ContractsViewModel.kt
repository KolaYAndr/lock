package org.homesharing.lockbox.presentation.contracts

import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import org.homesharing.lockbox.domain.model.contract.Contract

abstract class ContractsViewModel : ViewModel() {
    abstract val avatarLink: StateFlow<String>
    abstract val contractSelected: StateFlow<ContractSelected?>
    abstract val contracts: Flow<PagingData<Contract>>
    abstract fun updateContractSelected(selected: ContractSelected?)
}

sealed interface ContractSelected {
    data class CreateNew(val name: String = "") : ContractSelected
    data class Existing(val contract: Contract) : ContractSelected
}
