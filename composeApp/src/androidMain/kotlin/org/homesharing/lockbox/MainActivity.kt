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
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import org.homesharing.lockbox.navigation.BottomNavigation
import org.homesharing.lockbox.navigation.Navigation
import org.homesharing.lockbox.navigation.routes.topLevelRoutes
import org.koin.androidx.compose.KoinAndroidContext
import org.koin.androidx.compose.koinViewModel
import presentation.theme.LockBoxTheme

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

        setContent {
            val mainActivityViewModel = koinViewModel<MainActivityViewModel>()
            var canLogin by remember { mutableStateOf(false) }
            SideEffect {
                lifecycleScope.launch {
                    canLogin = mainActivityViewModel.canLogin().await()
                    keepSplash = false
                }
            }

            val navController = rememberNavController()
            KoinAndroidContext {
                LockBoxTheme {
                    Scaffold(
                        modifier = Modifier.fillMaxSize(),
                        bottomBar = {
                            val navBackStackEntry by navController.currentBackStackEntryAsState()
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
                        }
                    ) { paddingValues ->
                        Navigation(
                            navController = navController,
                            startFromLogin = !canLogin,
                            modifier = Modifier
                                .padding(paddingValues)
                                .background(LockBoxTheme.colors.grey950)
                        )
                    }
                }
            }
        }
    }
}
