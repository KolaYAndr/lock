package org.homesharing.lockbox.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import org.homesharing.lockbox.R
import presentation.composables.general.BasicButton
import presentation.composables.LogInCard
import presentation.composables.general.BasicDialog
import presentation.theme.LockBoxTheme

@Composable
fun LogInScreen(modifier: Modifier = Modifier) {
    var showCard by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .background(LockBoxTheme.colors.grey950)
            .then(modifier)
            .padding(horizontal = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            painter = painterResource(R.drawable.log_in),
            contentDescription = null,
            tint = LockBoxTheme.colors.grey500,
            modifier = Modifier.size(48.dp)
        )
        Text(
            text = stringResource(R.string.log_in_your_profile),
            style = LockBoxTheme.typography.titleMedium,
            modifier = Modifier.padding(top = 12.dp),
            color = LockBoxTheme.colors.grey100,
            textAlign = TextAlign.Center
        )
        Text(
            text = stringResource(R.string.start_using_today),
            style = LockBoxTheme.typography.bodyLarge,
            modifier = Modifier.padding(top = 8.dp),
            color = LockBoxTheme.colors.grey500,
            textAlign = TextAlign.Center
        )
        BasicButton(
            onClick = { showCard = true },
            text = stringResource(R.string.sing_in),
            modifier = Modifier.padding(top = 16.dp).minimumInteractiveComponentSize()
        )

        AnimatedVisibility(showCard) {
            BasicDialog(
                onDismissRequest = { showCard = false }
            ) {
                LogInCard(
                    onDismiss = { showCard = false },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}


@Preview
@Composable
fun PreviewLogInScreen() {
    LockBoxTheme {
        LogInScreen(modifier = Modifier.fillMaxSize())
    }
}