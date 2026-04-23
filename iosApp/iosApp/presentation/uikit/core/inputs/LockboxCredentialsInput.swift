//
//  LockboxCredentialsInput.swift
//  iosApp
//
//  Created by Николай on 16.04.2026.
//  Copyright © 2026 LockBox. All rights reserved.
//

import SwiftUI

struct LockboxCredentialsInput: View {
    @Binding private var text: String
    private let placeholderText: String
    private let canSave: Bool
    private let onCancel: () -> Void
    private let onFinish: () -> Void
    
    init(text: Binding<String>, placeholderText: String, canSave: Bool = false, onCancel: @escaping () -> Void = {}, onFinish: @escaping () -> Void = {}) {
        self._text = text
        self.placeholderText = placeholderText
        self.canSave = canSave
        self.onCancel = onCancel
        self.onFinish = onFinish
    }
    
    @FocusState private var isFocused: Bool
    
    var body: some View {
        HStack(spacing: 8) {
            TextField("", text: $text)
                .focused($isFocused)
                .keyboardType(.default)
                .submitLabel(.done)
                .onSubmit {
                    if canSave {
                        onFinish()
                        isFocused = false
                    }
                }
                .lineLimit(1)
                .headlineLarge()
                .foregroundStyle(.white)
                .tint(.white)
                .placeholder(when: text.isEmpty) {
                    Text(placeholderText)
                        .bodyLarge()
                        .foregroundStyle(.grey500)
                }
                .frame(maxWidth: .infinity, alignment: .leading)
            
            
            if canSave && isFocused {
                HStack {
                    Button {
                        onCancel()
                        isFocused = false
                    } label: {
                        Image(.close)
                            .resizable()
                            .scaledToFit()
                            .frame(width: 24, height: 24)
                            .foregroundStyle(.grey100)
                            .padding(6)
                    }
                    .buttonStyle(.plain)
                    .transition(.opacity.combined(with: .scale))
                    
                    Button {
                        onFinish()
                        isFocused = false
                    } label: {
                        Image(.check)
                            .resizable()
                            .scaledToFit()
                            .frame(width: 24, height: 24)
                            .foregroundStyle(.grey100)
                            .padding(6)
                    }
                    .buttonStyle(.plain)
                    .transition(.opacity.combined(with: .scale))
                }
            }
        }
        .padding(.horizontal, 16)
        .padding(.vertical, 10)
        .background(.grey900)
        .clipShape(.roundedCornerShape8)
    }
}

#Preview {
    @Previewable @State var text = ""
    LockboxCredentialsInput(text: $text, placeholderText: "placeholder")
}
