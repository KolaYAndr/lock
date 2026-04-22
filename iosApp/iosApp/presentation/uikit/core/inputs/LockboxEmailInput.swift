//
//  LockboxEmailInput.swift
//  iosApp
//
//  Created by Николай on 24.03.2026.
//  Copyright © 2026 LockBox. All rights reserved.
//

import SwiftUI

struct LockboxEmailInput: View {
    @Binding private var email: String
    private let errorText: String?
    private let onClearText: () -> Void
    private let onSubmit: (() -> Void)? = nil
    
    init(email: Binding<String>, errorText: String?, onClearText: @escaping () -> Void) {
        self._email = email
        self.errorText = errorText
        self.onClearText = onClearText
    }

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
        .animation(.easeInOut(duration: 0.18), value: email.isEmpty)
    }
}

#Preview {
    @Previewable @State var email = "ivan@zolo"
    return LockboxEmailInput(email: $email, errorText: nil, onClearText: { })
}

