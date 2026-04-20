package org.homesharing.lockbox.presentation.change_email

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.homesharing.lockbox.R
import org.homesharing.lockbox.presentation.uikit.composables.TopAppBarSpacer
import org.homesharing.lockbox.presentation.uikit.composables.core.buttons.LockboxButton
import org.homesharing.lockbox.presentation.uikit.theme.LockBoxTheme

@Composable
internal fun EmailChangedSuccessfullyScreen(onDone: () -> Unit) {
    BackHandler { onDone() }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(LockBoxTheme.colors.grey950)
            .padding(horizontal = 32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopAppBarSpacer()
        Icon(
            painter = painterResource(R.drawable.circle_check),
            contentDescription = null,
            modifier = Modifier.size(48.dp),
            tint = LockBoxTheme.colors.green600
        )

        Text(
            text = stringResource(R.string.email_successfully_changed),
            style = LockBoxTheme.typography.titleMedium,
            color = LockBoxTheme.colors.grey100,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 12.dp)
        )

        Text(
            text = stringResource(R.string.now_you_can_use_new_email),
            style = LockBoxTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            color = LockBoxTheme.colors.grey500,
            modifier = Modifier.padding(top = 8.dp)
        )

        LockboxButton(
            text = stringResource(R.string.ready),
            modifier = Modifier.padding(top = 12.dp),
            onClick = onDone,
            paddingValues = PaddingValues(10.dp)
        )
    }
}

@Composable
@Preview
private fun PreviewChangeSuccess() {
    LockBoxTheme {
        EmailChangedSuccessfullyScreen { }
    }
}
