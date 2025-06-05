package org.homesharing.lockbox.presentation.general.composables.inputs

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import org.homesharing.lockbox.presentation.general.composables.basic.BasicInput
import org.homesharing.lockbox.presentation.general.theme.LockBoxTheme

@Composable
internal fun LockBoxPropertyInput(
    value: String,
    onValueChange: (String) -> Unit,
    onDone: () -> Unit,
    placeholderText: String,
    modifier: Modifier = Modifier
) {
    BasicInput(
        value = value,
        onValueChange = onValueChange,
        enabled = true,
        placeholderText = placeholderText,
        onDone = onDone,
        modifier = modifier
    )
}

@Preview
@Composable
private fun PreviewPropertyInput() {
    LockBoxTheme {
        LockBoxPropertyInput(
            value = "Dom",
            onValueChange = {},
            onDone = {},
            placeholderText = "",
            modifier = Modifier.fillMaxWidth()
        )
    }
}
