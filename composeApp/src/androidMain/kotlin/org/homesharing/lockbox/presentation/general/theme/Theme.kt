package org.homesharing.lockbox.presentation.general.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import org.homesharing.lockbox.presentation.general.composables.Green100
import org.homesharing.lockbox.presentation.general.composables.Green200
import org.homesharing.lockbox.presentation.general.composables.Green300
import org.homesharing.lockbox.presentation.general.composables.Green400
import org.homesharing.lockbox.presentation.general.composables.Green50
import org.homesharing.lockbox.presentation.general.composables.Green500
import org.homesharing.lockbox.presentation.general.composables.Green600
import org.homesharing.lockbox.presentation.general.composables.Green700
import org.homesharing.lockbox.presentation.general.composables.Green800
import org.homesharing.lockbox.presentation.general.composables.Green900
import org.homesharing.lockbox.presentation.general.composables.Grey100
import org.homesharing.lockbox.presentation.general.composables.Grey1000
import org.homesharing.lockbox.presentation.general.composables.Grey200
import org.homesharing.lockbox.presentation.general.composables.Grey300
import org.homesharing.lockbox.presentation.general.composables.Grey400
import org.homesharing.lockbox.presentation.general.composables.Grey50
import org.homesharing.lockbox.presentation.general.composables.Grey500
import org.homesharing.lockbox.presentation.general.composables.Grey600
import org.homesharing.lockbox.presentation.general.composables.Grey700
import org.homesharing.lockbox.presentation.general.composables.Grey800
import org.homesharing.lockbox.presentation.general.composables.Grey900
import org.homesharing.lockbox.presentation.general.composables.Grey950
import org.homesharing.lockbox.presentation.general.composables.Red100
import org.homesharing.lockbox.presentation.general.composables.Red200
import org.homesharing.lockbox.presentation.general.composables.Red300
import org.homesharing.lockbox.presentation.general.composables.Red400
import org.homesharing.lockbox.presentation.general.composables.Red50
import org.homesharing.lockbox.presentation.general.composables.Red500
import org.homesharing.lockbox.presentation.general.composables.Red600
import org.homesharing.lockbox.presentation.general.composables.Red700
import org.homesharing.lockbox.presentation.general.composables.Red800
import org.homesharing.lockbox.presentation.general.composables.Red900
import org.homesharing.lockbox.presentation.general.composables.White
import org.homesharing.lockbox.presentation.general.composables.Yellow100
import org.homesharing.lockbox.presentation.general.composables.Yellow200
import org.homesharing.lockbox.presentation.general.composables.Yellow300
import org.homesharing.lockbox.presentation.general.composables.Yellow400
import org.homesharing.lockbox.presentation.general.composables.Yellow50
import org.homesharing.lockbox.presentation.general.composables.Yellow500
import org.homesharing.lockbox.presentation.general.composables.Yellow600
import org.homesharing.lockbox.presentation.general.composables.Yellow700
import org.homesharing.lockbox.presentation.general.composables.Yellow800
import org.homesharing.lockbox.presentation.general.composables.Yellow900

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
        Grey1000,
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
        roundedCornerShape12 = roundedCornerShape12,
        roundedCornerShape16 = roundedCornerShape16,
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