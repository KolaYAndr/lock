//
//  LockboxHorizontalDivider.swift
//  iosApp
//
//  Created by Николай on 03.04.2026.
//  Copyright © 2026 LockBox. All rights reserved.
//

import SwiftUI

internal struct LockboxHorizontalDivider : View {
    var body: some View {
        Divider()
            .frame(height: 0.5)
            .overlay(.grey800)
    }
}

#Preview {
    LockboxHorizontalDivider()
}
