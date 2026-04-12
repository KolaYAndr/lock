//
//  PasswordInputStep.swift
//  iosApp
//
//  Created by Николай on 07.04.2026.
//  Copyright © 2026 LockBox. All rights reserved.
//

import SwiftUI

private enum InputField: Hashable {
    case password
    case repeatedPassword
}

internal struct PasswordInputStep : View {
    @Binding private var password: String
    @Binding private var isPasswordVisible: Bool
    
    @Binding private var passwordRepeated: String
    @Binding private var isPasswordRepeatedVisible: Bool
    private var passwordRepeatedError: PasswordRepeatedError?
    
    @FocusState private var focusedField: InputField?
    
    private let toNextStep: () -> Void
    
    init(password: Binding<String>, isPasswordVisible: Binding<Bool>, passwordRepeated: Binding<String>, isPasswordRepeatedVisible: Binding<Bool>, passwordRepeatedError: PasswordRepeatedError?, toNextStep: @escaping () -> Void) {
        self._password = password
        self._isPasswordVisible = isPasswordVisible
        self._passwordRepeated = passwordRepeated
        self._isPasswordRepeatedVisible = isPasswordRepeatedVisible
        self.passwordRepeatedError = passwordRepeatedError
        self.toNextStep = toNextStep
    }
    
    var body : some View {
        Text(String(localized: "create_password"))
            .foregroundStyle(.white)
            .titleMedium()
            .multilineTextAlignment(.center)
            .padding(.top, 16)
        
        Text(String(localized: "password"))
            .subHeadlineLarge()
            .foregroundStyle(.grey100)
            .multilineTextAlignment(.center)
            .padding(.top, 24)
        
        LockboxPasswordInput(
            password: $password,
            isSeen: $isPasswordVisible,
            errorText: nil,
            onClearText: { password = "" }
        )
        .focused($focusedField, equals: .password)
        .submitLabel(.next)
        .onAppear {
            focusedField = .password
        }
        .onSubmit {
            focusedField = .repeatedPassword
        }
        .padding(.top, 6)
        
        Text(String(localized: "password_confirmation"))
            .subHeadlineLarge()
            .foregroundStyle(.grey100)
            .multilineTextAlignment(.center)
            .padding(.top, 24)
        
        LockboxPasswordInput(
            password: $passwordRepeated,
            isSeen: $isPasswordRepeatedVisible,
            errorText: parsePasswordRepeatedError(passwordRepeatedError),
            onClearText: { passwordRepeated = "" }
        )
        .focused($focusedField, equals: .repeatedPassword)
        .submitLabel(.next)
        .onSubmit {
            toNextStep()
        }
        .padding(.top, 6)
        
        Button {
            toNextStep()
        } label : {
            Text(String(localized: "set_password"))
                .headlineLarge()
                .foregroundStyle(.grey900)
                .frame(maxWidth: .infinity)
        }.buttonStyle(.greenBackground)
            .padding(.top, 24)
    }
    
    private func parsePasswordRepeatedError(_ pRE: PasswordRepeatedError?) -> String? {
        return switch pRE {
        case .passwordsDontMatch: String(localized: "passwords_dont_match")
        case .userExists: String(localized: "user_already_exists")
        case .unexpected: String(localized: "unknown_error")
        case .none: nil
        }
    }
}

#Preview {
    @Previewable @State var password = "ivan@zolo"
    @Previewable @State var repeated = "ivan@zolo"
    @Previewable @State var isVisible = false
    @Previewable @State var isRepeatedVisible = true
    PasswordInputStep(
        password: $password,
        isPasswordVisible: $isVisible,
        passwordRepeated: $repeated,
        isPasswordRepeatedVisible: $isRepeatedVisible,
        passwordRepeatedError: nil,
        toNextStep: {}
    )
}
