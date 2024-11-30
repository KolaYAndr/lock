package org.homesharing.lockbox

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.homesharing.lockbox.presentation.LogInScreen
import org.jetbrains.compose.ui.tooling.preview.Preview
import presentation.theme.LockBoxTheme

@Composable
@Preview
fun App() {
    LockBoxTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize()
        ) { paddingValues ->
            LogInScreen(modifier = Modifier.fillMaxSize().padding(paddingValues))
        }
    }
}