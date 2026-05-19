package org.homesharing.lockbox

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.homesharing.lockbox.presentation.LockboxApp

class LockboxActivity : ComponentActivity() {
//    private val lockboxActivityViewModel: LockboxActivityViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreenWithCondition()
        enableEdgeToEdge()

        super.onCreate(savedInstanceState)
        setContent {
            LockboxApp()
//            val isLoggedIn by lockboxActivityViewModel.loginState.collectAsStateWithLifecycle()
//
//            val snackbarHostState = remember { SnackbarHostState() }
//            CollectSnackbarEvents(snackbarHostState)
//
//            val navigationState = rememberNavigationState(
//                startRoute = Route.Boxes,
//                topLevelRoutes = topLevelRoutes.keys,
//            )
//
//            val navigator = remember {
//                Navigator(navigationState)
//            }
//
//            LockBoxTheme {
//                LockboxApp(
//                    isLoggedIn = isLoggedIn,
//                    navigationState = navigationState,
//                    navigator = navigator,
//                    snackbarHostState = snackbarHostState
//                )
//            }
        }
    }

//    @Composable
//    private fun LockboxApp(
//        isLoggedIn: LoginState,
//        navigationState: NavigationState,
//        navigator: Navigator,
//        snackbarHostState: SnackbarHostState,
//    ) {
//        Scaffold(
//            modifier = Modifier.background(LockBoxTheme.colors.grey950),
//            bottomBar = {
//                if (isLoggedIn == LoginState.LoggedIn) {
//                    AnimatedVisibility(
//                        visible = navigator.currentScreen.showBottomBar,
//                        enter = expandVertically(),
//                        exit = shrinkVertically(),
//                    ) {
//                        LockboxNavBar(
//                            selectedKey = navigationState.topLevelRoute,
//                            onSelectKey = navigator::navigate,
//                            topLevelRoutes = topLevelRoutes
//                        )
//                    }
//                }
//            },
//            topBar = {
//                if (isLoggedIn == LoginState.LoggedIn) {
//                    AnimatedVisibility(
//                        visible = navigator.currentScreen.topBar != null,
//                        enter = expandVertically(),
//                        exit = shrinkVertically(),
//                    ) {
//                        navigator.currentScreen.topBar?.let { topbar ->
//                            topbar { navigator.navBack() }
//                        }
//                    }
//                }
//            },
//            snackbarHost = {
//                SnackbarHost(
//                    hostState = snackbarHostState
//                ) { data ->
//                    LockBoxSnackbar(
//                        snackbarData = data
//                    )
//                }
//            },
//        ) { innerPadding ->
//            val layoutDirection = LocalLayoutDirection.current
//            AnimatedContent(
//                targetState = isLoggedIn,
//                label = "authentication $isLoggedIn",
//                transitionSpec = loginAnimation,
//                contentKey = { it }
//            ) { isLoggedIn ->
//                val bottomPadding by animateDpAsState(
//                    targetValue = if (navigator.currentScreen.showBottomBar) {
//                        innerPadding.calculateBottomPadding()
//                    } else {
//                        0.dp
//                    }
//                )
//                if (isLoggedIn == LoginState.LoggedIn) {
//                    AuthenticatedNavigation(
//                        navigator = navigator,
//                        state = navigationState,
//                        startPadding = innerPadding.calculateStartPadding(layoutDirection),
//                        endPadding = innerPadding.calculateEndPadding(layoutDirection),
//                        bottomPadding = bottomPadding,
//                    )
//                } else {
//                    LoginScreen()
//                }
//            }
//        }
//    }
//
    private fun installSplashScreenWithCondition() {
        val splashScreen = installSplashScreen()
        var keepSplashOn = true
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
//                lockboxActivityViewModel.loginState.collect { state ->
//                    keepSplashOn = (state == LoginState.Loading)
//                }
                delay(1500)
                keepSplashOn = false
            }
        }
        splashScreen.setKeepOnScreenCondition { keepSplashOn }
    }
//
//    @Composable
//    private fun CollectSnackbarEvents(snackbarHostState: SnackbarHostState) {
//        val scope = rememberCoroutineScope()
//        val lifecycleOwner = LocalLifecycleOwner.current
//
//        LaunchedEffect(lifecycleOwner.lifecycle) {
//            lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
//                withContext(Dispatchers.Main.immediate) {
//                    SnackbarController.events.flowOn(Dispatchers.Main.immediate)
//                        .collect { event ->
//                            scope.launch {
//                                snackbarHostState.currentSnackbarData?.dismiss()
//
//                                val result = snackbarHostState.showSnackbar(
//                                    message = getString(event.message),
//                                    actionLabel = event.action?.let { action -> getString(action.name) },
//                                    withDismissAction = true,
//                                    duration = event.duration
//                                )
//
//                                if (result == SnackbarResult.ActionPerformed) {
//                                    event.action?.effect?.invoke()
//                                }
//                            }
//                        }
//                }
//            }
//        }
//    }
}
