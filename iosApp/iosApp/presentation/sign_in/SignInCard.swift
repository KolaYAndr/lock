//
//  SignInCard.swift
//  iosApp
//
//  Created by Николай on 23.03.2026.
//  Copyright © 2026 LockBox. All rights reserved.
//

import SwiftUI
import Shared

private enum InputField: Hashable {
    case email
    case password
}

struct SignInCard: View {
    @StateObject private var viewModel = SignInViewModel(
        authRepository: KoinHelper.shared.getAuthRepository()
    )
    @FocusState private var focusedField: InputField?

    let onDismiss: () -> Void
    let toSignUp: () -> Void

    @Environment(\.openURL) private var openURL

    private let forgotPasswordURL = URL(
        string: "https://api.lockboxes.ru:8443/realms/lockbox/login-actions/reset-credentials?client_id=public-client"
    )!
    private let userAgreementUrl = URL(string: "https://api.lockboxes.ru:9000/policies/user_agreement_black.pdf")!
    private let privacyPolicyUrl = URL(string: "https://api.lockboxes.ru:9000/policies/privacy_policy_black.pdf")!

    var body: some View {
        VStack(alignment: .leading) {
            ZStack(alignment: .topTrailing) {
                Image(.logoHorizontal)
                    .frame(maxWidth: .infinity, alignment: .center)

                LockboxCloseButton(onClick: onDismiss)
                    .padding(.top, 8)
            }
            .frame(maxWidth: .infinity)
            .padding(.top, 12)

            Text(String(localized: "log_in_your_profile"))
                .foregroundStyle(.white)
                .titleMedium()
                .frame(maxWidth: .infinity, alignment: .center)
                .multilineTextAlignment(.center)
                .padding(.top, 12)

            Text(String(localized: "email"))
                .subHeadlineLarge()
                .foregroundStyle(.grey100)
                .padding(.top, 24)

            LockboxEmailInput(
                email: $viewModel.email,
                errorText: viewModel.emailError ? String(localized: "email_doesnt_match_pattern") : nil,
                onClearText: { viewModel.clearEmail() }
            )
            .focused($focusedField, equals: .email)
            .submitLabel(.next)
            .onSubmit {
                focusedField = .password
            }
            .padding(.top, 6)

            Text(String(localized: "password"))
                .subHeadlineLarge()
                .foregroundStyle(.grey100)
                .padding(.top, 24)

            LockboxPasswordInput(
                password: $viewModel.password,
                isSeen: $viewModel.isPasswordVisible,
                errorText: localizedErrorText(for: viewModel.passwordError),
                onClearText: { viewModel.clearPassword() }
            )
            .focused($focusedField, equals: .password)
            .submitLabel(.done)
            .onSubmit {
                if viewModel.isSignInButtonEnabled {
                    viewModel.signIn()
                }
            }
            .padding(.top, 6)

            Button {
                openURL(forgotPasswordURL)
            } label: {
                Text(String(localized: "forgot_password"))
                    .subHeadlineMedium()
                    .foregroundStyle(.grey500)
                    .underline()
                    .frame(maxWidth: .infinity, alignment: .leading)
                    .padding(.top, 12)
            }
            .buttonStyle(.plain)

            Button {
                viewModel.signIn()
            } label: {
                Text(String(localized: "sign_in"))
                    .frame(maxWidth: .infinity)
            }
            .buttonStyle(.greenBackground)
            .frame(maxWidth: .infinity)
            .disabled(!viewModel.isSignInButtonEnabled)
            .padding(.top, 20)

            PolicyAndConditions()
                .padding(.top, 2)
                .padding(.bottom, 12)

            HStack(spacing: 8) {
                Text(String(localized: "no_account_question"))
                    .subHeadlineMedium()
                    .foregroundStyle(.grey500)

                Button {
                    toSignUp()
                } label: {
                    Text(String(localized: "register"))
                        .subHeadlineLarge()
                        .foregroundStyle(.grey100)
                        .underline()
                }
                .buttonStyle(.plain)
            }
            .frame(maxWidth: .infinity)
            .padding(8)
        }
        .padding(.horizontal, 16)
        .padding(.vertical, 4)
        .onAppear {
            focusedField = .email
        }
        .frame(maxHeight: .infinity, alignment: .top)
    }

    private func localizedErrorText(for error: SignInPasswordError?) -> String? {
        switch error {
        case .invalidUserCredentials:
            return String(localized: "invalid_credentials")
        case .unexpected:
            return String(localized: "unknown_error")
        case .timeout:
            return String(localized: "timeout")
        case .unknownHost:
            return String(localized: "check_internet_connection")
        case .none:
            return nil
        }
    }
}

#Preview {
    SignInCard(
        onDismiss: {},
        toSignUp: {},
        )
}
