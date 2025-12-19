package org.homesharing.lockbox

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.homesharing.lockbox.navigation.AuthenticatedNavigation
import org.homesharing.lockbox.navigation.Navigator
import org.homesharing.lockbox.navigation.rememberNavigationState
import org.homesharing.lockbox.navigation.routes.Route
import org.homesharing.lockbox.navigation.routes.topLevelRoutes
import org.homesharing.lockbox.presentation.activity.LockboxActivityViewModel
import org.homesharing.lockbox.presentation.login.LoginScreen
import org.homesharing.lockbox.presentation.snackbar_controller.CommonStringKeys
import org.homesharing.lockbox.presentation.snackbar_controller.SnackbarController
import org.homesharing.lockbox.presentation.snackbar_controller.SnackbarDuration
import org.homesharing.lockbox.presentation.uikit.animation.loginAnimation
import org.homesharing.lockbox.presentation.uikit.composables.core.LockBoxSnackbar
import org.homesharing.lockbox.presentation.uikit.composables.core.LockboxNavBar
import org.homesharing.lockbox.presentation.uikit.theme.LockBoxTheme
import org.koin.androidx.compose.koinViewModel
import androidx.compose.material3.SnackbarDuration as AndroidSnackbarDuration

class LockBoxActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val lockboxActivityViewModel = koinViewModel<LockboxActivityViewModel>()
            val isLoggedIn by
            lockboxActivityViewModel.isLoggedIn.collectAsStateWithLifecycle(false, this)

            val snackbarHostState = remember { SnackbarHostState() }
            CollectSnackbarEvents(snackbarHostState)

            val navigationState = rememberNavigationState(
                startRoute = Route.Boxes,
                topLevelRoutes = topLevelRoutes.keys,
            )

            val navigator = remember {
                Navigator(navigationState)
            }

            LockBoxTheme {
                Scaffold(
                    modifier = Modifier.background(LockBoxTheme.colors.grey950),
                    bottomBar = {
                        if (isLoggedIn) {
                            LockboxNavBar(
                                selectedKey = navigationState.topLevelRoute,
                                onSelectKey = navigator::navigate
                            )
                        }
                    },
                    snackbarHost = {
                        SnackbarHost(
                            hostState = snackbarHostState
                        ) { data ->
                            LockBoxSnackbar(
                                snackbarData = data
                            )
                        }
                    },
                ) { innerPadding ->
                    AnimatedContent(
                        targetState = isLoggedIn,
                        label = "authentication $isLoggedIn",
                        transitionSpec = loginAnimation,
                    ) { isLoggedIn ->
                        if (isLoggedIn) {
                            AuthenticatedNavigation(
                                navigator = navigator,
                                state = navigationState,
                                padding = innerPadding,
                            )
                        } else {
                            LoginScreen()
                        }
                    }
                }
            }
        }
    }

    @Composable
    private fun CollectSnackbarEvents(snackbarHostState: SnackbarHostState) {
        val scope = rememberCoroutineScope()
        val lifecycleOwner = LocalLifecycleOwner.current

        LaunchedEffect(lifecycleOwner.lifecycle) {
            lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                withContext(Dispatchers.Main.immediate) {
                    SnackbarController.events.flowOn(Dispatchers.Main.immediate)
                        .collect { event ->
                            scope.launch {
                                snackbarHostState.currentSnackbarData?.dismiss()

                                val result = snackbarHostState.showSnackbar(
                                    message = resolveKey(event.messageKey),
                                    actionLabel = event.action?.nameKey?.let { resolveKey(it) },
                                    withDismissAction = true,
                                    duration = resolveDuration(event.duration)
                                )

                                if (result == SnackbarResult.ActionPerformed) {
                                    event.action?.effect?.invoke()
                                }
                            }
                        }
                }
            }
        }
    }

    private fun resolveKey(key: CommonStringKeys): String {
        return getString(
            when (key) {
                CommonStringKeys.CouldNotLoadUserInfo -> R.string.could_not_load_user_info
                CommonStringKeys.CouldNotRefreshUserInfo -> R.string.could_not_refresh_user_info
                CommonStringKeys.CouldNotUpdateUserInfo -> R.string.could_not_update_user_info
                CommonStringKeys.Retry -> R.string.retry
                CommonStringKeys.CouldNotUpdateUserImage -> R.string.could_not_update_user_image
                CommonStringKeys.ImageUpdated -> R.string.image_updated
                CommonStringKeys.CouldNotSendLinkToChangeEmail -> R.string.could_not_send_link_to_change_email
                CommonStringKeys.CouldNotLoadGuestList -> R.string.could_not_load_guest_list
                CommonStringKeys.CouldNotLoadBoxInfo -> R.string.could_not_load_box_info
                CommonStringKeys.CouldNotUpdateBoxAddress -> R.string.could_not_update_box_address
                CommonStringKeys.CouldNotUpdateBoxName -> R.string.could_not_update_box_name
                CommonStringKeys.CouldNotUnbindBox -> R.string.could_not_unbind_box
                CommonStringKeys.CouldNotOpenBox -> R.string.could_not_open_box
                CommonStringKeys.BoxOpenedSuccessfully -> R.string.box_opened_successfully
                CommonStringKeys.TestDeviceCreated -> R.string.test_device_created
                CommonStringKeys.CouldNotCreateTestDevice -> R.string.could_not_create_test_device
                CommonStringKeys.VirtualDeviceCreated -> R.string.virtual_device_created
                CommonStringKeys.CouldNotCreateVirtualDevice -> R.string.could_not_create_virtual_device
                CommonStringKeys.CouldNotSendLinkToChangePassword -> R.string.could_not_send_link_to_change_password
                CommonStringKeys.LinkSentToChangePassword -> R.string.link_sent_to_change_password
                CommonStringKeys.CouldNotLoadActiveSessions -> R.string.could_not_load_active_sessions
                CommonStringKeys.CouldNotCloseOtherSessions -> R.string.could_not_close_other_sessions
                CommonStringKeys.CouldNotDeleteAccount -> R.string.could_not_delete_account

            }
        )
    }

    private fun resolveDuration(duration: SnackbarDuration): AndroidSnackbarDuration =
        when (duration) {
            SnackbarDuration.Short -> AndroidSnackbarDuration.Short
            SnackbarDuration.Long -> AndroidSnackbarDuration.Long
        }
}
