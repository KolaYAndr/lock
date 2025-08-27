package org.homesharing.lockbox.presentation.general.composables.login_top_rows

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import org.homesharing.lockbox.R
import org.homesharing.lockbox.presentation.general.composables.NetworkImage
import org.homesharing.lockbox.presentation.general.theme.LockBoxTheme

@Composable
internal fun DocumentsTopRow(avatarLink: String? = null) {
    NetworkImage(
        url = avatarLink,
        modifier = Modifier.size(40.dp)
    )
    Text(
        text = stringResource(R.string.documents),
        style = LockBoxTheme.typography.titleMedium,
        color = LockBoxTheme.colors.grey50
    )
}