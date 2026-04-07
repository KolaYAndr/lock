//
//  SignUpCard.swift
//  iosApp
//
//  Created by Николай on 23.03.2026.
//  Copyright © 2026 LockBox. All rights reserved.
//

import SwiftUI
import Shared

struct SignUpCard: View {
    private let toSignIn: () -> Void
    private let onClose: () -> Void

    @StateObject private var viewModel = SignUpViewModel(
        authRepository: KoinHelper.shared.getAuthRepository()
    )

    init(toSignIn: @escaping () -> Void, onClose: @escaping () -> Void) {
        self.toSignIn = toSignIn
        self.onClose = onClose
    }

    var body: some View {
        VStack(alignment: .leading) {
            CardTopRow(
                currentStep: viewModel.signUpStep.rawValue + 1,
                totalSteps: SignUpViewModel.totalSteps,
                onClose: onClose,
                onBack: {
                    switch viewModel.signUpStep {
                    case .emailInput: toSignIn()
                    case .passwordInput: viewModel.toStep(.emailInput)
                    case .emailConfirmation: viewModel.toStep(.passwordInput)
                    }
                }
            )
            .padding(.top, 4)

            switch viewModel.signUpStep {

            case .emailInput: EmailInputStep(
                email: $viewModel.email,
                emailError: viewModel.emailError,
                onClearText: viewModel.clearEmail,
                onNextStep: { viewModel.toStep(.passwordInput) },
                toSignIn: toSignIn
            ).padding(.horizontal, 16)

            case .passwordInput: PasswordInputStep(
                password: $viewModel.password,
                isPasswordVisible: $viewModel.isPasswordVisible,
                passwordRepeated: $viewModel.passwordRepeated,
                isPasswordRepeatedVisible: $viewModel.isPasswordRepeatedVisible,
                passwordRepeatedError: viewModel.passwordRepeatedError,
                toNextStep: { viewModel.toStep(.emailConfirmation) }
            ).padding(.horizontal, 16)


            case .emailConfirmation: ConfirmEmailStep(
                onChangeEmail: { viewModel.toStep(.emailInput) },
                sendEmailConfirmation: { viewModel.sendEmailConfirmation() },
                startPollingEmailConfirmation: { viewModel.startPollingEmailConfirmation() },
                stopPollingEmailConfirmation: { viewModel.stopPollingEmailConfirmation() },
                email: viewModel.email
            ).padding(.horizontal, 16)
            }
        }
        .frame(maxHeight: .infinity, alignment: .top)
        .padding(.vertical, 4)
    }
}

#Preview {
    SignUpCard(
        toSignIn: {},
        onClose: {}
    )
}
