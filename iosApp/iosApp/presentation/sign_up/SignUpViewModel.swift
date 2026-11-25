//
//  SignUpCardViewModel.swift
//  iosApp
//
//  Created by Николай on 26.03.2026.
//  Copyright © 2026 Lockbox. All rights reserved.
//

import Foundation
import Shared
import Combine

internal enum SignUpStep: Int {
    case emailInput
    case passwordInput
}

internal enum PasswordRepeatedError {
    case passwordsDontMatch
    case passwordTooShort
    case userExists
    case unexpected
}

@MainActor
internal final class SignUpViewModel: ObservableObject {
    private let authRepository: AuthRepository = KoinHelper.shared.getAuthRepository()
    
    @Published var email: String = ""
    @Published var emailError: Bool = false
    @Published var password: String = ""
    @Published var isPasswordVisible = false
    @Published var passwordRepeated: String = ""
    @Published var isPasswordRepeatedVisible = false
    @Published var passwordRepeatedError: PasswordRepeatedError? = nil
    @Published var signUpStep: SignUpStep = .emailInput
    
    static let totalSteps: Int = 2
    
    @Published private(set) var isRegistering = false
    private var cancellables: Set<AnyCancellable> = []

    init() {
        $email
            .dropFirst()
            .map { $0.trimmingCharacters(in: .whitespacesAndNewlines) }
            .sink { [weak self] _ in
                self?.emailError = false
            }
            .store(in: &cancellables)

        $passwordRepeated
            .dropFirst()
            .sink { [weak self] _ in
                self?.passwordRepeatedError = nil
            }
            .store(in: &cancellables)
    }

    func toStep(_ nextStep: SignUpStep) {
        guard !isRegistering else { return }
        switch nextStep {
        case .passwordInput:
            if email.trimmingCharacters(in: .whitespacesAndNewlines).isValidEmail {
                self.signUpStep = nextStep
            } else {
                self.emailError = true
            }
        case .emailInput:
            self.signUpStep = nextStep
        }
    }
    
    func clearEmail() {
        email = ""
        emailError = false
    }

    func register() {
        guard !isRegistering else { return }
        guard password == passwordRepeated else {
            passwordRepeatedError = .passwordsDontMatch
            return
        }
        guard password.count >= 8 else {
            passwordRepeatedError = .passwordTooShort
            return
        }

        let email = email.trimmingCharacters(in: .whitespacesAndNewlines)
        let password = password
        isRegistering = true
        Task {
            defer { isRegistering = false }
            do {
                let response = try await authRepository.register(email: email, password: password)
                if let failure = response as? ResponseFailure {
                    passwordRepeatedError = failure.errorCode == "USER_ALREADY_EXISTS" ? .userExists : .unexpected
                }
            } catch {
                passwordRepeatedError = .unexpected
            }
        }
    }
}
