//
//  HelpModalSheet.swift
//  iosApp
//
//  Created by Николай on 07.04.2026.
//  Copyright © 2026 Lockbox. All rights reserved.
//

import SwiftUI

internal struct HelpModalSheet : View {
    @Environment(\.dismiss) private var dismiss
    @Environment(\.openURL) private var openURL
    
    private let emailUrl = URL(string: "mailto:help@lockbox.ru")!
    private let tgWebUrl = URL(string: "https://t.me/korolev_2512")!
    private let tgAppUrl = URL(string: "tg://resolve?domain=korolev_2512")!
    
    
    var body: some View {
        ZStack(alignment: .top) {
            Color.grey900.ignoresSafeArea()
            
            VStack(alignment: .center) {
                TitledTopWithCloseAndBackButtons(title: String(localized: "help"), onClose: { dismiss() })
                    .padding(.top, 4)
                LockboxHorizontalDivider()
                
                Image(.headset)
                    .resizable()
                    .scaledToFit()
                    .frame(width: 48, height: 48, alignment: .center)
                    .padding(.top, 48)
                    .foregroundStyle(.grey500)
                
                Text(String(localized: "contact_us"))
                    .titleMedium()
                    .foregroundStyle(.grey100)
                    .padding(.top, 12)
                    .padding(.horizontal, 32)
                
                Text(String(localized: "we_will_try_to_solve_your_issue"))
                    .bodyLarge()
                    .foregroundStyle(.grey500)
                    .multilineTextAlignment(.center)
                    .padding(.top, 8)
                    .padding(.horizontal, 32)
                
                VStack {
                    ImageTextArrow(
                        text: String(localized: "lockbox_email"),
                        imageRes: .mail
                    ).onTapGesture { openURL(emailUrl) }
                    
                    LockboxHorizontalDivider()
                    
                    ImageTextArrow(
                        text: String(localized: "telegram"),
                        imageRes: .send
                    ).onTapGesture {
                        if UIApplication.shared.canOpenURL(tgAppUrl) {
                            UIApplication.shared.open(tgAppUrl)
                        } else {
                            openURL(tgWebUrl)
                        }
                    }
                    
                    LockboxHorizontalDivider()
                    
                    ImageTextArrow(
                        text: String(localized: "whats_app"),
                        imageRes: .phone
                    ).onTapGesture {
                        if UIApplication.shared.canOpenURL(tgAppUrl) {
                            UIApplication.shared.open(tgAppUrl)
                        } else {
                            openURL(tgWebUrl)
                        }
                    }
                }.padding(.horizontal, 16)
                    .padding(.top, 48)
            }
            .frame(maxWidth: .infinity, maxHeight: .infinity, alignment: .top)
        }
    }
}

#Preview {
    HelpModalSheet()
}
