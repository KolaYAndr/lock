package org.homesharing.lockbox.presentation.uikit.composable.property

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import lockbox.sharedui.generated.resources.Res
import lockbox.sharedui.generated.resources.ic_house
import lockbox.sharedui.generated.resources.living
import org.homesharing.lockbox.presentation.uikit.theme.HomesharingTheme
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun HomesharingProperty(
    painter: Painter,
    name: String,
    modifier: Modifier = Modifier,
    description: String? = null,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.Start)
    ) {
        Icon(
            painter = painter,
            contentDescription = null,
            modifier = Modifier.size(24.dp),
            tint = HomesharingTheme.colors.black.copy(alpha = .3f)
        )

        Column {
            Text(
                text = name,
                color = HomesharingTheme.colors.black,
                style = HomesharingTheme.typography.bodyMedium
            )

            description?.let {
                Text(
                    text = it,
                    color = HomesharingTheme.colors.black.copy(alpha = .5f),
                    style = HomesharingTheme.typography.bodySmall
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewHomesharingProperty() {
    HomesharingTheme {
        HomesharingProperty(
            painter = painterResource(Res.drawable.ic_house),
            name = stringResource(Res.string.living),
            modifier = Modifier.fillMaxWidth()
        )
    }
}