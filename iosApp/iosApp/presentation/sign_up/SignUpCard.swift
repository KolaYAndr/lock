//
//  SignUpCard.swift
//  iosApp
//
//  Created by Николай on 23.03.2026.
//  Copyright © 2026 Lockbox. All rights reserved.
//

import SwiftUI
import Shared

struct SignUpCard: View {
    private let toSignIn: () -> Void
    private let onClose: () -> Void

    @StateObject private var viewModel = SignUpViewModel()

    init(toSignIn: @escaping () -> Void, onClose: @escaping () -> Void) {
        self.toSignIn = toSignIn
        self.onClose = onClose
    }

    var body: some View {
        ScrollView {
            VStack(alignment: .leading) {
                CardTopRow(
                    currentStep: viewModel.signUpStep.rawValue + 1,
                    totalSteps: SignUpViewModel.totalSteps,
                    onClose: onClose,
                    onBack: {
                        switch viewModel.signUpStep {
                        case .emailInput: toSignIn()
                        case .passwordInput: viewModel.toStep(.emailInput)
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
                    toNextStep: viewModel.register
                ).padding(.horizontal, 16)
                    
                }
            }
            .frame(maxHeight: .infinity, alignment: .top)
            .padding(.vertical, 4)
            .disabled(viewModel.isRegistering)
        }
    }
}

#Preview {
    SignUpCard(
        toSignIn: {},
        onClose: {}
    )
}
