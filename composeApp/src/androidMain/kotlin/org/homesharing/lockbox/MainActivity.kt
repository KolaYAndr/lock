package org.homesharing.lockbox

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
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
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.homesharing.lockbox.navigation.BottomNavigation
import org.homesharing.lockbox.navigation.Navigation
import org.homesharing.lockbox.navigation.routes.topLevelRoutes
import org.homesharing.lockbox.presentation.snackbar_controller.LockBoxSnackbar
import org.homesharing.lockbox.presentation.snackbar_controller.SnackbarController
import org.koin.androidx.compose.KoinAndroidContext
import org.koin.androidx.compose.koinViewModel
import presentation.theme.LockBoxTheme

private const val SPLASH_SCREEN_TIME = 1000L

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        var keepSplash = true
        super.onCreate(savedInstanceState)
        splashScreen.setKeepOnScreenCondition { keepSplash }

//        window.statusBarColor = ContextCompat.getColor(this, R.color.Grey950)
        val systemBarStyle = SystemBarStyle.dark(ContextCompat.getColor(this, R.color.Grey950))

        enableEdgeToEdge(
            statusBarStyle = systemBarStyle,
            navigationBarStyle = systemBarStyle
        )

        lifecycleScope.launch {
            delay(SPLASH_SCREEN_TIME)
            keepSplash = false
        }

        setContent {
            val mainActivityViewModel = koinViewModel<MainActivityViewModel>()
            val isLoggedIn =
                mainActivityViewModel.isLoggedIn.collectAsStateWithLifecycle(false, this)

            val snackbarHostState = remember { SnackbarHostState() }


            CollectSnackbarEvents(snackbarHostState)

            val navController = rememberNavController()
            KoinAndroidContext {
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
                            )
                            { data ->
                                LockBoxSnackbar(
                                    snackbarData = data
                                )
                            }
                        }
                    ) { paddingValues ->
                        Navigation(
                            navController = navController,
                            startFromLogin = !isLoggedIn.value,
                            modifier = Modifier.padding(paddingValues)
                        )
                    }
                }
            }
        }
    }

    @Composable
    private fun CollectSnackbarEvents(snackbarHostState: SnackbarHostState) {
        val scope = rememberCoroutineScope()
        val lifecycleOwner = LocalLifecycleOwner.current
        val context = LocalContext.current

        LaunchedEffect(lifecycleOwner.lifecycle) {
            lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                withContext(Dispatchers.Main.immediate) {
                    SnackbarController.events.collect { event ->
                        scope.launch {
                            snackbarHostState.currentSnackbarData?.dismiss()

                            val result = snackbarHostState.showSnackbar(
                                message = context.getString(event.message),
                                actionLabel = event.action?.name?.let { context.getString(it) },
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
