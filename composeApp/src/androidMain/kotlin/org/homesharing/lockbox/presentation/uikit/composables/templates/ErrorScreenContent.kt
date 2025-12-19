package org.homesharing.lockbox.presentation.uikit.composables.templates

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.homesharing.lockbox.R
import org.homesharing.lockbox.presentation.uikit.composables.core.LockBoxPullToRefresh
import org.homesharing.lockbox.presentation.uikit.composables.core.buttons.LockboxButton
import org.homesharing.lockbox.presentation.uikit.theme.LockBoxTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ErrorScreenContent(
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
    message: String?,
    modifier: Modifier = Modifier,
) {
    LockBoxPullToRefresh(
        isRefreshing = isRefreshing,
        state = rememberPullToRefreshState(),
        onRefresh = onRefresh
    ) {
        LazyColumn(
            modifier = modifier,
            contentPadding = PaddingValues(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(32.dp, Alignment.CenterVertically)
        ) {
            item {
                Text(
                    text = message ?: stringResource(R.string.unknown_error),
                    color = LockBoxTheme.colors.grey100,
                    style = LockBoxTheme.typography.titleSmall,
                    textAlign = TextAlign.Center
                )
            }

            item {
                LockboxButton(
                    onClick = onRefresh,
                    text = stringResource(R.string.refresh),
                )
            }
        }
    }
}

@Preview
@Composable
private fun PreviewErrorScreen() {
    LockBoxTheme {
        ErrorScreenContent(
            isRefreshing = false,
            onRefresh = { },
            message = null,
            modifier = Modifier.fillMaxSize()
        )
    }
}
