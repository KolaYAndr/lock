package org.homesharing.lockbox.presentation.general.composables

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshState
import androidx.compose.material3.pulltorefresh.pullToRefreshIndicator
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.homesharing.lockbox.presentation.general.theme.LockBoxTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LockBoxPullToRefresh(
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
    state: PullToRefreshState = rememberPullToRefreshState(),
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit
) {
    PullToRefreshBox(
        indicator = {
            LockBoxCircularProgress(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .pullToRefreshIndicator(
                        state = state,
                        isRefreshing = isRefreshing,
                        shape = LockBoxTheme.shape.circleShape,
                        containerColor = LockBoxTheme.colors.grey900,
                        threshold = 50.dp
                    )
                    .padding(4.dp)
            )
        },
        isRefreshing = isRefreshing,
        onRefresh = onRefresh,
        state = state,
        modifier = modifier,
    ) {
        content()
    }
}
