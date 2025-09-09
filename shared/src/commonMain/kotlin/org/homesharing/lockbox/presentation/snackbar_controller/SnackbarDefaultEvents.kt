package org.homesharing.lockbox.presentation.snackbar_controller

enum class CommonStringKeys {
    CouldNotLoadUserInfo,
    CouldNotRefreshUserInfo,
    CouldNotUpdateUserInfo,
    Retry,
    CouldNotUpdateUserImage,
    ImageUpdated,
    CouldNotSendLinkToChangeEmail,
    CouldNotLoadGuestList,
    CouldNotLoadBoxInfo,
    CouldNotUpdateBoxAddress,
    CouldNotUpdateBoxName,
    CouldNotUnbindBox,
    CouldNotOpenBox,
    BoxOpenedSuccessfully,
    TestDeviceCreated,
    CouldNotCreateTestDevice,
    VirtualDeviceCreated,
    CouldNotCreateVirtualDevice,
    CouldNotSendLinkToChangePassword,
    LinkSentToChangePassword,
    CouldNotLoadActiveSessions,
    CouldNotCloseOtherSessions,
}

internal object SnackbarDefaultEvents {
    fun couldNotLoadAccountInfo(onRetry: suspend () -> Unit) = SnackbarEvent(
        messageKey = CommonStringKeys.CouldNotLoadUserInfo,
        action = SnackbarAction(
            nameKey = CommonStringKeys.Retry,
            effect = { onRetry() }
        )
    )

    fun couldNotRefreshAccountInfo(onRetry: suspend () -> Unit) = SnackbarEvent(
        messageKey = CommonStringKeys.CouldNotRefreshUserInfo,
        action = SnackbarAction(
            nameKey = CommonStringKeys.Retry,
            effect = { onRetry() }
        )
    )
}
