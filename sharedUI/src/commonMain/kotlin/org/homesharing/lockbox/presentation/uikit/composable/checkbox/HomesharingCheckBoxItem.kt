package org.homesharing.lockbox.presentation.uikit.composable.checkbox

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.homesharing.lockbox.presentation.uikit.modifier.paddedBorder
import org.homesharing.lockbox.presentation.uikit.theme.HomesharingTheme

@Composable
internal fun HomesharingCheckBoxItem(
    title: String,
    subtitle: String,
    isChecked: Boolean,
    onCheck: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val borderColor by animateColorAsState(
        targetValue = if (isPressed) {
            HomesharingTheme.colors.black.copy(alpha = .5f)
        } else {
            Color.Transparent
        }
    )
    val backgroundColor by animateColorAsState(
        if (isPressed) {
            HomesharingTheme.colors.black.copy(alpha = .05f)
        } else {
            HomesharingTheme.colors.white
        }
    )
    Row(
        modifier = modifier
            .paddedBorder(borderColor)
            .clip(HomesharingTheme.shape.roundedCornerShape16)
            .background(
                backgroundColor,
                HomesharingTheme.shape.roundedCornerShape16,
            )
            .clickable(
                enabled = enabled,
                role = Role.Checkbox,
                interactionSource = interactionSource,
                onClick = onCheck
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.Start)
    ) {
        Checkbox(
            checked = isChecked,
            onCheckedChange = { onCheck() },
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

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(2.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.Start,
        ) {
            Text(
                text = title,
                color = if (enabled) {
                    HomesharingTheme.colors.black
                } else {
                    HomesharingTheme.colors.black.copy(alpha = .3f)
                },
                style = HomesharingTheme.typography.bodyMedium
            )

            Text(
                text = subtitle,
                color = if (enabled) {
                    HomesharingTheme.colors.black.copy(alpha = .5f)
                } else {
                    HomesharingTheme.colors.black.copy(alpha = .3f)
                },
                style = HomesharingTheme.typography.bodySmall
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewHomesharingCheckBoxItem() {
    HomesharingTheme {
        HomesharingCheckBoxItem(
            title = "Title",
            subtitle = "Subtitle",
            isChecked = true,
            onCheck = {},
            modifier = Modifier.fillMaxWidth()
        )
    }
}