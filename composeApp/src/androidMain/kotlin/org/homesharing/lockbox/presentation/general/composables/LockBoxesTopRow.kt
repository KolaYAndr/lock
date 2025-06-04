package org.homesharing.lockbox.presentation.general.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import org.homesharing.lockbox.R
import org.homesharing.lockbox.presentation.general.theme.LockBoxTheme

@Composable
internal fun LockBoxesTopRow() {
    Box(
        modifier = Modifier
            .padding(start = 16.dp, top = 8.dp, bottom = 8.dp, end = 12.dp)
            .size(40.dp)
            .background(
                color = LockBoxTheme.colors.grey800,
                shape = LockBoxTheme.shape.circleShape
            ),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(R.drawable.user),
            tint = LockBoxTheme.colors.grey500,
            contentDescription = null,
            modifier = Modifier.size(28.dp)
        )
    }
    Text(
        text = stringResource(R.string.main),
        style = LockBoxTheme.typography.titleMedium,
        color = LockBoxTheme.colors.grey50
    )
}
