//
//  LockboxColor.swift
//  iosApp
//
//  Created by Николай on 21.09.2025.
//  Copyright © 2025 LockBox. All rights reserved.
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
    static var white: Color { Color(red: 1, green: 1, blue: 1) }
    
    static var grey50: Color  { Color(argb: 0xFAFAFA) }
    static var grey100: Color { Color(argb: 0xF5F5F5) }
    static var grey200: Color { Color(argb: 0xE5E5E5) }
    static var grey300: Color { Color(argb: 0xD4D4D4) }
    static var grey400: Color { Color(argb: 0xA3A3A3) }
    static var grey500: Color { Color(argb: 0x737373) }
    static var grey600: Color { Color(argb: 0x525252) }
    static var grey700: Color { Color(argb: 0x404040) }
    static var grey800: Color { Color(argb: 0x262626) }
    static var grey900: Color { Color(argb: 0x171717) }
    static var grey950: Color { Color(argb: 0x0A0A0A) }
    static var grey1000: Color { Color(argb: 0x090909) }

    static var red50: Color  { Color(argb: 0xFEF2F2) }
    static var red100: Color { Color(argb: 0xFEE2E2) }
    static var red200: Color { Color(argb: 0xFECACA) }
    static var red300: Color { Color(argb: 0xFCA5A5) }
    static var red400: Color { Color(argb: 0xF87171) }
    static var red500: Color { Color(argb: 0xEF4444) }
    static var red600: Color { Color(argb: 0xDC2626) }
    static var red700: Color { Color(argb: 0xB91C1C) }
    static var red800: Color { Color(argb: 0x991B1B) }
    static var red900: Color { Color(argb: 0x7F1D1D) }

    static var yellow50: Color  { Color(argb: 0xFFFBEB) }
    static var yellow100: Color { Color(argb: 0xFEF3C7) }
    static var yellow200: Color { Color(argb: 0xFDE68A) }
    static var yellow300: Color { Color(argb: 0xFCD34D) }
    static var yellow400: Color { Color(argb: 0xFBBF24) }
    static var yellow500: Color { Color(argb: 0xF59E0B) }
    static var yellow600: Color { Color(argb: 0xD97706) }
    static var yellow700: Color { Color(argb: 0xB45309) }
    static var yellow800: Color { Color(argb: 0x92400E) }
    static var yellow900: Color { Color(argb: 0x78350F) }

    static var green50: Color  { Color(argb: 0xF0FDF4) }
    static var green100: Color { Color(argb: 0xDCFCE7) }
    static var green200: Color { Color(argb: 0xBBF7D0) }
    static var green300: Color { Color(argb: 0x86EFAC) }
    static var green400: Color { Color(argb: 0x4ADE80) }
    static var green500: Color { Color(argb: 0x22C55E) }
    static var green600: Color { Color(argb: 0x16A34A) }
    static var green700: Color { Color(argb: 0x15803D) }
    static var green800: Color { Color(argb: 0x166534) }
    static var green900: Color { Color(argb: 0x14532D) }
}
