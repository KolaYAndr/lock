//
//  LoginTypeAnimation.swift
//  iosApp
//
//  Created by Николай on 23.03.2026.
//  Copyright © 2026 Lockbox. All rights reserved.
//

import SwiftUI

internal var forwardTransition: AnyTransition {
    .asymmetric(
        insertion: .move(edge: .trailing),
        removal: .move(edge: .leading)
    )
}

internal var backwardTransition: AnyTransition {
    .asymmetric(
        insertion: .move(edge: .leading),
        removal: .move(edge: .trailing)
    )
}
