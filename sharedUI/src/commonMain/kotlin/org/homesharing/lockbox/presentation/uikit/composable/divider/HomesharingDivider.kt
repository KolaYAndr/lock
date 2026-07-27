package org.homesharing.lockbox.presentation.uikit.composable.divider

import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.homesharing.lockbox.presentation.uikit.theme.HomesharingTheme

@Composable
internal fun HomesharingDivider(
    modifier: Modifier = Modifier
) {
    HorizontalDivider(
        modifier = modifier,
        thickness = 1.dp,
        color = HomesharingTheme.colors.black.copy(alpha = .2f)
    )
}

@Preview
@Composable
private fun PreviewHomesharingDivider() {
    HomesharingTheme {
        HomesharingDivider()
    }
}