package presentation.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Shape

@Immutable
data class LockBoxShape(
    val roundedCornerShape8: Shape,
    val roundedCornerShape10: Shape,
    val roundedCornerShape24: Shape,
    val roundedCornerShape999: Shape,
    val circleShape: Shape,
)

val LocalLockBoxShape = staticCompositionLocalOf {
    LockBoxShape(
        roundedCornerShape8 = roundedCornerShape8,
        roundedCornerShape10 = roundedCornerShape10,
        roundedCornerShape24 = roundedCornerShape24,
        roundedCornerShape999 = roundedCornerShape999,
        circleShape = circleShape,
    )
}