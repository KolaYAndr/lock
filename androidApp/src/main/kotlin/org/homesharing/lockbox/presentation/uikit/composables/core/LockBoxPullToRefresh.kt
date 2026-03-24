package org.homesharing.lockbox.presentation.uikit.composables.core

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.homesharing.lockbox.presentation.uikit.theme.LockBoxTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun LockBoxPullToRefresh(
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
    state: PullToRefreshState,
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit,
) {
    PullToRefreshBox(
        indicator = {
            PullToRefreshDefaults.Indicator(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(4.dp),
                state = state,
                isRefreshing = isRefreshing,
                containerColor = LockBoxTheme.colors.grey900,
                color = LockBoxTheme.colors.green500,
                maxDistance = 50.dp
            )
        },
        isRefreshing = isRefreshing,
        onRefresh = onRefresh,
        state = state,
        modifier = modifier,
        content = content
    )
}
