//
//  SignUpCardViewModel.swift
//  iosApp
//
//  Created by Николай on 26.03.2026.
//  Copyright © 2026 LockBox. All rights reserved.
//

import Foundation
import Shared
import Combine

internal final class SignUpViewModel: ObservableObject {
    @Published var email: String = ""
    @Published var emailError: Bool = false
    @Published var password: String = ""
    @Published var passwordRepeated: String = ""
    @Published var passwordRepeatedError: PasswordRepeatedError? = nil
    @Published var signUpStep: SignUpStep = .emailInput
    static let totalSteps: Int = 3
    
    private var cancellables: Set<AnyCancellable> = []

    private let authRepository: AuthRepository

    init(authRepository: AuthRepository) {
        self.authRepository = authRepository
        
        $email
            .dropFirst()
            .map { $0.trimmingCharacters(in: .whitespacesAndNewlines) }
            .sink { [weak self] _ in
                self?.emailError = false
            }
            .store(in: &cancellables)
    }
    
    func clearEmail() {
        email = ""
        emailError = false
    }

    func sendEmailConfirmation() {
        Task {
            do {
                try await authRepository.sendConfirmation(email: email)
            }
        }
    }

    func checkIfConfirmed() async {
        do {
            try await authRepository.loadTokensInitially(username: email, password: password)
        } catch {
        }
    }

    func signUp() {
        if (password != passwordRepeated) {
            passwordRepeatedError = .passwordsDontMatch
            return
        }

        Task {
            do {
                let response = try await authRepository.signUp(username: email, password: password, passwordRepeated: passwordRepeated)

                switch (response) {
                case let failure as ResponseFailure:
                    self.passwordRepeatedError = parsePasswordRepeatedError(message: failure.message)
                case let success as ResponseSuccess<KotlinUnit>:
                    toStep(.emailConfirmation)
                default:
                    self.passwordRepeatedError = .unexpected
                }
            } catch {
                self.passwordRepeatedError = .unexpected
            }
        }
    }

    func toStep(_ nextStep: SignUpStep) {
        switch (nextStep) {
        case .passwordInput:
            if email.isValidEmail {
                self.signUpStep = nextStep
            } else {
                self.emailError = true
            }
        default:
            self.signUpStep = nextStep
        }
    }

    private let userExists = "{\"errorMessage\":\"User exists with same email\"}"
    private let passwordsDontMatch = "web.exchange.bind.exception"

    private func parsePasswordRepeatedError(message: String) -> PasswordRepeatedError {
        switch (message) {
        case userExists:
            return .userExists
        case passwordsDontMatch:
            return .passwordsDontMatch
        default:
            return .unexpected
        }
    }
}

internal enum SignUpStep: Int {
    case emailInput
    case passwordInput
    case emailConfirmation
}

internal enum PasswordRepeatedError {
    case passwordsDontMatch
    case userExists
    case unexpected
}
