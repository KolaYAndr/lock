package org.homesharing.lockbox.presentation.general.composables

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import org.homesharing.lockbox.R
import org.homesharing.lockbox.presentation.general.theme.LockBoxTheme

@Composable
internal fun LockBoxesTopRow() {
    Icon(
        painter = painterResource(R.drawable.user),
        tint = LockBoxTheme.colors.grey500,
        contentDescription = null,
        modifier = Modifier.size(28.dp)
    )
    Text(
        text = stringResource(R.string.main),
        style = LockBoxTheme.typography.titleMedium,
        color = LockBoxTheme.colors.grey50
    )
}
