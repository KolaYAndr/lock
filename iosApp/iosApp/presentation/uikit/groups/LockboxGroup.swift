//
//  LockboxGroup.swift
//  iosApp
//
//  Created by Николай on 23.04.2026.
//  Copyright © 2026 LockBox. All rights reserved.
//

import SwiftUI

internal struct LockboxGroup<Content: View>: View {
    private let content: () -> Content
    
    init(
        @ViewBuilder content: @escaping () -> Content
    ) {
        self.content = content
    }
    
    var body: some View {
        VStack(alignment: .leading, spacing: 0) {
            content()
        }
        .background(.grey900)
        .clipShape(.roundedCornerShape8)
        .overlay(
            RoundedRectangle(cornerRadius: 8)
                .inset(by: 0.5)
                .stroke(.grey800, lineWidth: 0.5)
        )
    }
}
