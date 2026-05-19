//
//  Shapes.swift
//  iosApp
//
//  Created by Николай on 21.09.2025.
//  Copyright © 2025 Lockbox. All rights reserved.
//

import SwiftUI

extension Shape where Self == RoundedRectangle {
    static var roundedCornerShape8: RoundedRectangle {
        RoundedRectangle(cornerRadius: 8, style: .continuous)
    }

    static var roundedCornerShape10: RoundedRectangle {
        RoundedRectangle(cornerRadius: 10, style: .continuous)
    }
    static var roundedCornerShape12: RoundedRectangle {
        RoundedRectangle(cornerRadius: 12, style: .continuous)
    }
    static var roundedCornerShape16: RoundedRectangle {
        RoundedRectangle(cornerRadius: 16, style: .continuous)
    }
    static var roundedCornerShape24: RoundedRectangle {
        RoundedRectangle(cornerRadius: 24, style: .continuous)
    }
    static var roundedCornerShape999: RoundedRectangle {
        RoundedRectangle(cornerRadius: 999, style: .continuous)
    }
}

extension Shape where Self == Circle {
    static var circleShape: Circle {
        Circle()
    }
}
