//
//  LockboxEmailInput.swift
//  iosApp
//
//  Created by Николай on 24.03.2026.
//  Copyright © 2026 LockBox. All rights reserved.
//

import SwiftUI

struct LockboxEmailInput: View {
    @Binding var email: String
    let errorText: String?
    let onClearText: () -> Void
    let onSubmit: (() -> Void)? = nil

    var body: some View {
        LockboxInput(
            value: $email,
            placeholder: String(localized: "email"),
            errorText: errorText,
            keyboardType: .emailAddress,
            textContentType: .emailAddress,
            autocapitalization: .never,
            autocorrectionDisabled: true,
            onSubmit: onSubmit,
            trailingContent: {
                if !email.isEmpty {
                    Button(action: onClearText) {
                        Image(.close)
                            .font(.system(size: 18, weight: .medium))
                            .foregroundStyle(.grey100)
                    }
                    .buttonStyle(.plain)
                    .transition(.opacity.combined(with: .scale))
                }
            }
        )
    }
}
