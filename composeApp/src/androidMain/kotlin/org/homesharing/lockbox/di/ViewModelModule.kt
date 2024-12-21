package org.homesharing.lockbox.di

import org.homesharing.lockbox.presentation.login.LoginViewModel
import org.homesharing.lockbox.presentation.sign_in.SignInViewModel
import org.homesharing.lockbox.presentation.sign_up.SignUpViewModel
import org.homesharing.lockbox.presentation.forgot_password.ForgotPasswordViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val provideViewModelModule = module {
    viewModelOf(::SignInViewModel)
    viewModelOf(::SignUpViewModel)
    viewModelOf(::ForgotPasswordViewModel)
    viewModelOf(::LoginViewModel)
}
