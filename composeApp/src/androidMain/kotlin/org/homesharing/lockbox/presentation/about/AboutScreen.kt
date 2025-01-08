package org.homesharing.lockbox.presentation.about

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.homesharing.lockbox.R
import presentation.composables.TitledTopBar
import presentation.composables.general.BasicGroup
import presentation.composables.general.BasicHorizontalDivider
import presentation.theme.LockBoxTheme

@Composable
fun AboutScreen(onNavBack: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.background(LockBoxTheme.colors.grey950),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TitledTopBar(
            title = stringResource(R.string.about_app),
            onNavigate = onNavBack::invoke,
            modifier = Modifier.fillMaxWidth()
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(horizontal = 32.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                painter = painterResource(R.drawable.logo),
                contentDescription = null,
                tint = LockBoxTheme.colors.white,
                modifier = Modifier
                    .size(80.dp)
            )

            Text(
                text = stringResource(R.string.app_version, "1.0.0"),
                style = LockBoxTheme.typography.subHeadlineMedium,
                color = LockBoxTheme.colors.grey500,
                modifier = Modifier.padding(top = 28.dp)
            )

            Text(
                text = stringResource(R.string.legal_info).uppercase(),
                style = LockBoxTheme.typography.footnote,
                color = LockBoxTheme.colors.grey700,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 8.dp)
            )

            Text(
                text = stringResource(R.string.year_lockbox),
                style = LockBoxTheme.typography.footnote,
                color = LockBoxTheme.colors.grey700,
                modifier = Modifier.padding(top = 2.dp)
            )
        }

        val uriHandler = LocalUriHandler.current
        BasicGroup(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            Agreement(
                onClick = { uriHandler.openUri("https://t.me/@korolev_2512") },
                text = stringResource(R.string.user_agreement),
                modifier = Modifier.fillMaxWidth()
            )

            BasicHorizontalDivider(modifier = Modifier.fillMaxWidth())

            Agreement(
                onClick = { uriHandler.openUri("https://t.me/@korolev_2512") },
                text = stringResource(R.string.privacy_policy),
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
private fun Agreement(onClick: () -> Unit, text: String, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = text,
            style = LockBoxTheme.typography.headlineLarge,
            color = LockBoxTheme.colors.white,
            modifier = Modifier.padding(vertical = 10.dp, horizontal = 16.dp)
        )

        Icon(
            painter = painterResource(R.drawable.arrow_right),
            tint = LockBoxTheme.colors.grey500,
            contentDescription = null,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
        )
    }
}

@Preview
@Composable
fun PreviewAboutApp() {
    LockBoxTheme {
        AboutScreen({})
    }
}