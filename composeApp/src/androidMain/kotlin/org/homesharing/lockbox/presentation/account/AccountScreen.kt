package org.homesharing.lockbox.presentation.account

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.homesharing.lockbox.R
import org.homesharing.lockbox.navigation.routes.AccountRoutes
import presentation.composables.RoundText
import presentation.composables.TitledTopBar
import presentation.composables.general.BasicButton
import presentation.composables.general.BasicDialog
import presentation.composables.general.BasicGroup
import presentation.composables.general.BasicHorizontalDivider
import presentation.theme.LockBoxTheme

@Composable
fun AccountScreen(
    accountUiState: AccountUiState,
    onNavigateBack: () -> Unit,
    onHideDialog: () -> Unit,
    onShowDialog: () -> Unit,
    onDelete: () -> Unit,
    onNavigate: (AccountRoutes) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.background(LockBoxTheme.colors.grey950),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TitledTopBar(
            title = stringResource(R.string.account),
            onNavigate = { onNavigateBack() },
            modifier = Modifier.fillMaxWidth()
        )

        RoundText(
            text = "ИИ",
            modifier = Modifier.padding(top = 24.dp)
        )

        Text(
            text = stringResource(R.string.load_account_photo),
            style = LockBoxTheme.typography.subHeadlineLarge,
            color = LockBoxTheme.colors.grey100,
            modifier = Modifier.padding(top = 16.dp)
        )

        BasicGroup(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(top = 28.dp)
        ) {
            Text(
                text = accountUiState.name,
                style = LockBoxTheme.typography.headlineLarge,
                color = LockBoxTheme.colors.white,
                modifier = Modifier.padding(vertical = 14.dp, horizontal = 16.dp)
            )

            BasicHorizontalDivider(modifier = Modifier.fillMaxWidth())

            Text(
                text = accountUiState.surname,
                style = LockBoxTheme.typography.headlineLarge,
                color = LockBoxTheme.colors.white,
                modifier = Modifier.padding(vertical = 14.dp, horizontal = 16.dp)
            )
        }

        BasicGroup(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(top = 28.dp)
        ) {
            ContactInfo(
                title = stringResource(R.string.email),
                subtitle = accountUiState.email,
                showChangeText = true,
                modifier = Modifier.fillMaxWidth()
            ) {
                onNavigate(AccountRoutes.ChangeEmail)
            }

            BasicHorizontalDivider(modifier = Modifier.fillMaxWidth())

            ContactInfo(
                title = stringResource(R.string.phone_number),
                subtitle = accountUiState.phoneNumber.ifEmpty {
                    stringResource(R.string.phone_number_not_set)
                },
                showChangeText = false,
                modifier = Modifier.fillMaxWidth()
            ) {
                onNavigate(AccountRoutes.ChangePhoneNumber)
            }
        }

        BasicButton(
            onClick = { onShowDialog() },
            text = stringResource(R.string.delete_account),
            paddingValues = PaddingValues(14.dp),
            colors = ButtonDefaults.buttonColors(
                contentColor = LockBoxTheme.colors.red600,
                containerColor = LockBoxTheme.colors.grey900
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(top = 24.dp)
        )
    }

    if (accountUiState.showDialog) {
        BasicDialog(
            onDismiss = onHideDialog::invoke,
        ) {
            DialogContent(onHideDialog, onDelete)
        }
    }
}

@Composable
private fun DialogContent(onHideDialog: () -> Unit, onDelete: () -> Unit) {
    Text(
        text = stringResource(R.string.delete_account_question),
        style = LockBoxTheme.typography.titleMedium,
        color = LockBoxTheme.colors.grey100
    )

    Text(
        text = stringResource(R.string.not_cancellable_action),
        style = LockBoxTheme.typography.bodyMedium,
        color = LockBoxTheme.colors.grey500,
        modifier = Modifier.padding(top = 8.dp)
    )

    Row(
        modifier = Modifier
            .padding(top = 16.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.End)
    ) {
        Text(
            text = stringResource(R.string.cancel),
            style = LockBoxTheme.typography.headlineMedium,
            color = LockBoxTheme.colors.grey100,
            modifier = Modifier
                .padding(10.dp)
                .clickable { onHideDialog() }
        )

        BasicButton(
            onClick = onDelete::invoke,
            text = stringResource(R.string.delete),
            paddingValues = PaddingValues(10.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = LockBoxTheme.colors.red600,
                contentColor = LockBoxTheme.colors.grey100
            )
        )
    }
}

@Composable
private fun ContactInfo(
    title: String,
    subtitle: String,
    showChangeText: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    Row(
        modifier = modifier
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = title,
                style = LockBoxTheme.typography.headlineLarge,
                color = LockBoxTheme.colors.white
            )

            Text(
                text = subtitle,
                style = LockBoxTheme.typography.footnote,
                color = LockBoxTheme.colors.grey500
            )
        }

        if (showChangeText) {
            Text(
                text = stringResource(R.string.change_full),
                style = LockBoxTheme.typography.headlineLarge,
                color = LockBoxTheme.colors.grey500,
                modifier = Modifier.padding(horizontal = 12.dp)
            )
        }

        Icon(
            painter = painterResource(R.drawable.arrow_right),
            contentDescription = null,
            tint = LockBoxTheme.colors.grey500
        )
    }
}


@Preview
@Composable
fun PreviewSettings() {
    LockBoxTheme {
        val accountUiState = AccountUiState()
        AccountScreen(accountUiState, {}, {}, {}, {}, {}, Modifier.fillMaxSize())
    }
}
