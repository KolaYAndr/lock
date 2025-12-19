package org.homesharing.lockbox.presentation.uikit.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.homesharing.lockbox.R
import org.homesharing.lockbox.presentation.uikit.theme.LockBoxTheme

@Composable
internal fun IconTextArrow(
    painter: Painter,
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = Modifier
            .clickable { onClick() }
            .padding(vertical = 12.dp, horizontal = 16.dp)
            .then(modifier),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Icon(
            painter = painter,
            contentDescription = null,
            modifier = Modifier.size(24.dp),
            tint = LockBoxTheme.colors.grey100
        )

        Text(
            text = text,
            style = LockBoxTheme.typography.headlineLarge,
            color = LockBoxTheme.colors.constantWhite,
            modifier = Modifier.weight(1f)
        )

        Icon(
            painter = painterResource(R.drawable.arrow_right),
            contentDescription = null,
            tint = LockBoxTheme.colors.grey500,
            modifier = Modifier.size(24.dp)
        )
    }
}

@Composable
@Preview
private fun PreviewIconTextArrow() {
    LockBoxTheme {
        IconTextArrow(
            painterResource(R.drawable.headset),
            "",
            {},
            Modifier.fillMaxWidth()
        )
    }
}
