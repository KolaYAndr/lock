//
//  HelpButton.swift
//  iosApp
//
//  Created by Николай on 08.04.2026.
//  Copyright © 2026 LockBox. All rights reserved.
//

import SwiftUI

internal struct LockboxHelpButton : View {
    private let action: () -> Void
    
    init(action: @escaping () -> Void) {
        self.action = action
    }
    
    var body: some View {
        Button {
            action()
        } label : {
            Image(.headset)
                .resizable()
                .scaledToFit()
                .frame(width: 16, height: 16)
                .foregroundStyle(.grey100)
            
            Text("help")
                .subHeadlineLarge()
                .foregroundStyle(.grey100)
                .padding(7)
        }
        .buttonStyle(.plain)
    }
}

#Preview {
    LockboxHelpButton(action: {})
}
