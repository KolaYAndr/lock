//
//  SignInViewModel.swift
//  iosApp
//
//  Created by Николай on 23.03.2026.
//  Copyright © 2026 LockBox. All rights reserved.
//

import Foundation

@MainActor
final class SignInViewModel: ObservableObject {
    @Published var email: String = ""
    @Published var password: String = ""
    @Published var emailError: Bool = false
    @Published var passwordError: SignInPasswordError? = nil
    @Published var isPasswordVisible: Bool = false
    @Published var isLoading: Bool = false

    var isSignInButtonEnabled: Bool {
        !email.trimmingCharacters(in: .whitespacesAndNewlines).isEmpty &&
            !password.isEmpty &&
            !emailError &&
            passwordError == nil &&
            !isLoading
    }

    func clearEmail() {
        email = ""
        emailError = false
    }

    func clearPassword() {
        password = ""
        passwordError = nil
    }

    func updateEmail(_ newValue: String) {
        email = newValue
        emailError = false
    }

    func updatePassword(_ newValue: String) {
        password = newValue
        passwordError = nil
    }

    func signIn() {
        let trimmedEmail = email.trimmingCharacters(in: .whitespacesAndNewlines)

        guard trimmedEmail.isValidEmail else {
            emailError = true
            return
        }

        emailError = false
        passwordError = nil
    }
}

internal enum SignInPasswordError: Equatable {
    case invalidUserCredentials
    case unexpected
    case unknownHost
    case timeout
}
