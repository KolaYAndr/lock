package org.homesharing.lockbox.presentation_v2.uikit.composable.chip

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.homesharing.lockbox.R
import org.homesharing.lockbox.presentation_v2.uikit.theme.HomesharingTheme

@Composable
internal fun HomesharingChip(
    title: String,
    painter: Painter,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .clip(HomesharingTheme.shape.roundedCornerShape12)
            .border(
                width = 1.dp,
                color = HomesharingTheme.colors.black.copy(alpha = .1f),
                shape = HomesharingTheme.shape.roundedCornerShape12
            )
            .padding(horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.Start)
    ) {
        Icon(
            painter = painter,
            contentDescription = null,
            modifier = Modifier
                .padding(vertical = 8.dp)
                .size(24.dp),
            tint = HomesharingTheme.colors.black,
        )

        Text(
            text = title,
            color = HomesharingTheme.colors.black,
            style = HomesharingTheme.typography.bodyMedium,
            maxLines = 1,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewHomesharingChip() {
    HomesharingTheme {
        HomesharingChip(
            title = "Kitchen",
            painter = painterResource(R.drawable.chef_hat),
        )
    }
}
