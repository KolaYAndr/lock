package org.homesharing.lockbox.presentation.uikit.composable.pickable

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import lockbox.sharedui.generated.resources.Res
import lockbox.sharedui.generated.resources.ic_chef_hat
import org.homesharing.lockbox.presentation.uikit.theme.HomesharingTheme
import org.jetbrains.compose.resources.painterResource

@Composable
internal fun HomesharingPickableItem(
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
            .clip(HomesharingTheme.shape.roundedCornerShape16)
            .background(HomesharingTheme.colors.white)
            .clickable(
                onClick = onClick,
                role = Role.Switch,
                enabled = enabled
            )
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
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
            style = HomesharingTheme.typography.bodyMedium,
            textAlign = TextAlign.Center
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewHomesharingPickableItem() {
    HomesharingTheme {
        HomesharingPickableItem(
            icon = painterResource(Res.drawable.ic_chef_hat),
            title = "Кухня",
            isSelected = true,
            onClick = {},
        )
    }
}
