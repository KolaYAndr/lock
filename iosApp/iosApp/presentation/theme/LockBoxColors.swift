//
//  LockboxColor.swift
//  iosApp
//
//  Created by Николай on 21.09.2025.
//  Copyright © 2025 Lockbox. All rights reserved.
//

import SwiftUI

extension Color {
    init(argb: UInt32) {
        let a = Double((argb >> 24) & 0xFF) / 255.0
        let r = Double((argb >> 16) & 0xFF) / 255.0
        let g = Double((argb >> 8) & 0xFF) / 255.0
        let b = Double(argb & 0xFF) / 255.0
        self.init(.sRGB, red: r, green: g, blue: b, opacity: a)
    }
}

extension ShapeStyle where Self == Color {
    static var white: Color {
        Color(red: 1, green: 1, blue: 1)
    }

    static var grey50: Color {
        Color(argb: 0xFFFAFAFA)
    }
    static var grey100: Color {
        Color(argb: 0xFFF5F5F5)
    }
    static var grey200: Color {
        Color(argb: 0xFFE5E5E5)
    }
    static var grey300: Color {
        Color(argb: 0xFFD4D4D4)
    }
    static var grey400: Color {
        Color(argb: 0xFFA3A3A3)
    }
    static var grey500: Color {
        Color(argb: 0xFF737373)
    }
    static var grey600: Color {
        Color(argb: 0xFF525252)
    }
    static var grey700: Color {
        Color(argb: 0xFF404040)
    }
    static var grey800: Color {
        Color(argb: 0xFF262626)
    }
    static var grey900: Color {
        Color(argb: 0xFF171717)
    }
    static var grey950: Color {
        Color(argb: 0xFF0A0A0A)
    }
    static var grey1000: Color {
        Color(argb: 0xFF090909)
    }

    static var red50: Color {
        Color(argb: 0xFFFEF2F2)
    }
    static var red100: Color {
        Color(argb: 0xFFFEE2E2)
    }
    static var red200: Color {
        Color(argb: 0xFFFECACA)
    }
    static var red300: Color {
        Color(argb: 0xFFFCA5A5)
    }
    static var red400: Color {
        Color(argb: 0xFFF87171)
    }
    static var red500: Color {
        Color(argb: 0xFFEF4444)
    }
    static var red600: Color {
        Color(argb: 0xFFDC2626)
    }
    static var red700: Color {
        Color(argb: 0xFFB91C1C)
    }
    static var red800: Color {
        Color(argb: 0xFF991B1B)
    }
    static var red900: Color {
        Color(argb: 0xFF7F1D1D)
    }

    static var yellow50: Color {
        Color(argb: 0xFFFFFBEB)
    }
    static var yellow100: Color {
        Color(argb: 0xFFFEF3C7)
    }
    static var yellow200: Color {
        Color(argb: 0xFFFDE68A)
    }
    static var yellow300: Color {
        Color(argb: 0xFFFCD34D)
    }
    static var yellow400: Color {
        Color(argb: 0xFFFBBF24)
    }
    static var yellow500: Color {
        Color(argb: 0xFFF59E0B)
    }
    static var yellow600: Color {
        Color(argb: 0xFFD97706)
    }
    static var yellow700: Color {
        Color(argb: 0xFFB45309)
    }
    static var yellow800: Color {
        Color(argb: 0xFF92400E)
    }
    static var yellow900: Color {
        Color(argb: 0xFF78350F)
    }

    static var green50: Color {
        Color(argb: 0xFFF0FDF4)
    }
    static var green100: Color {
        Color(argb: 0xFFDCFCE7)
    }
    static var green200: Color {
        Color(argb: 0xFFBBF7D0)
    }
    static var green300: Color {
        Color(argb: 0xFF86EFAC)
    }
    static var green400: Color {
        Color(argb: 0xFF4ADE80)
    }
    static var green500: Color {
        Color(argb: 0xFF22C55E)
    }
    static var green600: Color {
        Color(argb: 0xFF16A34A)
    }
    static var green700: Color {
        Color(argb: 0xFF15803D)
    }
    static var green800: Color {
        Color(argb: 0xFF166534)
    }
    static var green900: Color {
        Color(argb: 0xFF14532D)
    }
}
