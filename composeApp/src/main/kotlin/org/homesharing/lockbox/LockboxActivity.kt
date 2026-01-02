package org.homesharing.lockbox

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
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
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation3.runtime.NavKey
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.homesharing.lockbox.navigation.AuthenticatedNavigation
import org.homesharing.lockbox.navigation.NavigationState
import org.homesharing.lockbox.navigation.Navigator
import org.homesharing.lockbox.navigation.deeplinks.deepLinkMap
import org.homesharing.lockbox.navigation.rememberNavigationState
import org.homesharing.lockbox.navigation.routes.Route
import org.homesharing.lockbox.navigation.routes.topLevelRoutes
import org.homesharing.lockbox.presentation.login.LoginScreen
import org.homesharing.lockbox.presentation.snackbar.SnackbarController
import org.homesharing.lockbox.presentation.uikit.animation.loginAnimation
import org.homesharing.lockbox.presentation.uikit.composables.core.LockBoxSnackbar
import org.homesharing.lockbox.presentation.uikit.composables.core.LockboxNavBar
import org.homesharing.lockbox.presentation.uikit.theme.LockBoxTheme
import org.koin.androidx.viewmodel.ext.android.viewModel

class LockboxActivity : ComponentActivity() {
    private val lockboxActivityViewModel: LockboxActivityViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreenWithCondition()
        val style = SystemBarStyle.dark(ContextCompat.getColor(this, R.color.Grey950))
        enableEdgeToEdge(
            statusBarStyle = style,
            navigationBarStyle = style
        )

        val key = parseDeepLinkOrDefaultStart(intent.data)

        super.onCreate(savedInstanceState)
        setContent {
            val isLoggedIn by lockboxActivityViewModel.loginState.collectAsStateWithLifecycle()

            val snackbarHostState = remember { SnackbarHostState() }
            CollectSnackbarEvents(snackbarHostState)

            val navigationState = rememberNavigationState(
                startRoute = key,
                topLevelRoutes = topLevelRoutes.keys,
            )

            val navigator = remember {
                Navigator(navigationState)
            }

            LockBoxTheme {
                LockboxApp(
                    isLoggedIn = isLoggedIn,
                    navigationState = navigationState,
                    navigator = navigator,
                    snackbarHostState = snackbarHostState
                )
            }
        }
    }

    @Composable
    private fun LockboxApp(
        isLoggedIn: LoginState,
        navigationState: NavigationState,
        navigator: Navigator,
        snackbarHostState: SnackbarHostState,
    ) {
        Scaffold(
            modifier = Modifier.background(LockBoxTheme.colors.grey950),
            bottomBar = {
                if (isLoggedIn == LoginState.LoggedIn) {
                    LockboxNavBar(
                        selectedKey = navigationState.topLevelRoute,
                        onSelectKey = navigator::navigate,
                        topLevelRoutes = topLevelRoutes
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
                if (isLoggedIn == LoginState.LoggedIn) {
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

    private fun parseDeepLinkOrDefaultStart(uri: Uri?): NavKey = deepLinkMap[uri] ?: Route.Boxes


    private fun installSplashScreenWithCondition() {
        val splashScreen = installSplashScreen()
        var keepSplashOn = true
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                lockboxActivityViewModel.loginState.collect { state ->
                    keepSplashOn = (state == LoginState.Loading)
                }
            }
        }
        splashScreen.setKeepOnScreenCondition { keepSplashOn }
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
                                    message = getString(event.message),
                                    actionLabel = event.action?.let { action -> getString(action.name) },
                                    withDismissAction = true,
                                    duration = event.duration
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
}
