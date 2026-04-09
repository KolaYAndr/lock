package org.homesharing.lockbox.interop

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class SwiftFlow<T>(kotlinFlow: Flow<T>) : Flow<T> by kotlinFlow {
    fun watch(block: (T) -> Unit): SwiftFlowCancellable {
        val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main)
        onEach(block).launchIn(scope)
        return object : SwiftFlowCancellable {
            override fun cancel() {
                scope.cancel()
            }
        }
    }
}

interface SwiftFlowCancellable {
    fun cancel()
}