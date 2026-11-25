package org.homesharing.lockbox.presentation.uikit.composable.radio_item

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import lockbox.sharedui.generated.resources.Res
import lockbox.sharedui.generated.resources.ic_house
import lockbox.sharedui.generated.resources.ic_user
import org.homesharing.lockbox.presentation.uikit.composable.radio.HomesharingRadio
import org.homesharing.lockbox.presentation.uikit.theme.HomesharingTheme
import org.jetbrains.compose.resources.painterResource

@Composable
internal fun HomesharingRadioItem(
    icon: Painter,
    title: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    Column(
        modifier = modifier
            .border(
                width = 2.dp,
                color = if (isSelected && enabled) {
                    HomesharingTheme.colors.black
                } else {
                    HomesharingTheme.colors.black.copy(alpha = .1f)
                },
                shape = HomesharingTheme.shape.roundedCornerShape16
            )
            .background(HomesharingTheme.colors.white)
            .clip(HomesharingTheme.shape.roundedCornerShape16)
            .clickable(
                onClick = onClick,
                role = Role.RadioButton,
                enabled = enabled
            )
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.Start
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = icon,
                contentDescription = null,
                tint = if (enabled) {
                    HomesharingTheme.colors.black
                } else {
                    HomesharingTheme.colors.black.copy(alpha = .3f)
                },
                modifier = Modifier.size(24.dp)
            )

            HomesharingRadio(
                selected = isSelected,
                onClick = onClick,
                enabled = enabled,
            )
        }

        Text(
            text = title,
            color = if (enabled) {
                HomesharingTheme.colors.black
            } else {
                HomesharingTheme.colors.black.copy(alpha = .3f)
            },
            style = HomesharingTheme.typography.bodyMedium
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewHomesharingRadioItem() {
    HomesharingTheme {
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            HomesharingRadioItem(
                icon = painterResource(Res.drawable.ic_house),
                title = "Владелец",
                isSelected = true,
                onClick = {},
                modifier = Modifier.weight(1f)
            )
            HomesharingRadioItem(
                icon = painterResource(Res.drawable.ic_user),
                title = "Гость",
                isSelected = false,
                onClick = {},
                modifier = Modifier.weight(1f)
            )
        }
    }
}