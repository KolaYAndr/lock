package org.homesharing.lockbox.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.withContext

@Composable
internal fun <T> ActionOnSharedFlow(
    lifecycleOwner: LifecycleOwner,
    flow: SharedFlow<T>,
    onFlowCollected: suspend (T) -> Unit,
) {
    val currentOnFlowCollected by rememberUpdatedState(onFlowCollected)
    LaunchedEffect(lifecycleOwner.lifecycle, flow) {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            withContext(Dispatchers.Main.immediate) {
                flow.collect { data ->
                    currentOnFlowCollected(data)
                }
            }
        }
    }
}