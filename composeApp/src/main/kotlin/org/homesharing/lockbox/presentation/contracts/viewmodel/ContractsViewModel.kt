package org.homesharing.lockbox.presentation.contracts.viewmodel

import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import org.homesharing.lockbox.domain.model.contract.Contract

internal abstract class ContractsViewModel : ViewModel() {
    abstract val contracts: Flow<PagingData<Contract>>
}
