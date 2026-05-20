package org.homesharing.lockbox

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.launch
import org.homesharing.lockbox.presentation.LockboxApp
import org.homesharing.lockbox.presentation.LockboxAppViewModel
import org.homesharing.lockbox.presentation.LoginState
import org.koin.androidx.viewmodel.ext.android.viewModel

class LockboxActivity : ComponentActivity() {
    private val viewModel: LockboxAppViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreenWithCondition()
        enableEdgeToEdge()

        super.onCreate(savedInstanceState)
        setContent {
            val loginState by viewModel.loginState.collectAsStateWithLifecycle()
            LockboxApp(
                isLoggedIn = loginState == LoginState.LoggedIn
            )
        }
    }

    private fun installSplashScreenWithCondition() {
        val splashScreen = installSplashScreen()
        var keepSplashOn = true
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.loginState.collect { state ->
                    keepSplashOn = state == LoginState.Loading
                }
            }
        }
        splashScreen.setKeepOnScreenCondition { keepSplashOn }
    }
}
