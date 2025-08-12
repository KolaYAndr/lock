package org.homesharing.lockbox.presentation.general.composables.inputs

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import org.homesharing.lockbox.presentation.general.composables.basic.BasicInput
import org.homesharing.lockbox.R
import org.homesharing.lockbox.presentation.general.theme.LockBoxTheme

@Composable
internal fun DateInput(
    date: String,
    onFieldClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    BasicInput(
        modifier = modifier,
        value = date,
        onValueChange = {},
        enabled = false,
        onAny = null,
        placeholderText = stringResource(R.string.choose_date),
        leadingPainter = painterResource(R.drawable.calendar),
        onFieldClick = onFieldClick
    )
}

@Preview
@Composable
private fun DateInputPreview() {
    LockBoxTheme {
        DateInput(
            date = "",
            onFieldClick = {},
            modifier = Modifier.fillMaxWidth()
        )
    }
}