//
//  EmailInputStep.swift
//  iosApp
//
//  Created by Николай on 03.04.2026.
//  Copyright © 2026 LockBox. All rights reserved.
//

import SwiftUI

internal struct EmailInputStep : View {
    @Binding private var email: String
    private let emailError: Bool
    private let onClearText: () -> Void
    private let onNextStep: () -> Void
    private let toSignIn: () -> Void
    
    init(email: Binding<String>, emailError: Bool, onClearText: @escaping () -> Void, onNextStep: @escaping () -> Void, toSignIn: @escaping () -> Void) {
        self._email = email
        self.emailError = emailError
        self.onClearText = onClearText
        self.onNextStep = onNextStep
        self.toSignIn = toSignIn
    }
    
    @FocusState private var isFocused: Bool?
    
    var body: some View {
        Text(String(localized: "type_in_email"))
            .titleMedium()
            .foregroundStyle(.white)
            .padding(.top, 16)
        
        Text(String(localized: "promise_to_text_only_important_info"))
            .bodyMedium()
            .foregroundStyle(.grey500)
            .padding(.top, 8)
        
        Text(String(localized: "email"))
            .subHeadlineLarge()
            .foregroundStyle(.grey100)
            .padding(.top, 24)
        
        LockboxEmailInput(
            email: $email,
            errorText: emailError ? String(localized: "email_doesnt_match_pattern") : nil,
            onClearText: { onClearText() }
        ).focused($isFocused, equals: true)
        .onAppear { isFocused = true }
        .submitLabel(.next)
        .onSubmit { onNextStep() }
        .padding(.top, 6)
        
        Button {
            onNextStep()
        } label : {
            Text(String(localized: "confirm_email"))
                .headlineLarge()
                .foregroundStyle(.grey900)
                .frame(maxWidth: .infinity)
        }.buttonStyle(.greenBackground)
        .padding(.top, 24)
        
        PolicyAndConditions()
            .padding(.bottom, 12)
            .padding(.top, 2)
        
        HStack(spacing: 8) {
            Text(String(localized: "no_account_question"))
                .subHeadlineMedium()
                .foregroundStyle(.grey500)
            
            Button {
                toSignIn()
            } label: {
                Text(String(localized: "register"))
                    .subHeadlineLarge()
                    .foregroundStyle(.grey100)
                    .underline()
            }.buttonStyle(.plain)
        }
        .frame(maxWidth: .infinity)
        .padding(.top, 8)
    }
}

#Preview {
    @Previewable @State var email = "ivan@zolo"
    EmailInputStep(email: $email, emailError: false, onClearText: {}, onNextStep: {}, toSignIn: {})
}
