package org.homesharing.lockbox.di

//internal val viewModelModule = module {
//    viewModelOf(::LockboxActivityViewModelImpl) bind LockboxActivityViewModel::class
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
//}