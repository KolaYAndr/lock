package org.homesharing.lockbox.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
    LaunchedEffect(lifecycleOwner.lifecycle) {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            withContext(Dispatchers.Main.immediate) {
                flow.collect { data ->
                    onFlowCollected(data)
                }
            }
        }
    }
}