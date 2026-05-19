//package org.homesharing.lockbox.presentation.login.viewmodel
//
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.flow.update
//
//internal class LoginViewModelImpl : LoginViewModel() {
//    override val loginType: MutableStateFlow<LoginType> = MutableStateFlow(LoginType.SignIn)
//
//    override fun toTypeAuth(authType: LoginType) = loginType.update { authType }
//}
