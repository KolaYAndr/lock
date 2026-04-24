//
//  SecurityScreen.swift
//  iosApp
//
//  Created by Николай on 13.04.2026.
//  Copyright © 2026 LockBox. All rights reserved.
//

import SwiftUI

internal struct SecurityScreen: View {
    @Environment(\.openURL) private var openURL
    private let forgotPasswordUrl = URL(string: String(localized: "forgot_password_url"))!
    
    var body: some View {
        ScrollView {
            VStack(alignment: .leading, spacing: 12) {
                SecurityProperty(title: String(localized: "change_password"))
                    .onTapGesture {
                        openURL(forgotPasswordUrl)
                    }
                
                NavigationLink(value: SettingsRoute.activeSessions) {
                    SecurityProperty(title: String(localized: "active_sessions"))
                }
            }
            .padding(.horizontal, 16)
            .padding(.vertical, 12)
            .navigationTitle(Text(String(localized: "security")))
        }
    }
}

private struct SecurityProperty: View {
    private let title: String
    
    init(title: String) {
        self.title = title
    }
    
    var body: some View {
        HStack(spacing: 12) {
            Text(title)
                .frame(maxWidth: .infinity, alignment: .leading)
                .headlineLarge()
                .foregroundStyle(.white)
                .padding(.vertical, 14)
            
            Image(.chevronRight)
                .resizable()
                .scaledToFit()
                .frame(width: 24, height: 24)
                .foregroundStyle(.grey500)
        }
        .padding(.horizontal, 16)
        .background(.grey900)
        .clipShape(.roundedCornerShape8)
    }
}

#Preview {
    SecurityScreen()
}

