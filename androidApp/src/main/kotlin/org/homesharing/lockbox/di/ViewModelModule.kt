package org.homesharing.lockbox.di

import org.homesharing.lockbox.LockboxActivityViewModel
import org.homesharing.lockbox.LockboxActivityViewModelImpl
import org.homesharing.lockbox.presentation_v2.flats_flow.add_or_import_flat.AddFlatViewModel
import org.homesharing.lockbox.presentation_v2.flats_flow.flat_address.FlatAddressViewModel
import org.homesharing.lockbox.presentation_v2.flats_flow.flat_data.FlatDataViewModel
import org.homesharing.lockbox.presentation_v2.flats_flow.flat_rooms.FlatRoomsViewModel
import org.homesharing.lockbox.presentation_v2.guest_invitaion_flow.guest_info.GuestInfoViewModel
import org.homesharing.lockbox.presentation_v2.guest_invitaion_flow.invitation_details.GuestInvitationViewModel
import org.homesharing.lockbox.presentation_v2.login.LoginViewModel
import org.homesharing.lockbox.presentation_v2.profile_flow.profile_setup.ProfileSetupViewModel
import org.homesharing.lockbox.presentation_v2.profile_flow.role_chooser.RoleChooserViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

internal val viewModelModule = module {
    viewModelOf(::LockboxActivityViewModelImpl) bind LockboxActivityViewModel::class
//    viewModelOf(::AccountViewModelImpl) bind AccountViewModel::class
//    viewModelOf(::AddPhoneNumberViewModelImpl) bind AddPhoneNumberViewModel::class
//    viewModelOf(::ChangeEmailViewModelImpl) bind ChangeEmailViewModel::class
//    viewModelOf(::ContractsViewModelImpl) bind ContractsViewModel::class
//    viewModelOf(::LockboxesViewModelImpl) bind LockboxesViewModel::class
//    viewModelOf(::LoginViewModelImpl) bind LoginViewModel::class
//    viewModelOf(::ActiveSessionsViewModelImpl) bind ActiveSessionsViewModel::class
//    viewModelOf(::SettingsViewModelImpl) bind SettingsViewModel::class
//    viewModelOf(::SignInViewModelImpl) bind SignInViewModel::class
//    viewModelOf(::SignUpViewModelImpl) bind SignUpViewModel::class
//    viewModelOf(::AddVirtualDeviceModalViewModelImpl) bind AddVirtualDeviceModalViewModel::class
//    viewModelOf(::ConnectDeviceModalViewModelImpl) bind ConnectDeviceModalViewModel::class
//    viewModelOf(::WarningViewModelImpl) bind WarningViewModel::class
//    viewModel<AccessModalSheetViewModel> { parameters ->
//        AccessModalSheetViewModelImpl(
//            mac = parameters.get(),
//            repository = get(),
//        )
//    }
//    viewModel<DetailedLockboxViewModel> { parameters ->
//        DetailedLockboxViewModelImpl(
//            mac = parameters.get(),
//            boxesRepository = get(),
//        )
//    }
    viewModelOf(::LoginViewModel)
    viewModelOf(::ProfileSetupViewModel)
    viewModelOf(::RoleChooserViewModel)
    viewModelOf(::AddFlatViewModel)
    viewModelOf(::FlatAddressViewModel)
    viewModelOf(::FlatRoomsViewModel)
    viewModelOf(::FlatDataViewModel)
    viewModelOf(::GuestInfoViewModel)
    viewModelOf(::GuestInvitationViewModel)
}