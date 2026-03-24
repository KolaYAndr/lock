package org.homesharing.lockbox.presentation.uikit.composables.core.buttons

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.homesharing.lockbox.presentation.uikit.theme.LockBoxTheme

@Composable
internal fun LockboxButton(
    onClick: () -> Unit,
    text: String,
    paddingValues: PaddingValues = PaddingValues(8.dp),
    style: ButtonColors = LockboxButtonStyle.greenBackground(),
    enabled: Boolean = true,
    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier,
) {
    Button(
        onClick = onClick,
        shape = LockBoxTheme.shape.roundedCornerShape8,
        colors = style,
        contentPadding = paddingValues,
        enabled = enabled,
        modifier = modifier
    ) {
        Text(
            text = text,
            style = LockBoxTheme.typography.headlineLarge,
            textAlign = TextAlign.Center
        )
    }
}

internal object LockboxButtonStyle {
    @Composable
    fun greenBackground(): ButtonColors = ButtonDefaults.buttonColors(
        containerColor = LockBoxTheme.colors.green500,
        contentColor = LockBoxTheme.colors.grey900,
        disabledContentColor = LockBoxTheme.colors.grey700,
        disabledContainerColor = LockBoxTheme.colors.grey800
    )

    @Composable
    fun lighterGrey(): ButtonColors = ButtonDefaults.buttonColors(
        containerColor = LockBoxTheme.colors.grey800,
        contentColor = LockBoxTheme.colors.grey100,
        disabledContainerColor = LockBoxTheme.colors.grey800,
        disabledContentColor = LockBoxTheme.colors.grey700
    )

    @Composable
    fun grey(): ButtonColors = ButtonDefaults.buttonColors(
        containerColor = LockBoxTheme.colors.grey900,
        contentColor = LockBoxTheme.colors.grey100,
        disabledContentColor = LockBoxTheme.colors.grey700,
        disabledContainerColor = LockBoxTheme.colors.grey800
    )

    @Composable
    fun darkerGrey(): ButtonColors = ButtonDefaults.buttonColors(
        containerColor = LockBoxTheme.colors.grey950,
        contentColor = LockBoxTheme.colors.grey100,
        disabledContentColor = LockBoxTheme.colors.grey700,
        disabledContainerColor = LockBoxTheme.colors.grey800
    )

    @Composable
    fun red(): ButtonColors = ButtonDefaults.buttonColors(
        contentColor = LockBoxTheme.colors.red600,
        containerColor = LockBoxTheme.colors.grey900,
        disabledContentColor = LockBoxTheme.colors.grey700,
        disabledContainerColor = LockBoxTheme.colors.grey800
    )

    @Composable
    fun redBackGround(): ButtonColors = ButtonDefaults.buttonColors(
        containerColor = LockBoxTheme.colors.red600,
        contentColor = LockBoxTheme.colors.grey100,
        disabledContentColor = LockBoxTheme.colors.grey700,
        disabledContainerColor = LockBoxTheme.colors.grey800
    )
}

@Composable
@Preview
private fun PreviewGreenButton() {
    LockBoxTheme {
        LockboxButton(
            onClick = { },
            text = "Voyti",
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
        )
    }
}

@Composable
@Preview
private fun PreviewOuterRedButton() {
    LockBoxTheme {
        LockboxButton(
            onClick = { },
            text = "Voyti",
            style = LockboxButtonStyle.redBackGround(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
        )
    }
}

@Composable
@Preview
private fun PreviewRedButton() {
    LockBoxTheme {
        LockboxButton(
            onClick = { },
            text = "Voyti",
            style = LockboxButtonStyle.red(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
        )
    }
}

@Composable
@Preview
private fun PreviewLighterGreyButton() {
    LockBoxTheme {
        LockboxButton(
            onClick = { },
            text = "Voyti",
            style = LockboxButtonStyle.lighterGrey(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
        )
    }
}

@Composable
@Preview
private fun PreviewGreyButton() {
    LockBoxTheme {
        LockboxButton(
            onClick = { },
            text = "Voyti",
            style = LockboxButtonStyle.grey(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
        )
    }
}

@Composable
@Preview
private fun PreviewDarkerGreyButton() {
    LockBoxTheme {
        LockboxButton(
            onClick = { },
            text = "Voyti",
            style = LockboxButtonStyle.darkerGrey(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
        )
    }
}
