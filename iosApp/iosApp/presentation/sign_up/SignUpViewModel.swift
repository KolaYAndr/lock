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

internal final class SignUpViewModel: ObservableObject {
    @Published var email: String = ""
    @Published var emailError: Bool = false
    @Published var password: String = ""
    @Published var isPasswordVisible = false
    @Published var passwordRepeated: String = ""
    @Published var isPasswordRepeatedVisible = false
    @Published var passwordRepeatedError: PasswordRepeatedError? = nil
    @Published var signUpStep: SignUpStep = .emailInput
    static let totalSteps: Int = 3

    private var cancellables: Set<AnyCancellable> = []
    private var pollingTask: Task<Void, Never>? = nil

    func startPollingEmailConfirmation() {
        pollingTask?.cancel()
        pollingTask = Task { [weak self] in
            while !Task.isCancelled {
                try? await Task.sleep(nanoseconds: 1_500_000_000)
                await self?.checkIfConfirmed()
            }
        }
    }

    private let authRepository: AuthRepository
    
    func stopPollingEmailConfirmation() {
        pollingTask?.cancel()
        pollingTask = nil
    }

    private let userExistsErrorMessage = "{\"errorMessage\":\"User exists with same email\"}"
    private let passwordsDontMatchErrorCode = "web.exchange.bind.exception"

    func parsePasswordRepeatedError(message: String) -> PasswordRepeatedError {
        switch message {
        case userExistsErrorMessage:
            return .userExists
        case passwordsDontMatchErrorCode:
            return .passwordsDontMatch
        default:
            return .unexpected
        }
    }
    
    init(authRepository: AuthRepository) {
        self.authRepository = authRepository

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
        } catch { }
    }
}
