//package org.homesharing.lockbox.presentation.account.viewmodel
//
//import androidx.lifecycle.viewModelScope
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.flow.update
//import kotlinx.coroutines.launch
//import org.homesharing.lockbox.domain.api.response.Response
//import org.homesharing.lockbox.domain.model.avatar.Avatar
//import org.homesharing.lockbox.domain.repository.AccountRepository
//import org.homesharing.lockbox.presentation.snackbar.SnackbarController
//import org.homesharing.lockbox.presentation.snackbar.SnackbarDefaultEvents
//import org.homesharing.lockbox.presentation.snackbar.SnackbarDefaultEvents.couldNotDeleteAccountEvent
//import org.homesharing.lockbox.presentation.snackbar.SnackbarDefaultEvents.couldNotUpdateUserImageEvent
//import org.homesharing.lockbox.presentation.snackbar.SnackbarDefaultEvents.couldNotUpdateUserInfoEvent
//import org.homesharing.lockbox.presentation.snackbar.SnackbarDefaultEvents.successfulUpdateUserImageEvent
//
//
//internal class AccountViewModelImpl(
//    private val repository: AccountRepository,
//) : AccountViewModel() {
//    override val uiState = MutableStateFlow(AccountUiState())
//
//    init {
//        fetchInfo()
//    }
//
//    override fun updateProfileName() {
//        viewModelScope.launch {
//            repository.updateProfileInfo(
//                fullName = uiState.value.fullName
//            )
//        }
//    }
//
//    private suspend fun retryUpdatingInfo() {
//        val response = repository.updateProfileInfo(
//            fullName = uiState.value.fullName
//        )
//
//        if (response is Response.Failure)
//            SnackbarController.receiveEvent(couldNotUpdateUserInfoEvent { retryUpdatingInfo() })
//    }
//
//    private fun fetchInfo() {
//        viewModelScope.launch {
//            when (val accountInfo = repository.getProfileInfo()) {
//                is Response.Success -> {
//                    uiState.update {
//                        it.copy(
//                            fullName = accountInfo.data.legalInfo.fullName,
//                            email = accountInfo.data.contactInfo.email,
//                        )
//                    }
//                }
//
//                is Response.Failure -> SnackbarController.receiveEvent(
//                    SnackbarDefaultEvents.couldNotRefreshAccountInfo { retryUpdatingInfo() }
//                )
//            }
//
//            val avatar = repository.getImageLink()
//            if (avatar is Response.Success<Avatar>) {
//                uiState.update {
//                    it.copy(
//                        imageUrl = avatar.data.downloadUrl
//                    )
//                }
//            }
//        }
//    }
//
//
//    override fun onNameChange(newName: String) = uiState.update {
//        it.copy(fullName = newName)
//    }
//
//    override fun sendImage(data: ByteArray) {
//        viewModelScope.launch {
//            when (repository.sendImage(data)) {
//                is Response.Success ->
//                    SnackbarController.receiveEvent(successfulUpdateUserImageEvent)
//
//                is Response.Failure -> SnackbarController.receiveEvent(couldNotUpdateUserImageEvent)
//            }
//        }
//    }
//
//    override fun deleteAccount() {
//        viewModelScope.launch {
//            when (repository.deactivateUser()) {
//                is Response.Success -> repository.logOut()
//                is Response.Failure ->
//                    SnackbarController.receiveEvent(couldNotDeleteAccountEvent { deleteAccount() })
//            }
//        }
//    }
//}
