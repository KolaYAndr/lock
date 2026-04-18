package org.homesharing.lockbox.presentation.uikit.composables.core.inputs

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import org.homesharing.lockbox.R
import org.homesharing.lockbox.presentation.uikit.theme.LockBoxTheme

@Composable
internal fun LockboxDateInput(
    date: String,
    onFieldClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    LockboxInput(
        modifier = modifier,
        value = date,
        onValueChange = {},
        enabled = false,
        placeholderText = stringResource(R.string.choose_date),
        leadingPainter = painterResource(R.drawable.calendar),
        onFieldClick = onFieldClick
    )
}

@Preview
@Composable
private fun LockboxDateInputPreview() {
    LockBoxTheme {
        LockboxDateInput(
            date = "",
            onFieldClick = {},
            modifier = Modifier.fillMaxWidth()
        )
    }
}
