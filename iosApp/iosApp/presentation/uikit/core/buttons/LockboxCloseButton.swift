//
//  LockboxCloseButton.swift
//  iosApp
//
//  Created by Николай on 23.03.2026.
//  Copyright © 2026 Lockbox. All rights reserved.
//

import SwiftUI

internal struct LockboxCloseButton: View {
    let onClick: () -> Void

    var body: some View {
        Button(action: onClick) {
            Image(.close)
                .resizable()
                .frame(width: 16, height: 16)
                .foregroundStyle(.grey500)
        }
        .frame(width: 24, height: 24)
        .background(Circle().fill(.grey800))
        .clipShape(Circle())
    }
}

#Preview {
    LockboxCloseButton {}
}
