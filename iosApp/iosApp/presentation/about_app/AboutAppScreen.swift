//
//  AboutAppScreen.swift
//  iosApp
//
//  Created by Николай on 13.04.2026.
//  Copyright © 2026 LockBox. All rights reserved.
//

import SwiftUI

internal struct AboutAppScreen: View {
    @Environment(\.openURL) private var openURL
    private let privacyPolicyUrl = URL(string: String(localized: "privacy_policy_url"))!
    private let userAgreementUrl = URL(string: String(localized: "user_agreement_url"))!
    
    var body: some View {
        ScrollView {
            VStack {
                VStack {
                    Image(.logo)
                        .resizable()
                        .scaledToFit()
                        .frame(width: 80, height: 80)
                        .padding(.top, 150)
                    
                    Text(String(format: NSLocalizedString("app_version", comment: ""), "1.0.0"))
                        .subHeadlineMedium()
                        .foregroundStyle(.grey500)
                        .padding(.top, 28)
                        .multilineTextAlignment(.center)
                    
                    Text(String(localized: "legal_info"))
                        .footnote()
                        .foregroundStyle(.grey700)
                        .padding(.top, 8)
                        .multilineTextAlignment(.center)
                    
                    Text(String(localized: "year_lockbox"))
                        .footnote()
                        .foregroundStyle(.grey700)
                        .padding(.top, 2)
                        .padding(.bottom, 150)
                        .multilineTextAlignment(.center)
                }
                .frame(maxHeight: .infinity, alignment: .center)
                .padding(.horizontal, 16)
                
                LockboxGroup {
                    Agreement(title: String(localized: "user_agreement"))
                        .onTapGesture {
                            openURL(userAgreementUrl)
                        }
                    
                    LockboxHorizontalDivider()
                    
                    Agreement(title: String(localized: "privacy_policy"))
                        .onTapGesture {
                            openURL(privacyPolicyUrl)
                        }
                }
                .padding(.bottom, 12)
            }
            .padding(.horizontal, 16)
        }.navigationTitle(Text(String(localized: "about_app")))
    }
}

private struct Agreement: View {
    private let title: String
    
    init(title: String) {
        self.title = title
    }
    
    var body: some View {
        HStack(spacing: 12) {
            Text(title)
                .headlineLarge()
                .foregroundStyle(.white)
                .frame(maxWidth: .infinity, alignment: .leading)
                .padding(.vertical, 10)
            
            Image(.chevronRight)
                .resizable()
                .scaledToFit()
                .frame(width: 24, height: 24)
                .foregroundStyle(.grey500)
        }
        .padding(.horizontal, 16)
    }
}

#Preview {
    AboutAppScreen()
}
