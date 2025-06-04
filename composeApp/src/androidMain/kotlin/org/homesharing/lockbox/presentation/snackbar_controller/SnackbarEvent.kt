package org.homesharing.lockbox.presentation.snackbar_controller

import androidx.annotation.StringRes
import androidx.compose.material3.SnackbarDuration
import org.homesharing.lockbox.R

internal data class SnackbarEvent(
    @StringRes val message: Int,
    val duration: SnackbarDuration = SnackbarDuration.Long,
    val action: SnackbarAction? = null
)

internal data class SnackbarAction(
    @StringRes val name: Int,
    val effect: suspend () -> Unit,
)

internal object SnackbarDefaultEvents {
    fun couldNotLoadAccountInfo(onRetry: suspend () -> Unit) = SnackbarEvent(
        message = R.string.could_not_load_user_info,
        action = SnackbarAction(
            name = R.string.retry,
            effect = { onRetry() }
        )
    )

    fun couldNotRefreshAccountInfo(onRetry: suspend () -> Unit) = SnackbarEvent(
        message = R.string.could_not_refresh_user_info,
        action = SnackbarAction(
            name = R.string.retry,
            effect = { onRetry() }
        )
    )
}
