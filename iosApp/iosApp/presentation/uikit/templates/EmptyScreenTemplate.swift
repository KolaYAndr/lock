//
//  EmptyScreenTemplate.swift
//  iosApp
//
//  Created by Николай on 14.04.2026.
//  Copyright © 2026 LockBox. All rights reserved.
//

import SwiftUI

internal struct EmptyScreenTemplate: View {
    private let imageResource: ImageResource
    private let title: String
    private let subtitle: String
    private let buttonText: String
    private let action: () -> Void
    
    init(imageResource: ImageResource, title: String, subtitle: String, buttonText: String, action: @escaping () -> Void) {
        self.imageResource = imageResource
        self.title = title
        self.subtitle = subtitle
        self.buttonText = buttonText
        self.action = action
    }
    
    var body: some View {
        VStack(alignment: .center) {
            Image(imageResource)
                .resizable()
                .scaledToFit()
                .frame(width: 48, height: 48)
                .foregroundStyle(.grey500)
            
            Text(title)
                .titleMedium()
                .foregroundStyle(.grey100)
                .multilineTextAlignment(.center)
                .padding(.top, 12)
            
            Text(subtitle)
                .bodyLarge()
                .foregroundStyle(.grey500)
                .multilineTextAlignment(.center)
                .padding(.top, 8)
            
            Button {
                action()
            } label: {
                Text(buttonText)
                    .bodyMedium()
                    .foregroundStyle(.grey900)
            }
            .buttonStyle(.greenBackground)
            .padding(.top, 16)
        }
        .padding(.horizontal, 32)
        .frame(maxWidth: .infinity, maxHeight: .infinity, alignment: .center)
    }
}

#Preview {
    EmptyScreenTemplate(imageResource: .logIn, title: String(localized: "log_in_your_profile"), subtitle: String(localized: "start_using_today"), buttonText: String(localized: "sign_in"), action: { })
}
