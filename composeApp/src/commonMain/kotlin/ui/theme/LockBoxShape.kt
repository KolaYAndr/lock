package ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Shape

@Immutable
data class LockBoxShape(
    val default: Shape
)

val LocalLockBoxShape = staticCompositionLocalOf {
    LockBoxShape(
        default = roundedCornerShape
    )
}