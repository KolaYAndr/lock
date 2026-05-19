package org.homesharing.lockbox.presentation.uikit.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle

@Immutable
internal data class HomesharingTypography(
    val titleLarge: TextStyle,
    val titleSmall: TextStyle,
    val bodyMedium: TextStyle,
    val bodySmall: TextStyle,
    val bodyXSmall: TextStyle,
)

internal val LocalHomesharingTypography = staticCompositionLocalOf {
    HomesharingTypography(
        TitleLarge,
        TitleSmall,
        BodyMedium,
        BodySmall,
        BodyXSmall,
    )
}
