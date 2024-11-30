package presentation.theme

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import org.homesharing.lockbox.R

val inter = Font(R.font.inter)
val interBlack = Font(R.font.inter_black)
val interBlackItalic = Font(R.font.inter_black_italic)
val interBold = Font(R.font.inter_bold)
val interBoldItalic = Font(R.font.inter_bold_italic)
val interExtraBold = Font(R.font.inter_extra_bold)
val interExtraBoldItalic = Font(R.font.inter_extra_bold_italic)
val interExtraLightItalic = Font(R.font.inter_extra_light_italic)
val interItalic = Font(R.font.inter_italic)
val interLightItalic = Font(R.font.inter_light_italic)
val interMediumItalic = Font(R.font.inter_medium_italic)
val interSemiBoldItalic = Font(R.font.inter_semi_bold_italic)
val interThinItalic = Font(R.font.inter_thin_italic)

val interFamily = FontFamily(
    inter,
    interBlack,
    interBlackItalic,
    interBold,
    interBoldItalic,
    interExtraBold,
    interExtraBoldItalic,
    interExtraLightItalic,
    interItalic,
    interLightItalic,
    interMediumItalic,
    interSemiBoldItalic,
    interThinItalic,
)

val TitleLarge = TextStyle(
    fontFamily = interFamily,
    fontWeight = FontWeight.W500,
    fontSize = 24.sp,
    lineHeight = 28.sp,
    )

val TitleMedium = TextStyle(
    fontFamily = interFamily,
    fontWeight = FontWeight.W500,
    fontSize = 20.sp,
    lineHeight = 26.sp,
)

val TitleSmall = TextStyle(
    fontFamily = interFamily,
    fontWeight = FontWeight.W500,
    fontSize = 17.sp,
    lineHeight = 22.sp,
)

val HeadlineLarge = TextStyle(
    fontFamily = interFamily,
    fontWeight = FontWeight.W500,
    fontSize = 16.sp,
    lineHeight = 20.sp,
)

val HeadlineMedium = TextStyle(
    fontFamily = interFamily,
    fontWeight = FontWeight.W500,
    fontSize = 15.sp,
    lineHeight = 20.sp,
)

val BodyLarge = TextStyle(
    fontFamily = interFamily,
    fontWeight = FontWeight.W400,
    fontSize = 16.sp,
    lineHeight = 20.sp,
)

val BodyMedium = TextStyle(
    fontFamily = interFamily,
    fontWeight = FontWeight.W400,
    fontSize = 15.sp,
    lineHeight = 20.sp,
)

val SubHeadlineLarge = TextStyle(
    fontFamily = interFamily,
    fontWeight = FontWeight.W500,
    fontSize = 14.sp,
    lineHeight = 18.sp,
)

val SubHeadlineMedium = TextStyle(
    fontFamily = interFamily,
    fontWeight = FontWeight.W400,
    fontSize = 14.sp,
    lineHeight = 18.sp,
)

val Footnote = TextStyle(
    fontFamily = interFamily,
    fontWeight = FontWeight.W400,
    fontSize = 13.sp,
    lineHeight = 18.sp,
)

val CaptionLarge = TextStyle(
    fontFamily = interFamily,
    fontWeight = FontWeight.W400,
    fontSize = 12.sp,
    lineHeight = 16.sp,
)

val CaptionMedium = TextStyle(
    fontFamily = interFamily,
    fontWeight = FontWeight.W400,
    fontSize = 11.sp,
    lineHeight = 14.sp,
)

val CaptionSmall = TextStyle(
    fontFamily = interFamily,
    fontWeight = FontWeight.W400,
    fontSize = 9.sp,
    lineHeight = 12.sp,
)
