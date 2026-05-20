//
//  TrailingContent.swift
//  iosApp
//
//  Created by Николай on 24.03.2026.
//  Copyright © 2026 Lockbox. All rights reserved.
//

import SwiftUI

internal extension View {
    @ViewBuilder
    func placeholder<Content: View>(
        when shouldShow: Bool,
        alignment: Alignment = .leading,
        @ViewBuilder placeholder: () -> Content
    ) -> some View {
        overlay(alignment: alignment) {
            if shouldShow {
                placeholder()
            }
        }
    }
}
