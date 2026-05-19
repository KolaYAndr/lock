//
//  LockboxTypography.swift
//  iosApp
//
//  Created by Николай on 21.09.2025.
//  Copyright © 2025 Lockbox. All rights reserved.
//

import SwiftUI

extension Font {
    static let titleLarge = Font.custom("Inter-Black.ttf", size: 24)
    static let titleMedium = Font.custom("Inter-Black.ttf", size: 20)
    static let titleSmall = Font.custom("Inter-Black.ttf", size: 17)
    static let headlineLarge = Font.custom("Inter-Black.ttf", size: 16)
    static let headlineMedium = Font.custom("Inter-Black.ttf", size: 15)
    static let bodyLarge = Font.custom("Inter.ttf", size: 16)
    static let bodyMedium = Font.custom("Inter.ttf", size: 15)
    static let subHeadlineLarge = Font.custom("Inter-Black.ttf", size: 14)
    static let subHeadlineMedium = Font.custom("Inter.ttf", size: 14)
    static let footnote = Font.custom("Inter.ttf", size: 13)
    static let captionLarge = Font.custom("Inter.ttf", size: 12)
    static let captionMedium = Font.custom("Inter.ttf", size: 11)
    static let captionSmall = Font.custom("Inter.ttf", size: 9)
}

extension CGFloat {
    static let spacingLarge: CGFloat = 6
    static let spacingMedium: CGFloat = 5
    static let spacingSmall: CGFloat = 4
    static let spacingExtraSmall: CGFloat = 3
}

struct TitleLarge: ViewModifier {
    func body(content: Content) -> some View {
        content
            .font(.titleLarge)
            .lineSpacing(.spacingSmall)
    }
}

struct TitleMedium: ViewModifier {
    func body(content: Content) -> some View {
        content
            .font(.titleMedium)
            .lineSpacing(.spacingMedium)
    }
}

struct TitleSmall: ViewModifier {
    func body(content: Content) -> some View {
        content
            .font(.titleSmall)
            .lineSpacing(.spacingMedium)
    }
}

struct HeadlineLarge: ViewModifier {
    func body(content: Content) -> some View {
        content
            .font(.headlineLarge)
            .lineSpacing(.spacingSmall)
    }
}

struct HeadlineMedium: ViewModifier {
    func body(content: Content) -> some View {
        content
            .font(.headlineMedium)
            .lineSpacing(.spacingMedium)
    }
}

struct BodyLarge: ViewModifier {
    func body(content: Content) -> some View {
        content
            .font(.bodyLarge)
            .lineSpacing(.spacingSmall)
    }
}

struct BodyMedium: ViewModifier {
    func body(content: Content) -> some View {
        content
            .font(.bodyMedium)
            .lineSpacing(.spacingMedium)
    }
}

struct SubHeadlineLarge: ViewModifier {
    func body(content: Content) -> some View {
        content
            .font(.subHeadlineLarge)
            .lineSpacing(.spacingSmall)
    }
}

struct SubHeadlineMedium: ViewModifier {
    func body(content: Content) -> some View {
        content
            .font(.subHeadlineMedium)
            .lineSpacing(.spacingSmall)
    }
}

struct Footnote: ViewModifier {
    func body(content: Content) -> some View {
        content
            .font(.footnote)
            .lineSpacing(.spacingMedium)
    }
}


struct CaptionLarge: ViewModifier {
    func body(content: Content) -> some View {
        content
            .font(.captionLarge)
            .lineSpacing(.spacingSmall)
    }
}

struct CaptionMedium: ViewModifier {
    func body(content: Content) -> some View {
        content
            .font(.captionMedium)
            .lineSpacing(.spacingExtraSmall)
    }
}

struct CaptionSmall: ViewModifier {
    func body(content: Content) -> some View {
        content
            .font(.captionSmall)
            .lineSpacing(.spacingExtraSmall)
    }
}

extension View {
    func titleLarge() -> some View {
        modifier(TitleLarge())
    }

    func titleMedium() -> some View {
        modifier(TitleMedium())
    }

    func titleSmall() -> some View {
        modifier(TitleSmall())
    }

    func headlineLarge() -> some View {
        modifier(HeadlineLarge())
    }

    func headlineMedium() -> some View {
        modifier(HeadlineMedium())
    }

    func bodyLarge() -> some View {
        modifier(BodyLarge())
    }

    func bodyMedium() -> some View {
        modifier(BodyMedium())
    }

    func subHeadlineLarge() -> some View {
        modifier(SubHeadlineLarge())
    }

    func subHeadlineMedium() -> some View {
        modifier(SubHeadlineMedium())
    }

    func footnote() -> some View {
        modifier(Footnote())
    }

    func captionLarge() -> some View {
        modifier(CaptionLarge())
    }

    func captionMedium() -> some View {
        modifier(CaptionMedium())
    }

    func captionSmall() -> some View {
        modifier(CaptionSmall())
    }
}
