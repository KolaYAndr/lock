package org.homesharing.lockbox.presentation_v2.uikit.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Shape

@Immutable
internal data class HomesharingShape(
    val roundedCornerShape12: Shape,
    val roundedCornerShape16: Shape,
    val roundedCornerShape18: Shape,
    val roundedCornerShape62top: Shape,
    val roundedCornerShape64: Shape,
    val roundedCornerShape999: Shape,
)

internal val LocalHomesharingShape = staticCompositionLocalOf {
    HomesharingShape(
        roundedCornerShape12 = roundedCornerShape12,
        roundedCornerShape16 = roundedCornerShape16,
        roundedCornerShape18 = roundedCornerShape18,
        roundedCornerShape62top = roundedCornerShape62top,
        roundedCornerShape64 = roundedCornerShape64,
        roundedCornerShape999 = roundedCornerShape999,
    )
}
