//package org.homesharing.lockbox.presentation.settings.viewmodel
//
//import androidx.lifecycle.ViewModel
//import kotlinx.coroutines.flow.StateFlow
//
//internal abstract class SettingsViewModel : ViewModel() {
//    abstract val uiState: StateFlow<SettingsUiState>
//    abstract fun refreshAccountInfo()
//    abstract fun logOut()
//}
//
//internal data class SettingsUiState(
//    val fullName: String = "",
//    val email: String = "",
//    val imageLink: String = "",
//    val isRefreshing: Boolean = false,
//)
