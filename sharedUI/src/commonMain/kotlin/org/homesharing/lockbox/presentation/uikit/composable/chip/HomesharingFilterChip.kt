package org.homesharing.lockbox.presentation.uikit.composable.chip

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.homesharing.lockbox.presentation.uikit.theme.HomesharingTheme

@Composable
internal fun HomesharingFilterChip(
    title: String,
    selected: Boolean,
    onSelectionChange: () -> Unit,
    modifier: Modifier = Modifier,
) {
    FilterChip(
        selected = selected,
        onClick = onSelectionChange,
        modifier = modifier,
        shape = HomesharingTheme.shape.roundedCornerShape12,
        label = {
            Text(
                text = title,
                style = HomesharingTheme.typography.bodyMedium,
                maxLines = 1,
            )
        },
        colors = FilterChipDefaults.filterChipColors(
            containerColor = Color.Transparent,
            labelColor = HomesharingTheme.colors.black,
            iconColor = HomesharingTheme.colors.black,
            selectedContainerColor = HomesharingTheme.colors.black.copy(alpha = 0.1f),
            selectedLabelColor = HomesharingTheme.colors.black,
            selectedLeadingIconColor = HomesharingTheme.colors.black
        ),
        border = FilterChipDefaults.filterChipBorder(
            enabled = true,
            selected = selected,
            borderColor = HomesharingTheme.colors.black.copy(alpha = 0.1f),
            selectedBorderColor = HomesharingTheme.colors.black,
            borderWidth = 1.dp,
            selectedBorderWidth = 1.dp
        )
    )
}

@Preview
@Composable
private fun PreviewHomesharingFilterChips() {
    HomesharingTheme {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text("Unselected state:", style = HomesharingTheme.typography.bodySmall)
            HomesharingFilterChip(
                title = "Kitchen",
                selected = false,
                onSelectionChange = { }
            )

            Text("Selected state:", style = HomesharingTheme.typography.bodySmall)
            HomesharingFilterChip(
                title = "Kitchen",
                selected = true,
                onSelectionChange = { }
            )
        }
    }
}