//package org.homesharing.lockbox.presentation.contracts.viewmodel
//
//import androidx.lifecycle.ViewModel
//import androidx.paging.PagingData
//import kotlinx.coroutines.flow.Flow
//import kotlinx.coroutines.flow.SharedFlow
//import org.homesharing.lockbox.domain.model.contract.Contract
//
//internal abstract class ContractsViewModel : ViewModel() {
//    abstract val deleteSuccess: SharedFlow<Unit>
//    abstract val contracts: Flow<PagingData<Contract>>
//    abstract fun deleteContract(id: String)
//}
