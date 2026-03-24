//
//  LockboxInput.swift
//  iosApp
//
//  Created by Николай on 24.03.2026.
//  Copyright © 2026 LockBox. All rights reserved.
//

import SwiftUI

struct LockboxInput<TrailingContent: View>: View {
    @Binding var value: String

    let placeholder: String
    let errorText: String?
    let keyboardType: UIKeyboardType
    let textContentType: UITextContentType?
    let autocapitalization: TextInputAutocapitalization
    let autocorrectionDisabled: Bool
    let onSubmit: (() -> Void)?
    @ViewBuilder let trailingContent: () -> TrailingContent

    @FocusState private var isFocused: Bool

    init(
        value: Binding<String>,
        placeholder: String,
        errorText: String? = nil,
        keyboardType: UIKeyboardType = .default,
        textContentType: UITextContentType? = nil,
        autocapitalization: TextInputAutocapitalization = .never,
        autocorrectionDisabled: Bool = true,
        onSubmit: (() -> Void)? = nil,
        @ViewBuilder trailingContent: @escaping () -> TrailingContent = {
            EmptyView()
        }
    ) {
        self._value = value
        self.placeholder = placeholder
        self.errorText = errorText
        self.keyboardType = keyboardType
        self.textContentType = textContentType
        self.autocapitalization = autocapitalization
        self.autocorrectionDisabled = autocorrectionDisabled
        self.onSubmit = onSubmit
        self.trailingContent = trailingContent
    }

    var body: some View {
        VStack(alignment: .leading, spacing: 6) {
            HStack(spacing: 12) {
                TextField("", text: $value, onCommit: { onSubmit?() })
                    .focused($isFocused)
                    .keyboardType(keyboardType)
                    .textContentType(textContentType)
                    .textInputAutocapitalization(autocapitalization)
                    .disableAutocorrection(autocorrectionDisabled)
                    .submitLabel(.done)
                    .bodyLarge()
                    .foregroundStyle(.grey100)
                    .tint(.white)
                    .placeholder(when: value.isEmpty) {
                        Text(placeholder)
                            .bodyLarge()
                            .foregroundStyle(.grey500)
                    }

                if isFocused {
                    trailingContent()
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
            .contentShape(RoundedRectangle(cornerRadius: 8, style: .continuous))

            if let errorText, !errorText.isEmpty {
                Text(errorText)
                    .footnote()
                    .foregroundStyle(.red600)
                    .transition(.opacity)
            }
        }
        .animation(.easeInOut(duration: 0.18), value: isFocused)
        .animation(.easeInOut(duration: 0.18), value: value.isEmpty)
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
