//package org.homesharing.lockbox.presentation.contracts.viewmodel
//
//import androidx.lifecycle.viewModelScope
//import androidx.paging.cachedIn
//import kotlinx.coroutines.flow.MutableSharedFlow
//import kotlinx.coroutines.launch
//import org.homesharing.lockbox.domain.api.response.Response
//import org.homesharing.lockbox.domain.repository.DocumentsRepository
//import org.homesharing.lockbox.presentation.snackbar.SnackbarController
//import org.homesharing.lockbox.presentation.snackbar.SnackbarDefaultEvents.couldNotDeleteContract
//
//internal class ContractsViewModelImpl(
//    private val documentsRepository: DocumentsRepository,
//) : ContractsViewModel() {
//    override val deleteSuccess = MutableSharedFlow<Unit>()
//    override val contracts = documentsRepository.getDocumentsPagingFlow().cachedIn(viewModelScope)
//
//    override fun deleteContract(id: String) {
//        viewModelScope.launch {
//            when (documentsRepository.deleteContract(id)) {
//                is Response.Failure -> SnackbarController.receiveEvent(
//                    couldNotDeleteContract { deleteContract(id) }
//                )
//
//                is Response.Success -> deleteSuccess.emit(Unit)
//            }
//        }
//    }
//}
