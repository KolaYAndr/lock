package org.homesharing.lockbox.presentation.uikit.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember

@Composable
internal fun HomesharingTheme(
    content: @Composable () -> Unit,
) {
    val homesharingColor = remember {
        HomesharingColor(
            white = WHITE,
            black = BLACK,
            red = RED
        )
    }

    val homesharingTypography = homesharingTypography()

    val homeSharingShape = remember {
        HomesharingShape(
            roundedCornerShape12 = roundedCornerShape12,
            roundedCornerShape16 = roundedCornerShape16,
            roundedCornerShape18 = roundedCornerShape18,
            roundedCornerShape24 = roundedCornerShape24,
            roundedCornerShape62top = roundedCornerShape62top,
            roundedCornerShape64 = roundedCornerShape64,
            roundedCornerShape999 = roundedCornerShape999,
        )
    }

    val materialColorScheme = remember {
        lightColorScheme(
            background = WHITE,
            surface = WHITE,
            onBackground = BLACK,
            onSurface = BLACK,
            error = RED,
        )
    }

    MaterialTheme(colorScheme = materialColorScheme) {
        CompositionLocalProvider(
            LocalHomesharingColor provides homesharingColor,
            LocalHomesharingTypography provides homesharingTypography,
            LocalHomesharingShape provides homeSharingShape,
            content = content
        )
    }
}

internal object HomesharingTheme {
    val colors: HomesharingColor
        @Composable get() = LocalHomesharingColor.current

    val typography: HomesharingTypography
        @Composable get() = LocalHomesharingTypography.current

    val shape: HomesharingShape
        @Composable get() = LocalHomesharingShape.current
}
