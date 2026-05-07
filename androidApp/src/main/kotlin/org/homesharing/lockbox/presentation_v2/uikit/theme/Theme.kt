package org.homesharing.lockbox.presentation_v2.uikit.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

@Composable
internal fun HomesharingTheme(
    content: @Composable () -> Unit,
) {
    val homesharingColor = HomesharingColor(
        white = WHITE,
        black = BLACK,
        red = RED
    )

    val homesharingTypography = HomesharingTypography(
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

    val homeSharingShape = HomesharingShape(
        roundedCornerShape16 = roundedCornerShape16,
        roundedCornerShape18 = roundedCornerShape18,
        roundedCornerShape62top = roundedCornerShape62top,
        roundedCornerShape64 = roundedCornerShape64,
    )

    CompositionLocalProvider(
        LocalHomesharingColor provides homesharingColor,
        LocalHomesharingTypography provides homesharingTypography,
        LocalHomesharingShape provides homeSharingShape,
        content = content
    )
}

internal object HomesharingTheme {
    val colors: HomesharingColor
        @Composable get() = LocalHomesharingColor.current

    val typography: HomesharingTypography
        @Composable get() = LocalHomesharingTypography.current

    val shape: HomesharingShape
        @Composable get() = LocalHomesharingShape.current
}
