package org.homesharing.lockbox.presentation.uikit.composable.radio

import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import org.homesharing.lockbox.presentation.uikit.theme.HomesharingTheme

@Composable
internal fun HomesharingRadio(
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    RadioButton(
        modifier = modifier,
        enabled = enabled,
        selected = selected,
        onClick = onClick,
        colors = RadioButtonDefaults.colors(
            selectedColor = HomesharingTheme.colors.black,
            unselectedColor = HomesharingTheme.colors.black.copy(alpha = .3f),
            disabledSelectedColor = HomesharingTheme.colors.black.copy(alpha = .4f),
            disabledUnselectedColor = HomesharingTheme.colors.black.copy(alpha = .5f)
        )
    )
}

@Preview
@Composable
private fun PreviewHomesharingRadio() {
    HomesharingTheme {
        HomesharingRadio(
            selected = true,
            onClick = {}
        )
    }
}