//
//  SignInViewModel.swift
//  iosApp
//
//  Created by Николай on 23.03.2026.
//  Copyright © 2026 LockBox. All rights reserved.
//

import Foundation
import Combine
import Shared

@MainActor
final class SignInViewModel: ObservableObject {
    @Published var email: String = ""
    @Published var password: String = ""
    @Published var emailError: Bool = false
    @Published var passwordError: SignInPasswordError? = nil
    @Published var isPasswordVisible: Bool = false
    
    private let authRepository: AuthRepository = KoinHelper.shared.getAuthRepository()
    private var cancellables: Set<AnyCancellable> = []
    
    init() {
        $email
            .dropFirst()
            .map { $0.trimmingCharacters(in: .whitespacesAndNewlines) }
            .sink { [weak self] _ in
                self?.emailError = false
            }
            .store(in: &cancellables)
        
        $password
            .dropFirst()
            .sink { [weak self] _ in
                self?.passwordError = nil
            }
            .store(in: &cancellables)
    }
    
    var isSignInButtonEnabled: Bool {
        !email.trimmingCharacters(in: .whitespacesAndNewlines).isEmpty &&
        !password.isEmpty &&
        !emailError &&
        passwordError == nil
    }
    
    func clearEmail() {
        email = ""
        emailError = false
    }
    
    func clearPassword() {
        password = ""
        passwordError = nil
    }
    
    func signIn() {
        let trimmedEmail = email.trimmingCharacters(in: .whitespacesAndNewlines)
        
        guard trimmedEmail.isValidEmail else {
            emailError = true
            return
        }
        
        Task { @MainActor in
            do {
                let response = try await authRepository.loadTokensInitially(
                    username: trimmedEmail,
                    password: password
                )
                
                if let failure = response as? ResponseFailure {
                    self.passwordError = parsePasswordError(message: failure.message)
                }
            } catch {
                self.passwordError = .unexpected
            }
        }
    }
    
    private func parsePasswordError(message: String) -> SignInPasswordError {
        switch message {
        case "INVALID_USER_CREDENTIALS":
            return .invalidUserCredentials
        default:
            return .unexpected
        }
    }
}

internal enum SignInPasswordError: Equatable {
    case invalidUserCredentials
    case unexpected
    case unknownHost
    case timeout
}
