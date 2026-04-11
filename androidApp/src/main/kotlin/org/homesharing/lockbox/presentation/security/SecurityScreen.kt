package org.homesharing.lockbox.presentation.security

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.homesharing.lockbox.R
import org.homesharing.lockbox.presentation.sign_in.FORGOT_PASSWORD_URL
import org.homesharing.lockbox.presentation.uikit.composables.TitledTopBar
import org.homesharing.lockbox.presentation.uikit.theme.LockBoxTheme

@Composable
internal fun SecurityScreen(
    navigateToActiveSessions: () -> Unit,
) {
    SecurityScreen(
        onToActiveSessions = navigateToActiveSessions,
        modifier = Modifier.fillMaxSize()
    )
}

@Composable
private fun SecurityScreen(
    onToActiveSessions: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val topAppBarHeight = TopAppBarDefaults.TopAppBarExpandedHeight
    val topInsets =
        WindowInsets.statusBars.only(WindowInsetsSides.Top).asPaddingValues().calculateTopPadding()
    Column(
        modifier = modifier.background(LockBoxTheme.colors.grey950),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(topAppBarHeight + topInsets))
        val uriHandler = LocalUriHandler.current
        SecurityElement(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp)
                .padding(horizontal = 16.dp),
            onClick = { uriHandler.openUri(FORGOT_PASSWORD_URL) },
            title = stringResource(R.string.change_password)
        )

        SecurityElement(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp)
                .padding(horizontal = 16.dp),
            title = stringResource(R.string.active_sessions),
            onClick = onToActiveSessions
        )
    }
}

@Composable
internal fun SecurityTopBar(onNavBack: () -> Unit) {
    TitledTopBar(
        title = stringResource(R.string.security),
        onNavigate = onNavBack,
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
private fun SecurityElement(title: String, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .background(
                color = LockBoxTheme.colors.grey900,
                shape = LockBoxTheme.shape.roundedCornerShape8
            )
            .border(
                width = 0.5.dp,
                color = LockBoxTheme.colors.grey800,
                shape = LockBoxTheme.shape.roundedCornerShape8
            )
            .clickable { onClick() }
            .padding(vertical = 14.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = title,
            style = LockBoxTheme.typography.headlineLarge,
            color = LockBoxTheme.colors.constantWhite,
        )

        Icon(
            painter = painterResource(R.drawable.arrow_right),
            tint = LockBoxTheme.colors.grey500,
            contentDescription = null
        )
    }
}

@Preview
@Composable
private fun PreviewSecurityScreenChangePassword() {
    LockBoxTheme {
        SecurityScreen(
            {},
            modifier = Modifier.fillMaxSize(),
        )
    }
}
