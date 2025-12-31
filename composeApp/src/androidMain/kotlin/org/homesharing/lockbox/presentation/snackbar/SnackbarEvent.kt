package org.homesharing.lockbox.presentation.snackbar

import androidx.annotation.StringRes
import androidx.compose.material3.SnackbarDuration
import org.homesharing.lockbox.R

internal data class SnackbarEvent(
    @field:StringRes val message: Int,
    val duration: SnackbarDuration = SnackbarDuration.Long,
    val action: SnackbarAction? = null,
)

internal data class SnackbarAction(
    @field:StringRes val name: Int = R.string.retry,
    val effect: suspend () -> Unit,
)


internal object SnackbarDefaultEvents {
    fun couldNotLoadAccountInfo(onRetry: suspend () -> Unit) = SnackbarEvent(
        message = R.string.could_not_load_user_info,
        action = SnackbarAction(
            effect = { onRetry() }
        )
    )

    fun couldNotRefreshAccountInfo(onRetry: suspend () -> Unit) = SnackbarEvent(
        message = R.string.could_not_refresh_user_info,
        action = SnackbarAction(
            effect = { onRetry() }
        )
    )


    fun couldNotUpdateUserInfoEvent(onRetry: suspend () -> Unit) = SnackbarEvent(
        message = R.string.could_not_update_user_info,
        action = SnackbarAction(
            effect = { onRetry() }
        )
    )

    fun couldNotDeleteAccountEvent(onRetry: () -> Unit) = SnackbarEvent(
        message = R.string.could_not_delete_account,
        action = SnackbarAction(
            effect = { onRetry() }
        )
    )

    val couldNotUpdateUserImageEvent = SnackbarEvent(message = R.string.could_not_update_user_image)

    val successfulUpdateUserImageEvent =
        SnackbarEvent(
            message = R.string.image_updated,
            duration = SnackbarDuration.Short,
        )


    fun couldNotSendLinkToChangeEmail(onRetry: () -> Unit) = SnackbarEvent(
        message = R.string.could_not_send_link_to_change_email,
        action = SnackbarAction(
            effect = onRetry
        )
    )


    fun couldNotLoadGuestList(onRetry: () -> Unit) = SnackbarEvent(
        message = R.string.could_not_load_guest_list,
        action = SnackbarAction(
            effect = onRetry
        )
    )

    fun couldNotLoadBoxInfo(onRetry: () -> Unit) = SnackbarEvent(
        message = R.string.could_not_load_box_info,
        action = SnackbarAction(
            effect = onRetry
        )
    )

    fun couldNotUpdateBoxAddress(onRetry: () -> Unit) = SnackbarEvent(
        message = R.string.could_not_update_box_address,
        action = SnackbarAction(
            effect = onRetry
        )
    )

    fun couldNotUpdateBoxName(onRetry: () -> Unit) = SnackbarEvent(
        message = R.string.could_not_update_box_name,
        action = SnackbarAction(
            effect = onRetry
        )
    )

    fun couldNotUnbindBox(onRetry: () -> Unit) = SnackbarEvent(
        message = R.string.could_not_unbind_box,
        action = SnackbarAction(
            effect = onRetry
        )
    )

    fun couldNotOpenBox(onRetry: () -> Unit) = SnackbarEvent(
        message = R.string.could_not_open_box,
        action = SnackbarAction(
            effect = onRetry
        )
    )

    val boxOpenedSuccessfully = SnackbarEvent(
        message = R.string.box_opened_successfully,
        duration = SnackbarDuration.Short
    )

    val virtualDeviceCreated = SnackbarEvent(
        message = R.string.virtual_device_created,
        duration = SnackbarDuration.Short
    )

    fun couldNotCreateVirtualDevice(onRetry: () -> Unit) = SnackbarEvent(
        message = R.string.could_not_create_virtual_device,
        action = SnackbarAction(
            effect = onRetry
        )
    )


    val testDeviceCreated = SnackbarEvent(
        message = R.string.test_device_created,
        duration = SnackbarDuration.Short
    )

    fun couldNotCreateTestDevice(onRetry: () -> Unit) = SnackbarEvent(
        message = R.string.could_not_create_test_device,
        action = SnackbarAction(
            effect = onRetry
        )
    )

    fun couldNotSendLinkToChangePassword(onRetry: () -> Unit) = SnackbarEvent(
        message = R.string.could_not_send_link_to_change_password,
        action = SnackbarAction(
            effect = { onRetry() }
        )
    )

    val linkSentToResetPassword = SnackbarEvent(
        message = R.string.link_sent_to_change_password,
        duration = SnackbarDuration.Short
    )


    fun couldNotLoadActiveSessions(onRetry: () -> Unit) = SnackbarEvent(
        message = R.string.could_not_load_active_sessions,
        action = SnackbarAction(
            effect = { onRetry() }
        )
    )

    fun couldNotCloseOtherSessions(onRetry: () -> Unit) = SnackbarEvent(
        message = R.string.could_not_close_other_sessions,
        action = SnackbarAction(
            effect = { onRetry() }
        )
    )
}