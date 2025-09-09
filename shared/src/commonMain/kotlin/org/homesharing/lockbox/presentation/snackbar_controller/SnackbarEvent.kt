package org.homesharing.lockbox.presentation.snackbar_controller

data class SnackbarEvent(
    val messageKey: CommonStringKeys,
    val duration: SnackbarDuration = SnackbarDuration.Long,
    val action: SnackbarAction? = null
)

data class SnackbarAction(
    val nameKey: CommonStringKeys,
    val effect: suspend () -> Unit
)
