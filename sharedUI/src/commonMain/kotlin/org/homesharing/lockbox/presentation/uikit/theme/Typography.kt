package org.homesharing.lockbox.presentation.uikit.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import lockbox.sharedui.generated.resources.Res
import lockbox.sharedui.generated.resources.inter_italic_variable
import lockbox.sharedui.generated.resources.inter_variable
import org.jetbrains.compose.resources.Font

@Composable
internal fun interFamily() = FontFamily(
    Font(Res.font.inter_variable),
    Font(Res.font.inter_italic_variable),
)

@Composable
internal fun homesharingTypography(): HomesharingTypography {
    val inter = interFamily()
    return HomesharingTypography(
        titleLarge = TitleLarge.copy(fontFamily = inter),
        titleMedium = TitleMedium.copy(fontFamily = inter),
        titleSmall = TitleSmall.copy(fontFamily = inter),
        bodyMedium = BodyMedium.copy(fontFamily = inter),
        bodySmall = BodySmall.copy(fontFamily = inter),
        bodyXSmall = BodyXSmall.copy(fontFamily = inter),
    )
}

internal val TitleLarge = TextStyle(
    fontWeight = FontWeight.W600,
    fontSize = 24.sp,
    lineHeight = 36.sp,
)

internal val TitleMedium = TextStyle(
    fontWeight = FontWeight.W600,
    fontSize = 20.sp,
    lineHeight = 32.sp,
)

internal val TitleSmall = TextStyle(
    fontWeight = FontWeight.W600,
    fontSize = 18.sp,
    lineHeight = 28.sp,
)

internal val BodyMedium = TextStyle(
    fontWeight = FontWeight.W400,
    fontSize = 16.sp,
    lineHeight = 24.sp,
)

internal val BodySmall = TextStyle(
    fontWeight = FontWeight.W500,
    fontSize = 14.sp,
    lineHeight = 20.sp,
)

internal val BodyXSmall = TextStyle(
    fontWeight = FontWeight.W500,
    fontSize = 12.sp,
    lineHeight = 16.sp,
)
