package org.homesharing.lockbox

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.homesharing.lockbox.navigation.BottomNavigation
import org.homesharing.lockbox.navigation.Navigation
import org.homesharing.lockbox.navigation.routes.topLevelRoutes
import org.homesharing.lockbox.presentation.activity.LockboxActivityViewModel
import org.homesharing.lockbox.presentation.general.theme.LockBoxTheme
import org.homesharing.lockbox.presentation.snackbar.LockBoxSnackbar
import org.homesharing.lockbox.presentation.snackbar_controller.CommonStringKeys
import org.homesharing.lockbox.presentation.snackbar_controller.SnackbarController
import org.homesharing.lockbox.presentation.snackbar_controller.SnackbarDuration
import org.koin.androidx.compose.koinViewModel
import androidx.compose.material3.SnackbarDuration as AndroidSnackbarDuration

private const val SPLASH_SCREEN_TIME = 1000L

class LockBoxActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        var keepSplash = true
        super.onCreate(savedInstanceState)
        splashScreen.setKeepOnScreenCondition { keepSplash }

        val systemBarStyle = SystemBarStyle.dark(Color.Transparent.toArgb())

        enableEdgeToEdge(
            statusBarStyle = systemBarStyle,
            navigationBarStyle = systemBarStyle
        )

        lifecycleScope.launch {
            delay(SPLASH_SCREEN_TIME)
            keepSplash = false
        }

        setContent {
            val lockboxActivityViewModel = koinViewModel<LockboxActivityViewModel>()
            val isLoggedIn by
            lockboxActivityViewModel.isLoggedIn.collectAsStateWithLifecycle(false, this)

            val snackbarHostState = remember { SnackbarHostState() }
            CollectSnackbarEvents(snackbarHostState)

            val navController = rememberNavController()
            LockBoxTheme {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(LockBoxTheme.colors.grey950),
                    bottomBar = {
                        BottomNavigation(
                            topLevelRoutes = topLevelRoutes,
                            navBackStackEntry = navBackStackEntry
                        ) { route ->
                            navController.navigate(route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
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
                    }
                ) { _ ->
                    Navigation(
                        navController = navController,
                        startFromLogin = !isLoggedIn,
                    )
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
                    SnackbarController.events.flowOn(Dispatchers.Main.immediate).collect { event ->
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
                CommonStringKeys.CouldNotUpdateBoxStatus -> R.string.could_not_update_box_status
            }
        )
    }

    private fun resolveDuration(duration: SnackbarDuration): AndroidSnackbarDuration = when(duration) {
        SnackbarDuration.Short -> AndroidSnackbarDuration.Short
        SnackbarDuration.Long -> AndroidSnackbarDuration.Long
    }
}
