//
//  LockboxButtonStyle.swift
//  iosApp
//
//  Created by Николай on 04.01.2026.
//  Copyright © 2026 LockBox. All rights reserved.
//

import SwiftUI

struct LockboxProminentButtonStyle: ButtonStyle {
    let containerColor: Color
    let contentColor: Color

    func makeBody(configuration: Configuration) -> Body {
        Body(
            configuration: configuration,
            containerColor: containerColor,
            contentColor: contentColor
        )
    }

    struct Body: View {
        let configuration: Configuration
        let containerColor: Color
        let contentColor: Color

        @Environment(\.isEnabled) private var isEnabled

        var body: some View {
            configuration.label
                .font(.headlineLarge)
                .foregroundStyle(currentContentColor)
                .padding(.all, 10)
                .background(currentBackground)
                .clipShape(RoundedRectangle(cornerRadius: 8, style: .continuous))
                .opacity(configuration.isPressed ? 0.9 : 1.0)
                .scaleEffect(configuration.isPressed ? 0.98 : 1.0)
                .animation(.easeOut(duration: 0.12), value: configuration.isPressed)
        }

        private var currentBackground: Color {
            isEnabled ? containerColor : .grey800
        }

        private var currentContentColor: Color {
            isEnabled ? contentColor : .grey700
        }
    }
}

extension ButtonStyle where Self == LockboxProminentButtonStyle {

    static var greenBackground: Self {
        .init(
            containerColor: .green500,
            contentColor: .grey900
        )
    }

    static var lighterGrey: Self {
        .init(
            containerColor: .grey800,
            contentColor: .grey100
        )
    }

    static var grey: Self {
        .init(
            containerColor: .grey900,
            contentColor: .grey100
        )
    }

    static var darkerGrey: Self {
        .init(
            containerColor: .grey950,
            contentColor: .grey100
        )
    }

    static var redGreyBackground: Self {
        .init(
            containerColor: .grey900,
            contentColor: .red600
        )
    }
    
    static var darkRed: Self {
        .init(
            containerColor: .grey950,
            contentColor: .red600
        )
    }

    static var redBackground: Self {
        .init(
            containerColor: .red600,
            contentColor: .grey100
        )
    }
}

#Preview {
    VStack (spacing: 4){
        Button {} label : { Text("TEXT") }.buttonStyle(.greenBackground)
        Button {} label : { Text("TEXT") }.buttonStyle(.lighterGrey)
        Button {} label : { Text("TEXT") }.buttonStyle(.grey)
        Button {} label : { Text("TEXT") }.buttonStyle(.darkerGrey)
        Button {} label : { Text("TEXT") }.buttonStyle(.darkRed)
        Button {} label : { Text("TEXT") }.buttonStyle(.redBackground)
    }
}
