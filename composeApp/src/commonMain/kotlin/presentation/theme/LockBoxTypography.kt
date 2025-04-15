package presentation.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle

@Immutable
data class LockBoxTypography(
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

val LocalLockBoxTypography = staticCompositionLocalOf {
    LockBoxTypography(
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
