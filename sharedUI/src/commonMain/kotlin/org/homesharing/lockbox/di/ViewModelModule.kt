package org.homesharing.lockbox.di

import org.homesharing.lockbox.presentation.LockboxAppViewModel
import org.homesharing.lockbox.presentation.add_flat_flow.add_or_import_flat.AddFlatViewModel
import org.homesharing.lockbox.presentation.add_flat_flow.flat_address.FlatAddressViewModel
import org.homesharing.lockbox.presentation.add_flat_flow.flat_data.FlatDataViewModel
import org.homesharing.lockbox.presentation.add_flat_flow.flat_rooms.FlatRoomsViewModel
import org.homesharing.lockbox.presentation.flat_detailed.FlatDetailedViewModel
import org.homesharing.lockbox.presentation.flat_list.FlatListViewModel
import org.homesharing.lockbox.presentation.guest_invitation_flow.check_in_setup.CheckInViewModel
import org.homesharing.lockbox.presentation.guest_invitation_flow.check_invitation.CheckInvitationViewModel
import org.homesharing.lockbox.presentation.guest_invitation_flow.guest_info.GuestInfoViewModel
import org.homesharing.lockbox.presentation.guest_invitation_flow.invitation_details.GuestInvitationViewModel
import org.homesharing.lockbox.presentation.login.LoginViewModel
import org.homesharing.lockbox.presentation.login_otp.LoginOtpViewModel
import org.homesharing.lockbox.presentation.navigation.authenticated.AuthenticatedNavigationViewModel
import org.homesharing.lockbox.presentation.setup_profile_flow.name_setup.NameSetupViewModel
import org.homesharing.lockbox.presentation.setup_profile_flow.role_picker.RolePickerViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {
    viewModelOf(::AuthenticatedNavigationViewModel)
    viewModelOf(::LoginViewModel)
    viewModelOf(::LoginOtpViewModel)
    viewModelOf(::NameSetupViewModel)
    viewModelOf(::RolePickerViewModel)
    viewModelOf(::AddFlatViewModel)
    viewModelOf(::FlatAddressViewModel)
    viewModelOf(::FlatRoomsViewModel)
    viewModelOf(::FlatDataViewModel)
    viewModelOf(::GuestInfoViewModel)
    viewModelOf(::GuestInvitationViewModel)
    viewModelOf(::CheckInViewModel)
    viewModelOf(::CheckInvitationViewModel)
    viewModelOf(::LockboxAppViewModel)
    viewModelOf(::FlatDetailedViewModel)
    viewModelOf(::FlatListViewModel)
}
