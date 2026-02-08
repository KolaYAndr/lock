//
//  LockboxButtonStyle.swift
//  iosApp
//
//  Created by Николай on 04.01.2026.
//  Copyright © 2026 LockBox. All rights reserved.
//

import SwiftUI

struct LockboxProminentButtonStyle: ButtonStyle {

    let tint: Color
    let disabledTint: Color

    func makeBody(configuration: Configuration) -> Body {
        Body(
            configuration: configuration,
            tint: tint,
            disabledTint: disabledTint,
            )
    }

    struct Body: View {
        let configuration: Configuration
        let tint: Color
        let disabledTint: Color

        @Environment(\.isEnabled)
        private var isEnabled

        var body: some View {
            configuration
                .buttonStyle(.borderedProminent)
                .tint(isEnabled ? tint : disabledTint)
                .buttonBorderShape(.roundedRectangle(radius: 8))
        }
    }
}

extension ButtonStyle where Self == LockboxProminentButtonStyle {

    static var greenBackground: LockboxProminentButtonStyle {
        LockboxProminentButtonStyle(
            tint: .green500,
            disabledTint: .grey800
        )
    }

    static var lighterGrey: LockboxProminentButtonStyle {
        LockboxProminentButtonStyle(
            tint: .grey800,
            disabledTint: .grey800
        )
    }

    static var grey: LockboxProminentButtonStyle {
        LockboxProminentButtonStyle(
            tint: .grey900,
            disabledTint: .grey800
        )
    }

    static var darkerGrey: LockboxProminentButtonStyle {
        LockboxProminentButtonStyle(
            tint: .grey950,
            disabledTint: .grey800
        )
    }

    static var red: LockboxProminentButtonStyle {
        LockboxProminentButtonStyle(
            tint: .grey900,
            disabledTint: .grey800
        )
    }

    static var redBackground: LockboxProminentButtonStyle {
        LockboxProminentButtonStyle(
            tint: .red600,
            disabledTint: .grey800
        )
    }
}

