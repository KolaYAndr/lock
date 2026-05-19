//package org.homesharing.lockbox.presentation.login.viewmodel
//
//import androidx.lifecycle.ViewModel
//import kotlinx.coroutines.flow.StateFlow
//
//internal abstract class LoginViewModel : ViewModel() {
//    abstract val loginType: StateFlow<LoginType>
//    abstract fun toTypeAuth(authType: LoginType)
//
//}
//
//private const val STEP_ONE = 1
//private const val STEP_TWO = 2
//
//internal sealed class LoginType(val hierarchyIndex: Int) {
//    data object SignIn : LoginType(STEP_ONE)
//    data object SignUp : LoginType(STEP_TWO)
//}
