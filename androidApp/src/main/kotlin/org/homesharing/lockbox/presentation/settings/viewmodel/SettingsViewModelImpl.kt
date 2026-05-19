//package org.homesharing.lockbox.presentation.settings.viewmodel
//
//import androidx.lifecycle.viewModelScope
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.flow.update
//import kotlinx.coroutines.launch
//import org.homesharing.lockbox.domain.api.response.Response
//import org.homesharing.lockbox.domain.model.avatar.Avatar
//import org.homesharing.lockbox.domain.repository.AccountRepository
//import org.homesharing.lockbox.presentation.snackbar.SnackbarController
//import org.homesharing.lockbox.presentation.snackbar.SnackbarDefaultEvents.couldNotLoadAccountInfo
//import org.homesharing.lockbox.presentation.snackbar.SnackbarDefaultEvents.couldNotRefreshAccountInfo
//
//internal class SettingsViewModelImpl(
//    private val repository: AccountRepository,
//) : SettingsViewModel() {
//    override val uiState = MutableStateFlow(SettingsUiState())
//
//    init {
//        fetchInfo()
//    }
//
//    private fun fetchInfo() {
//        viewModelScope.launch {
//            when (val response = repository.getProfileInfo()) {
//                is Response.Success -> {
//                    uiState.update {
//                        it.copy(
//                            fullName = response.data.legalInfo.fullName,
//                            email = response.data.contactInfo.email,
//                        )
//                    }
//                }
//
//                is Response.Failure -> SnackbarController.receiveEvent(couldNotLoadAccountInfo { fetchInfo() })
//            }
//
//            val avatar = repository.getImageLink()
//            if (avatar is Response.Success<Avatar>) {
//                uiState.update {
//                    it.copy(imageLink = avatar.data.downloadUrl)
//                }
//            }
//        }
//    }
//
//    override fun refreshAccountInfo() {
//        viewModelScope.launch {
//            uiState.update { it.copy(isRefreshing = true) }
//            when (val info = repository.getProfileInfo()) {
//                is Response.Success -> {
//                    launch {
//                        uiState.update {
//                            it.copy(
//                                fullName = info.data.legalInfo.fullName,
//                                email = info.data.contactInfo.email,
//                            )
//                        }
//                    }
//                }
//
//                is Response.Failure -> SnackbarController.receiveEvent(couldNotRefreshAccountInfo { refreshAccountInfo() })
//            }
//            uiState.update { it.copy(isRefreshing = false) }
//
//            val avatar = repository.getImageLink()
//            if (avatar is Response.Success<Avatar>) {
//                uiState.update {
//                    it.copy(imageLink = avatar.data.downloadUrl)
//                }
//            }
//        }
//    }
//
//    override fun logOut() {
//        viewModelScope.launch {
//            repository.logOut()
//        }
//    }
//}
