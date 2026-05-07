package org.homesharing.lockbox.presentation_v2.uikit.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle

@Immutable
internal data class HomesharingTypography(
    val titleLarge: TextStyle,
    val titleMedium: TextStyle,
    val titleSmall: TextStyle,
    val headlineLarge: TextStyle,
    val headlineMedium: TextStyle,
    val bodyLarge: TextStyle,
    val bodyMedium: TextStyle,
    val subHeadlineLarge: TextStyle,
    val subHeadlineMedium: TextStyle,
    val footnote: TextStyle,
    val captionLarge: TextStyle,
    val captionMedium: TextStyle,
    val captionSmall: TextStyle,
)

internal val LocalHomesharingTypography = staticCompositionLocalOf {
    HomesharingTypography(
        TitleLarge,
        TitleMedium,
        TitleSmall,
        HeadlineLarge,
        HeadlineMedium,
        BodyLarge,
        BodyMedium,
        SubHeadlineLarge,
        SubHeadlineMedium,
        Footnote,
        CaptionLarge,
        CaptionMedium,
        CaptionSmall
    )
}
