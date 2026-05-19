package org.homesharing.lockbox.presentation.uikit.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

@Immutable
internal data class HomesharingColor(
    val white: Color,
    val black: Color,
    val red: Color,
)

internal val LocalHomesharingColor = staticCompositionLocalOf {
    HomesharingColor(
        white = WHITE,
        black = BLACK,
        red = RED,
    )
}
