package presentation.composables

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
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.homesharing.lockbox.R
import presentation.composables.general.BasicHorizontalDivider
import presentation.composables.general.BasicModal
import presentation.theme.LockBoxTheme

@Composable
fun HelpModalSheet(onDismiss: () -> Unit, modifier: Modifier = Modifier) {
    val emailLauncher =
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
                modifier = Modifier.padding(top = 8.dp)
            )

            val email = stringResource(R.string.lockbox_email)
            IconTextArrow(
                modifier = Modifier.padding(top = 48.dp).fillMaxWidth(),
                painter = painterResource(R.drawable.mail),
                text = email,
                onClick = {
                    val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
                        data = Uri.parse("mailto:")
                        putExtra(Intent.EXTRA_EMAIL, email)
                    }
                    emailLauncher.launch(emailIntent)
                }
            )

            BasicHorizontalDivider(modifier = Modifier.fillMaxWidth())

            val uriHandler = LocalUriHandler.current
            IconTextArrow(
                modifier = Modifier.fillMaxWidth(),
                painter = painterResource(R.drawable.telegram),
                text = stringResource(R.string.telegram),
                onClick = {
                    uriHandler.openUri("https://t.me/@korolev_2512")
                }
            )

            BasicHorizontalDivider(modifier = Modifier.fillMaxWidth())

            IconTextArrow(
                modifier = Modifier.fillMaxWidth(),
                painter = painterResource(R.drawable.phone),
                text = stringResource(R.string.whatsapp),
                onClick = {
                    uriHandler.openUri("https://t.me/@korolev_2512")
                }
            )
        }
    }
}
