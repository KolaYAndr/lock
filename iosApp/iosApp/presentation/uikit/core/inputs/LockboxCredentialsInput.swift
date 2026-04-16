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
    
    init(text: Binding<String>, placeholderText: String) {
        self._text = text
        self.placeholderText = placeholderText
        self.isFocused = isFocused
    }

    @FocusState private var isFocused: Bool

    var body: some View {
        TextField("", text: $text, onCommit: {})
        .focused($isFocused)
        .keyboardType(.default)
        .submitLabel(.done)
        .lineLimit(1)
        .headlineLarge()
        .foregroundStyle(.white)
        .tint(.white)
        .placeholder(when: text.isEmpty) {
            Text(placeholderText)
                .bodyLarge()
                .foregroundStyle(.grey500)
        }
        .padding(.vertical, 10)
        .background(.grey900)
        .clipShape(.roundedCornerShape8)
    }
}

#Preview {
    @Previewable @State var text = ""
    LockboxCredentialsInput(text: $text, placeholderText: "placeholder")
}
