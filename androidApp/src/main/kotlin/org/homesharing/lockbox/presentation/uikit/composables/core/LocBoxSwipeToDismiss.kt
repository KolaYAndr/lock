package org.homesharing.lockbox.presentation.uikit.composables.core

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxState
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import org.homesharing.lockbox.R
import org.homesharing.lockbox.presentation.uikit.theme.LockBoxTheme

@Composable
internal fun LockBoxSwipeToDismiss(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit,
    content: @Composable RowScope.() -> Unit,
) {
    val scope = rememberCoroutineScope()
    val state = remember {
        SwipeToDismissBoxState(
            initialValue = SwipeToDismissBoxValue.Settled,
            positionalThreshold = { distance -> distance * 0.25f }
        )
    }
    SwipeToDismissBox(
        enableDismissFromStartToEnd = false,
        enableDismissFromEndToStart = true,
        modifier = modifier.clip(LockBoxTheme.shape.roundedCornerShape8),
        state = state,
        backgroundContent = {
            when (state.dismissDirection) {
                SwipeToDismissBoxValue.EndToStart -> {
                    Box(
                        modifier = Modifier
                            .background(LockBoxTheme.colors.red600)
                            .fillMaxSize()
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.trash),
                            tint = LockBoxTheme.colors.constantWhite,
                            contentDescription = null,
                            modifier = Modifier
                                .align(Alignment.CenterEnd)
                                .padding(end = 12.dp)
                        )
                    }
                }

                else -> Unit
            }
        },
        content = content,
        onDismiss = { orientation ->
            when (orientation) {
                SwipeToDismissBoxValue.EndToStart -> {
                    onDismiss()
                    scope.launch {
                        state.snapTo(SwipeToDismissBoxValue.Settled)
                    }
                }

                else -> Unit
            }
        }
    )
}