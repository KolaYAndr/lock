package org.homesharing.lockbox.di

import org.homesharing.lockbox.presentation.account.AccountViewModel
import org.homesharing.lockbox.presentation.account.AccountViewModelImpl
import org.homesharing.lockbox.presentation.activity.LockboxActivityViewModel
import org.homesharing.lockbox.presentation.activity.LockboxActivityViewModelImpl
import org.homesharing.lockbox.presentation.add_phone_number.AddPhoneNumberViewModel
import org.homesharing.lockbox.presentation.add_phone_number.AddPhoneNumberViewModelImpl
import org.homesharing.lockbox.presentation.change_email.ChangeEmailViewModel
import org.homesharing.lockbox.presentation.change_email.ChangeEmailViewModelImpl
import org.homesharing.lockbox.presentation.contracts.ContractsViewModel
import org.homesharing.lockbox.presentation.contracts.ContractsViewModelImpl
import org.homesharing.lockbox.presentation.detailed_lockbox.DetailedLockboxViewModel
import org.homesharing.lockbox.presentation.detailed_lockbox.DetailedLockboxViewModelImpl
import org.homesharing.lockbox.presentation.detailed_lockbox.access_modal_sheet.AccessModalSheetViewModel
import org.homesharing.lockbox.presentation.detailed_lockbox.access_modal_sheet.AccessModalSheetViewModelImpl
import org.homesharing.lockbox.presentation.lockboxes.LockboxesViewModel
import org.homesharing.lockbox.presentation.lockboxes.LockboxesViewModelImpl
import org.homesharing.lockbox.presentation.lockboxes.add_virtual_device_modal.AddVirtualDeviceModalViewModel
import org.homesharing.lockbox.presentation.lockboxes.add_virtual_device_modal.AddVirtualDeviceModalViewModelImpl
import org.homesharing.lockbox.presentation.lockboxes.connect_device_modal.ConnectDeviceModalViewModel
import org.homesharing.lockbox.presentation.lockboxes.connect_device_modal.ConnectDeviceModalViewModelImpl
import org.homesharing.lockbox.presentation.login.LoginViewModel
import org.homesharing.lockbox.presentation.login.LoginViewModelImpl
import org.homesharing.lockbox.presentation.security.SecurityViewModel
import org.homesharing.lockbox.presentation.security.SecurityViewModelImpl
import org.homesharing.lockbox.presentation.sessions.ActiveSessionsViewModel
import org.homesharing.lockbox.presentation.sessions.ActiveSessionsViewModelImpl
import org.homesharing.lockbox.presentation.settings.SettingsViewModel
import org.homesharing.lockbox.presentation.settings.SettingsViewModelImpl
import org.homesharing.lockbox.presentation.sign_in.SignInViewModel
import org.homesharing.lockbox.presentation.sign_in.SignInViewModelImpl
import org.homesharing.lockbox.presentation.sign_up.SignUpViewModel
import org.homesharing.lockbox.presentation.sign_up.SignUpViewModelImpl
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

internal val viewModelModule = module {
    viewModelOf(::AccountViewModelImpl) bind AccountViewModel::class
    viewModelOf(::LockboxActivityViewModelImpl) bind LockboxActivityViewModel::class
    viewModelOf(::ChangeEmailViewModelImpl) bind ChangeEmailViewModel::class
    viewModelOf(::ContractsViewModelImpl) bind ContractsViewModel::class
    viewModelOf(::DetailedLockboxViewModelImpl) bind DetailedLockboxViewModel::class
    viewModelOf(::LockboxesViewModelImpl) bind LockboxesViewModel::class
    viewModelOf(::LoginViewModelImpl) bind LoginViewModel::class
    viewModelOf(::SecurityViewModelImpl) bind SecurityViewModel::class
    viewModelOf(::ActiveSessionsViewModelImpl) bind ActiveSessionsViewModel::class
    viewModelOf(::SettingsViewModelImpl) bind SettingsViewModel::class
    viewModelOf(::SignInViewModelImpl) bind SignInViewModel::class
    viewModelOf(::SignUpViewModelImpl) bind SignUpViewModel::class
    viewModelOf(::AccessModalSheetViewModelImpl) bind AccessModalSheetViewModel::class
    viewModelOf(::AddVirtualDeviceModalViewModelImpl) bind AddVirtualDeviceModalViewModel::class
    viewModelOf(::ConnectDeviceModalViewModelImpl) bind ConnectDeviceModalViewModel::class
    viewModelOf(::AddPhoneNumberViewModelImpl) bind AddPhoneNumberViewModel::class
}
