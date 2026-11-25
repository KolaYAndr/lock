package org.homesharing.lockbox.presentation.uikit.composable.checkbox

import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import org.homesharing.lockbox.presentation.uikit.theme.HomesharingTheme

@Composable
internal fun HomesharingCheckbox(
    isChecked: Boolean,
    onCheck: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    Checkbox(
        modifier = modifier,
        checked = isChecked,
        onCheckedChange = onCheck,
        enabled = enabled,
        colors = CheckboxDefaults.colors(
            checkedColor = HomesharingTheme.colors.black,
            uncheckedColor = HomesharingTheme.colors.black,
            checkmarkColor = HomesharingTheme.colors.white,
            disabledCheckedColor = HomesharingTheme.colors.black.copy(alpha = .3f),
            disabledUncheckedColor = HomesharingTheme.colors.black.copy(alpha = .1f),
            disabledIndeterminateColor = HomesharingTheme.colors.black.copy(alpha = .3f),
        )
    )
}

@Preview
@Composable
private fun PreviewHomesharingCheckbox() {
    HomesharingTheme {
        HomesharingCheckbox(
            isChecked = false,
            onCheck = {}
        )
    }
}