package presentation.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

@Composable
fun LockBoxTheme(
    content: @Composable () -> Unit,
) {
    val lockBoxColors = LockBoxColors(
        White,
        Grey50,
        Grey100,
        Grey200,
        Grey300,
        Grey400,
        Grey500,
        Grey600,
        Grey700,
        Grey800,
        Grey900,
        Grey950,
        Red50,
        Red100,
        Red200,
        Red300,
        Red400,
        Red500,
        Red600,
        Red700,
        Red800,
        Red900,
        Yellow50,
        Yellow100,
        Yellow200,
        Yellow300,
        Yellow400,
        Yellow500,
        Yellow600,
        Yellow700,
        Yellow800,
        Yellow900,
        Green50,
        Green100,
        Green200,
        Green300,
        Green400,
        Green500,
        Green600,
        Green700,
        Green800,
        Green900,
    )

    val lockBoxTypography = LockBoxTypography(
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
        CaptionSmall,
    )

    val lockBoxShape = LockBoxShape(
        roundedCornerShape8 = roundedCornerShape8,
        roundedCornerShape10 = roundedCornerShape10,
        roundedCornerShape24 = roundedCornerShape24,
        roundedCornerShape999 = roundedCornerShape999,
        circleShape = circleShape,
    )

    CompositionLocalProvider(
        LocalLockBoxColors provides lockBoxColors,
        LocalLockBoxTypography provides lockBoxTypography,
        LocalLockBoxShape provides lockBoxShape,
        content = content
    )
}

object LockBoxTheme {
    val colors: LockBoxColors
        @Composable get() = LocalLockBoxColors.current

    val typography: LockBoxTypography
        @Composable get() = LocalLockBoxTypography.current

    val shape: LockBoxShape
        @Composable get() = LocalLockBoxShape.current
}