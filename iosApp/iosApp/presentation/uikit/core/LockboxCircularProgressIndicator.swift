//
//  LockboxCircularProgressIndicator.swift
//  iosApp
//
//  Created by Николай on 10.04.2026.
//  Copyright © 2026 LockBox. All rights reserved.
//

import SwiftUI

internal struct LockboxCircularProgressIndicator : View {
    var body: some View {
        ProgressView()
            .progressViewStyle(.circular)
            .foregroundStyle(.green500)
    }
}

#Preview {
    LockboxCircularProgressIndicator()
}
