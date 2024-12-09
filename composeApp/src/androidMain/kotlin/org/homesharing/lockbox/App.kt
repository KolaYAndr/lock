package org.homesharing.lockbox

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.homesharing.lockbox.di.provideAppModules
import org.homesharing.lockbox.presentation.LogInScreen
import org.homesharing.lockbox.presentation.view_model.SignUpViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.KoinApplication
import org.koin.core.KoinApplication
import presentation.theme.LockBoxTheme

@Composable
@Preview
fun App() {
    KoinApplication(
        application = {
            modules(provideAppModules)
        }
    ){
        LockBoxTheme {
            Scaffold(
                modifier = Modifier.fillMaxSize()
            ) { paddingValues ->
                LogInScreen(modifier = Modifier.fillMaxSize().padding(paddingValues))
            }
        }
    }
}