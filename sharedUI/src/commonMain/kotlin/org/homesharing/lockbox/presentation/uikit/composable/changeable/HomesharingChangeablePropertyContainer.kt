package org.homesharing.lockbox.presentation.uikit.composable.changeable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.jetbrains.compose.resources.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import lockbox.sharedui.generated.resources.Res
import lockbox.sharedui.generated.resources.address
import lockbox.sharedui.generated.resources.change_full
import org.homesharing.lockbox.presentation.uikit.theme.HomesharingTheme

@Composable
internal fun HomesharingChangeablePropertyContainer(
    title: String,
    onChangeClick: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = title,
                style = HomesharingTheme.typography.titleSmall,
                color = HomesharingTheme.colors.black,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = stringResource(Res.string.change_full),
                style = HomesharingTheme.typography.bodySmall,
                color = HomesharingTheme.colors.black.copy(alpha = .5f),
                modifier = Modifier.clickable { onChangeClick() }
            )
        }

        content()
    }
}

@Preview
@Composable
private fun PreviewHomesharingChangeablePropertyContainer() {
    HomesharingTheme {
        HomesharingChangeablePropertyContainer(
            title = stringResource(Res.string.address),
            onChangeClick = {},
            modifier = Modifier.fillMaxWidth(),
            content = {},
        )
    }
}