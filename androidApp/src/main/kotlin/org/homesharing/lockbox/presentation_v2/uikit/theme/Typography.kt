package org.homesharing.lockbox.presentation_v2.uikit.theme

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import org.homesharing.lockbox.R

internal val inter = Font(R.font.inter_variable)
internal val interItalic = Font(R.font.inter_italic_variable)

internal val interFamily = FontFamily(
    inter,
    interItalic,
)

internal val TitleLarge = TextStyle(
    fontFamily = interFamily,
    fontWeight = FontWeight.W600,
    fontSize = 24.sp,
    lineHeight = 36.sp,
)

internal val TitleSmall = TextStyle(
    fontFamily = interFamily,
    fontWeight = FontWeight.W600,
    fontSize = 18.sp,
    lineHeight = 28.sp,
)

internal val BodyMedium = TextStyle(
    fontFamily = interFamily,
    fontWeight = FontWeight.W400,
    fontSize = 16.sp,
    lineHeight = 24.sp,
)

internal val BodySmall = TextStyle(
    fontFamily = interFamily,
    fontWeight = FontWeight.W500,
    fontSize = 14.sp,
    lineHeight = 20.sp,
)

internal val BodyXSmall = TextStyle(
    fontFamily = interFamily,
    fontWeight = FontWeight.W500,
    fontSize = 12.sp,
    lineHeight = 16.sp,
)