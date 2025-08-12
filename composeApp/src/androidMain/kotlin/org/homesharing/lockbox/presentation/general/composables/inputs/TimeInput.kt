package org.homesharing.lockbox.presentation.general.composables.inputs

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import org.homesharing.lockbox.R
import org.homesharing.lockbox.presentation.general.composables.basic.BasicInput
import org.homesharing.lockbox.presentation.general.theme.LockBoxTheme

@Composable
internal fun TimeInput(time: String, onFieldClick: () -> Unit, modifier: Modifier = Modifier) {
    BasicInput(
        value = time,
        onValueChange = {},
        enabled = false,
        onFieldClick = onFieldClick,
        placeholderText = stringResource(R.string.choose_time),
        singleLine = true,
        modifier = modifier
    )
}

@Preview
@Composable
private fun PreviewTimeInput() {
    LockBoxTheme {
        TimeInput(time = "12:00", onFieldClick = {})
    }
}
