package org.homesharing.lockbox.presentation_v2.uikit.composable.property

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.homesharing.lockbox.R
import org.homesharing.lockbox.presentation_v2.uikit.theme.HomesharingTheme

@Composable
internal fun HomesharingProperty(
    painter: Painter,
    name: String,
    modifier: Modifier = Modifier,
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

        Text(
            text = name,
            color = HomesharingTheme.colors.black,
            style = HomesharingTheme.typography.bodyLarge
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewHomesharingProperty() {
    HomesharingTheme {
        HomesharingProperty(
            painter = painterResource(R.drawable.house),
            name = stringResource(R.string.living),
            modifier = Modifier.fillMaxWidth()
        )
    }
}