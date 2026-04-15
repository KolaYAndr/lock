package org.homesharing.lockbox.di

import org.homesharing.lockbox.LockboxActivityViewModel
import org.homesharing.lockbox.LockboxActivityViewModelImpl
import org.homesharing.lockbox.presentation.account.viewmodel.AccountViewModel
import org.homesharing.lockbox.presentation.account.viewmodel.AccountViewModelImpl
import org.homesharing.lockbox.presentation.add_phone_number.viewmodel.AddPhoneNumberViewModel
import org.homesharing.lockbox.presentation.add_phone_number.viewmodel.AddPhoneNumberViewModelImpl
import org.homesharing.lockbox.presentation.change_email.viewmodel.ChangeEmailViewModel
import org.homesharing.lockbox.presentation.change_email.viewmodel.ChangeEmailViewModelImpl
import org.homesharing.lockbox.presentation.contracts.viewmodel.ContractsViewModel
import org.homesharing.lockbox.presentation.contracts.viewmodel.ContractsViewModelImpl
import org.homesharing.lockbox.presentation.detailed_lockbox.access_modal_sheet.AccessModalSheetViewModel
import org.homesharing.lockbox.presentation.detailed_lockbox.access_modal_sheet.AccessModalSheetViewModelImpl
import org.homesharing.lockbox.presentation.detailed_lockbox.viewmodel.DetailedLockboxViewModel
import org.homesharing.lockbox.presentation.detailed_lockbox.viewmodel.DetailedLockboxViewModelImpl
import org.homesharing.lockbox.presentation.lockboxes.connect_device_modal.ConnectDeviceModalViewModel
import org.homesharing.lockbox.presentation.lockboxes.connect_device_modal.ConnectDeviceModalViewModelImpl
import org.homesharing.lockbox.presentation.lockboxes.viewmodel.LockboxesViewModel
import org.homesharing.lockbox.presentation.lockboxes.viewmodel.LockboxesViewModelImpl
import org.homesharing.lockbox.presentation.lockboxes.virtual_device_modal.AddVirtualDeviceModalViewModel
import org.homesharing.lockbox.presentation.lockboxes.virtual_device_modal.AddVirtualDeviceModalViewModelImpl
import org.homesharing.lockbox.presentation.login.viewmodel.LoginViewModel
import org.homesharing.lockbox.presentation.login.viewmodel.LoginViewModelImpl
import org.homesharing.lockbox.presentation.sessions.viewmodel.ActiveSessionsViewModel
import org.homesharing.lockbox.presentation.sessions.viewmodel.ActiveSessionsViewModelImpl
import org.homesharing.lockbox.presentation.settings.viewmodel.SettingsViewModel
import org.homesharing.lockbox.presentation.settings.viewmodel.SettingsViewModelImpl
import org.homesharing.lockbox.presentation.sign_in.viewmodel.SignInViewModel
import org.homesharing.lockbox.presentation.sign_in.viewmodel.SignInViewModelImpl
import org.homesharing.lockbox.presentation.sign_up.viewmodel.SignUpViewModel
import org.homesharing.lockbox.presentation.sign_up.viewmodel.SignUpViewModelImpl
import org.homesharing.lockbox.presentation.warning.WarningViewModel
import org.homesharing.lockbox.presentation.warning.WarningViewModelImpl
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

internal val viewModelModule = module {
    viewModelOf(::LockboxActivityViewModelImpl) bind LockboxActivityViewModel::class
    viewModelOf(::AccountViewModelImpl) bind AccountViewModel::class
    viewModelOf(::AddPhoneNumberViewModelImpl) bind AddPhoneNumberViewModel::class
    viewModelOf(::ChangeEmailViewModelImpl) bind ChangeEmailViewModel::class
    viewModelOf(::ContractsViewModelImpl) bind ContractsViewModel::class
    viewModelOf(::LockboxesViewModelImpl) bind LockboxesViewModel::class
    viewModelOf(::LoginViewModelImpl) bind LoginViewModel::class
    viewModelOf(::ActiveSessionsViewModelImpl) bind ActiveSessionsViewModel::class
    viewModelOf(::SettingsViewModelImpl) bind SettingsViewModel::class
    viewModelOf(::SignInViewModelImpl) bind SignInViewModel::class
    viewModelOf(::SignUpViewModelImpl) bind SignUpViewModel::class
    viewModelOf(::AddVirtualDeviceModalViewModelImpl) bind AddVirtualDeviceModalViewModel::class
    viewModelOf(::ConnectDeviceModalViewModelImpl) bind ConnectDeviceModalViewModel::class
    viewModelOf(::WarningViewModelImpl) bind WarningViewModel::class
    viewModel<AccessModalSheetViewModel> { parameters ->
        AccessModalSheetViewModelImpl(
            mac = parameters.get(),
            repository = get(),
        )
    }
    viewModel<DetailedLockboxViewModel> { parameters ->
        DetailedLockboxViewModelImpl(
            mac = parameters.get(),
            boxesRepository = get(),
        )
    }
}