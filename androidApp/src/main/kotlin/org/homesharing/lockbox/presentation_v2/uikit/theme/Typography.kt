package org.homesharing.lockbox.presentation_v2.uikit.theme

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import org.homesharing.lockbox.R

internal val inter = Font(R.font.inter)
internal val interBlack = Font(R.font.inter_black)

internal val interFamily = FontFamily(
    inter,
    interBlack,
)

internal val TitleLarge = TextStyle(
    fontFamily = interFamily,
    fontWeight = FontWeight.W500,
    fontSize = 24.sp,
    lineHeight = 28.sp,
    )

internal val TitleMedium = TextStyle(
    fontFamily = interFamily,
    fontWeight = FontWeight.W500,
    fontSize = 20.sp,
    lineHeight = 26.sp,
)

internal val TitleSmall = TextStyle(
    fontFamily = interFamily,
    fontWeight = FontWeight.W500,
    fontSize = 17.sp,
    lineHeight = 22.sp,
)

internal val HeadlineLarge = TextStyle(
    fontFamily = interFamily,
    fontWeight = FontWeight.W500,
    fontSize = 16.sp,
    lineHeight = 20.sp,
)

internal val HeadlineMedium = TextStyle(
    fontFamily = interFamily,
    fontWeight = FontWeight.W500,
    fontSize = 15.sp,
    lineHeight = 20.sp,
)

internal val BodyLarge = TextStyle(
    fontFamily = interFamily,
    fontWeight = FontWeight.W400,
    fontSize = 16.sp,
    lineHeight = 20.sp,
)

internal val BodyMedium = TextStyle(
    fontFamily = interFamily,
    fontWeight = FontWeight.W400,
    fontSize = 15.sp,
    lineHeight = 20.sp,
)

internal val SubHeadlineLarge = TextStyle(
    fontFamily = interFamily,
    fontWeight = FontWeight.W500,
    fontSize = 14.sp,
    lineHeight = 18.sp,
)

internal val SubHeadlineMedium = TextStyle(
    fontFamily = interFamily,
    fontWeight = FontWeight.W400,
    fontSize = 14.sp,
    lineHeight = 18.sp,
)

internal val Footnote = TextStyle(
    fontFamily = interFamily,
    fontWeight = FontWeight.W400,
    fontSize = 13.sp,
    lineHeight = 18.sp,
)

internal val CaptionLarge = TextStyle(
    fontFamily = interFamily,
    fontWeight = FontWeight.W400,
    fontSize = 12.sp,
    lineHeight = 16.sp,
)

internal val CaptionMedium = TextStyle(
    fontFamily = interFamily,
    fontWeight = FontWeight.W400,
    fontSize = 11.sp,
    lineHeight = 14.sp,
)

internal val CaptionSmall = TextStyle(
    fontFamily = interFamily,
    fontWeight = FontWeight.W400,
    fontSize = 9.sp,
    lineHeight = 12.sp,
)
