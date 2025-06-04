package org.homesharing.lockbox.presentation.general.composables

import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.unit.dp
import org.homesharing.lockbox.R
import org.homesharing.lockbox.presentation.general.composables.basic.BasicHorizontalDivider
import org.homesharing.lockbox.presentation.general.composables.basic.BasicModal
import org.homesharing.lockbox.presentation.general.theme.LockBoxTheme

@Composable
internal fun HelpModalSheet(onDismiss: () -> Unit, modifier: Modifier = Modifier) {
    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { }
    BasicModal(
        modifier = modifier,
        onDismiss = onDismiss
    ) {
        TitledTopWithCloseAndBackButtons(
            title = stringResource(R.string.help),
            subtitle = null,
            onDismiss = onDismiss,
            modifier = Modifier.fillMaxWidth()
        )

        BasicHorizontalDivider(modifier = Modifier.fillMaxWidth())

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                painter = painterResource(R.drawable.headset),
                contentDescription = null,
                modifier = Modifier.size(40.dp),
                tint = LockBoxTheme.colors.grey500
            )

            Text(
                text = stringResource(R.string.contact_us),
                style = LockBoxTheme.typography.titleMedium,
                color = LockBoxTheme.colors.grey100,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 12.dp)
            )

            Text(
                text = stringResource(R.string.we_will_try_to_solve_your_issue),
                style = LockBoxTheme.typography.bodyMedium,
                color = LockBoxTheme.colors.grey500,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 8.dp, bottom = 48.dp)
            )

            val email = stringResource(R.string.lockbox_email)
            IconTextArrow(
                modifier = Modifier.fillMaxWidth(),
                painter = painterResource(R.drawable.mail),
                text = email,
                onClick = {
                    val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
                        data = Uri.parse("mailto:")
                        putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
                    }
                    launcher.launch(emailIntent)
                }
            )

            BasicHorizontalDivider(modifier = Modifier.fillMaxWidth())

            IconTextArrow(
                modifier = Modifier.fillMaxWidth(),
                painter = painterResource(R.drawable.telegram),
                text = stringResource(R.string.telegram),
                onClick = {
                     val tgIntent = Intent(Intent.ACTION_VIEW).apply {
                        setDataAndType(Uri.parse("https://t.me/korolev_2512"), "text/plain")
                    }
                    launcher.launch(tgIntent)
                }
            )

            BasicHorizontalDivider(modifier = Modifier.fillMaxWidth())

            IconTextArrow(
                modifier = Modifier.fillMaxWidth(),
                painter = painterResource(R.drawable.phone),
                text = stringResource(R.string.whatsapp),
                onClick = {
                    val tgIntent = Intent(Intent.ACTION_VIEW).apply {
                        setDataAndType(Uri.parse("https://t.me/korolev_2512"), "text/plain")
                    }
                    launcher.launch(tgIntent)
                }
            )
        }
    }
}
