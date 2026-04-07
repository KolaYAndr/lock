//
//  LockboxPasswordInput.swift
//  iosApp
//
//  Created by Николай on 24.03.2026.
//  Copyright © 2026 LockBox. All rights reserved.
//

import SwiftUI

struct LockboxPasswordInput: View {
    @Binding private var password: String
    @Binding private var isSeen: Bool

    private let errorText: String?
    private let onClearText: () -> Void
    
    @FocusState private var isFocused: Bool

    init(password: Binding<String>, isSeen: Binding<Bool>, errorText: String?, onClearText: @escaping () -> Void) {
        self._password = password
        self._isSeen = isSeen
        self.onClearText = onClearText
        self.errorText = errorText
    }
    
    var body: some View {
        VStack(alignment: .leading, spacing: 6) {
            HStack(spacing: 12) {
                Group {
                    if isSeen {
                        TextField("", text: $password)
                    } else {
                        SecureField("", text: $password)
                    }
                }
                .focused($isFocused)
                .bodyLarge()
                .foregroundStyle(.grey100)
                .textInputAutocapitalization(.never)
                .disableAutocorrection(true)
                .textContentType(.password)
                .submitLabel(.done)
                .placeholder(when: password.isEmpty) {
                    Text(String(localized: "password_placeholder"))
                        .bodyLarge()
                        .foregroundStyle(.grey500)
                }

                if isFocused {
                    Button {
                        isSeen.toggle()
                    } label: {
                        Image(isSeen ? .eyeCrossed : .eye)
                            .font(.system(size: 16, weight: .medium))
                            .foregroundStyle(.grey100)
                            .accessibilityLabel(isSeen ? "Hide password" : "Show password")
                    }
                    .buttonStyle(.plain)
                }

                if !password.isEmpty {
                    Button {
                        onClearText()
                    } label: {
                        Image(.close)
                            .font(.system(size: 18, weight: .medium))
                            .foregroundStyle(.grey100)
                            .accessibilityLabel("Clear password")
                    }
                    .buttonStyle(.plain)
                    .transition(.opacity.combined(with: .scale))
                }
            }
            .padding(.horizontal, 12)
            .padding(.vertical, 14)
            .background(.grey800)
            .clipShape(RoundedRectangle(cornerRadius: 8, style: .continuous))
            .overlay(
                RoundedRectangle(cornerRadius: 8, style: .continuous)
                    .stroke(borderColor, lineWidth: 1)
            )

            if let errorText, !errorText.isEmpty {
                Text(errorText)
                    .footnote()
                    .foregroundStyle(.red600)
            }
        }
        .animation(.easeInOut(duration: 0.18), value: isSeen)
        .animation(.easeInOut(duration: 0.18), value: password.isEmpty)
        .animation(.easeInOut(duration: 0.18), value: errorText != nil)
    }

    private var borderColor: Color {
        if errorText != nil {
            return .red600
        }
        if isFocused {
            return .grey500
        }
        return .clear
    }
}

#Preview {
    @Previewable @State var password = "Password"
    @Previewable @State var isSeen = true
    LockboxPasswordInput(password: $password, isSeen: $isSeen, errorText: nil, onClearText: {})
}
