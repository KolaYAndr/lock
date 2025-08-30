package org.homesharing.lockbox.di

import org.homesharing.lockbox.presentation.login.LoginViewModel
import org.homesharing.lockbox.presentation.sign_in.SignInViewModel
import org.homesharing.lockbox.presentation.sign_up.SignUpViewModel
import org.homesharing.lockbox.presentation.settings.SettingsViewModel
import org.homesharing.lockbox.presentation.account.AccountViewModel
import org.homesharing.lockbox.presentation.security.SecurityViewModel
import org.homesharing.lockbox.presentation.change_email.ChangeEmailViewModel
import org.homesharing.lockbox.presentation.add_phone_number.AddPhoneNumberViewModel
import org.homesharing.lockbox.presentation.lockboxes.LockBoxesViewModel
import org.homesharing.lockbox.presentation.detailed_lockbox.DetailedLockBoxViewModel
import org.homesharing.lockbox.presentation.detailed_lockbox.access_modal_sheet.AccessModalSheetViewModel
import org.homesharing.lockbox.presentation.sessions.ActiveSessionsViewModel
import org.homesharing.lockbox.presentation.lockboxes.virtual_device_modal.AddVirtualDeviceViewModel
import org.homesharing.lockbox.LockBoxActivityViewModel
import org.homesharing.lockbox.presentation.documents.ContractsViewModel
import org.homesharing.lockbox.presentation.lockboxes.connect_device_modal.ConnectDeviceViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

internal val viewModelModule = module {
    viewModelOf(::SignInViewModel)
    viewModelOf(::SignUpViewModel)
    viewModelOf(::LoginViewModel)
    viewModelOf(::SettingsViewModel)
    viewModelOf(::AccountViewModel)
    viewModelOf(::SecurityViewModel)
    viewModelOf(::ChangeEmailViewModel)
    viewModelOf(::AddPhoneNumberViewModel)
    viewModelOf(::LockBoxesViewModel)
    viewModelOf(::LockBoxActivityViewModel)
    viewModelOf(::DetailedLockBoxViewModel)
    viewModelOf(::ActiveSessionsViewModel)
    viewModelOf(::AccessModalSheetViewModel)
    viewModelOf(::AddVirtualDeviceViewModel)
    viewModelOf(::ConnectDeviceViewModel)
    viewModelOf(::ContractsViewModel)
}
