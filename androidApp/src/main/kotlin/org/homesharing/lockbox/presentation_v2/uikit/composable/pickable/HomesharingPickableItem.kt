package org.homesharing.lockbox.presentation_v2.uikit.composable.pickable

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.homesharing.lockbox.R
import org.homesharing.lockbox.presentation_v2.uikit.theme.HomesharingTheme

@Composable
internal fun HomesharingPickableItem(
    icon: Painter,
    title: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    role: Role = Role.Switch,
    enabled: Boolean = true,
) {
    val isSelectedModifier = if (isSelected && enabled) {
        Modifier.border(
            width = 2.dp,
            color = HomesharingTheme.colors.black,
            shape = HomesharingTheme.shape.roundedCornerShape16
        )
    } else {
        Modifier.border(
            width = 2.dp,
            color = HomesharingTheme.colors.black.copy(alpha = .1f),
            shape = HomesharingTheme.shape.roundedCornerShape16
        )
    }
    Column(
        modifier = modifier
            .height(104.dp)
            .clip(HomesharingTheme.shape.roundedCornerShape16)
            .background(HomesharingTheme.colors.white)
            .clickable(
                onClick = onClick,
                role = role,
                enabled = enabled
            )
            .then(isSelectedModifier),
        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = icon,
            contentDescription = null,
            tint = if (enabled) {
                HomesharingTheme.colors.black
            } else {
                HomesharingTheme.colors.black.copy(alpha = .3f)
            },
            modifier = Modifier.size(32.dp)
        )

        Text(
            text = title,
            color = if (enabled) {
                HomesharingTheme.colors.black
            } else {
                HomesharingTheme.colors.black.copy(alpha = .3f)
            },
            style = HomesharingTheme.typography.bodyLarge
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewHomesharingPickableItem() {
    HomesharingTheme {
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            HomesharingPickableItem(
                icon = painterResource(R.drawable.house),
                title = "Владелец",
                isSelected = true,
                onClick = {},
                modifier = Modifier.weight(1f)
            )
            HomesharingPickableItem(
                icon = painterResource(R.drawable.user),
                title = "Гость",
                isSelected = false,
                onClick = {},
                modifier = Modifier.weight(1f)
            )
        }
    }
}