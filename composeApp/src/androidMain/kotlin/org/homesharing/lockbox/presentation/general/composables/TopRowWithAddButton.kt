package org.homesharing.lockbox.presentation.general.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.displayCutout
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import org.homesharing.lockbox.R
import org.homesharing.lockbox.presentation.general.composables.login_top_rows.LockBoxesTopRow
import org.homesharing.lockbox.presentation.general.theme.LockBoxTheme

@Composable
internal fun TopRowWithAddButton(
    rowContent: @Composable RowScope.() -> Unit,
    onAdd: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val displayCutout = WindowInsets.displayCutout.asPaddingValues()
    val systemBar = WindowInsets.statusBars.asPaddingValues()
    val topPadding = max(displayCutout.calculateTopPadding(), systemBar.calculateTopPadding())
    Row(
        modifier = modifier.padding(top = topPadding),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            rowContent()
        }

        IconButton(
            onClick = { onAdd() },
            modifier = Modifier.size(40.dp),
            colors = IconButtonDefaults.iconButtonColors(
                contentColor = LockBoxTheme.colors.grey100,
                containerColor = LockBoxTheme.colors.grey950
            )
        ) {
            Icon(
                painter = painterResource(R.drawable.plus),
                contentDescription = null
            )
        }
    }
}

@Preview
@Composable
private fun PreviewTopRow() {
    TopRowWithAddButton(
        rowContent = { LockBoxesTopRow(avatarLink = "") },
        onAdd = {}
    )
}
