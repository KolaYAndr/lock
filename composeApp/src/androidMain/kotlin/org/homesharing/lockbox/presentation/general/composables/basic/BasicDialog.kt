package org.homesharing.lockbox.presentation.general.composables.basic

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import org.homesharing.lockbox.R
import org.homesharing.lockbox.presentation.general.theme.LockBoxTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun BasicDialog(
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit,
) {
    BasicAlertDialog(
        onDismissRequest = onDismiss::invoke,
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true,
            usePlatformDefaultWidth = false
        ),
        modifier = modifier
            .padding(horizontal = 32.dp)
            .clip(LockBoxTheme.shape.roundedCornerShape8)
            .background(color = LockBoxTheme.colors.grey900)
            .border(
                width = 0.5.dp,
                color = LockBoxTheme.colors.grey800,
                shape = LockBoxTheme.shape.roundedCornerShape8
            )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            content()
        }
    }
}

@Composable
internal fun BasicDialogContent(
    title: String,
    subtitle: String? = null,
    onDismiss: () -> Unit,
    onDangerButtonAction: () -> Unit,
    dangerButtonText: String,
    dangerButtonStyle: ButtonColors = BasicButtonStyle.redBackGround()
) {
    Text(
        text = title,
        style = LockBoxTheme.typography.titleMedium,
        color = LockBoxTheme.colors.grey100
    )

    subtitle?.let { sub ->
        Text(
            text = sub,
            style = LockBoxTheme.typography.bodyMedium,
            color = LockBoxTheme.colors.grey500,
            modifier = Modifier.padding(top = 8.dp)
        )
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.End)
    ) {
        BasicButton(
            onClick = onDismiss,
            text = stringResource(R.string.cancel),
            paddingValues = PaddingValues(10.dp),
            style = BasicButtonStyle.grey()
        )

        BasicButton(
            onClick = onDangerButtonAction,
            text = dangerButtonText,
            paddingValues = PaddingValues(10.dp),
            style = dangerButtonStyle
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewDialog() {
    LockBoxTheme {
        BasicDialog(
            onDismiss = {},
            modifier = Modifier.padding(12.dp)
        ) {
            BasicDialogContent(
                title = "Title",
                subtitle = "Subtitle",
                onDismiss = {},
                dangerButtonText = "Danger",
                onDangerButtonAction = {}
            )
        }
    }
}
