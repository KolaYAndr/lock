package org.homesharing.lockbox.presentation.account

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts.PickVisualMedia
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.homesharing.lockbox.R
import org.homesharing.lockbox.navigation.routes.AccountRoutes
import presentation.composables.CredentialsInput
import presentation.composables.NetworkImage
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
    onChangeName: (String) -> Unit,
    onChangeSurname: (String) -> Unit,
    onSendImage: (ByteArray) -> Unit,
    onNavigateBack: () -> Unit,
    onDelete: () -> Unit,
    onNavigate: (AccountRoutes) -> Unit,
    modifier: Modifier = Modifier,
) {
    var showDeleteAccountDialog by remember { mutableStateOf(false) }
    val surnameFocusRequester = remember { FocusRequester() }
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(
        contract = PickVisualMedia(),
        onResult = { uri ->
            uri?.let { path ->
                context.contentResolver.openInputStream(path).use { stream ->
                    stream?.readBytes()?.let { data ->
                        onSendImage(data)
                    }
                }
            }
        }
    )

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
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .clickable {
                    launcher.launch(PickVisualMediaRequest(PickVisualMedia.ImageOnly))
                }
                .padding(top = 24.dp)
        ) {
            if (accountUiState.imageUri.isNotEmpty()) NetworkImage(url = accountUiState.imageUri)
            else RoundText(text = "${accountUiState.name} ${accountUiState.surname}")

            Text(
                text = stringResource(R.string.load_account_photo),
                style = LockBoxTheme.typography.subHeadlineLarge,
                color = LockBoxTheme.colors.grey100,
                modifier = Modifier.padding(top = 16.dp)
            )
        }


        BasicGroup(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(top = 28.dp)
        ) {
            CredentialsInput(
                text = accountUiState.name,
                onTextChange = { newValue -> onChangeName(newValue) },
                placeholderText = stringResource(R.string.input_name),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next,
                    showKeyboardOnFocus = true
                ),
                keyboardActions = KeyboardActions(onNext = {
                    surnameFocusRequester.requestFocus()
                }),
                modifier = Modifier.fillMaxWidth()
            )

            BasicHorizontalDivider(modifier = Modifier.fillMaxWidth())

            CredentialsInput(
                text = accountUiState.surname,
                onTextChange = { newValue -> onChangeSurname(newValue) },
                placeholderText = stringResource(R.string.input_surname),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done,
                    showKeyboardOnFocus = true
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(surnameFocusRequester)
            )
        }

        BasicGroup(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(top = 28.dp)
        ) {
            ContactInfo(title = stringResource(R.string.email),
                subtitle = accountUiState.email.ifEmpty {
                    stringResource(R.string.email_not_set)
                },
                isValueSet = accountUiState.email.isNotEmpty(),
                modifier = Modifier.fillMaxWidth(),
                onClick = { onNavigate(AccountRoutes.ChangeEmail) })

            BasicHorizontalDivider(modifier = Modifier.fillMaxWidth())

            ContactInfo(title = stringResource(R.string.phone_number),
                subtitle = accountUiState.phoneNumber.ifEmpty {
                    stringResource(R.string.phone_number_not_set)
                },
                isValueSet = accountUiState.phoneNumber.isNotEmpty(),
                modifier = Modifier.fillMaxWidth(),
                onClick = { onNavigate(AccountRoutes.ChangePhoneNumber) })
        }

        BasicButton(
            onClick = { showDeleteAccountDialog = true },
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

    if (showDeleteAccountDialog) {
        BasicDialog(
            onDismiss = { showDeleteAccountDialog = false },
        ) {
            DeleteAccountDialog(onHideDialog = { showDeleteAccountDialog = false }, onDelete = onDelete)
        }
    }
}

@Composable
private fun DeleteAccountDialog(onHideDialog: () -> Unit, onDelete: () -> Unit) {
    Text(
        text = stringResource(R.string.delete_account_question),
        style = LockBoxTheme.typography.titleMedium,
        color = LockBoxTheme.colors.grey100
    )

    Text(
        text = stringResource(R.string.not_cancellable_delete_account),
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
        Text(text = stringResource(R.string.cancel),
            style = LockBoxTheme.typography.headlineMedium,
            color = LockBoxTheme.colors.grey100,
            modifier = Modifier
                .padding(10.dp)
                .clickable { onHideDialog() })

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
    isValueSet: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(modifier = modifier
        .clickable { onClick() }
        .padding(horizontal = 16.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = title,
                style = LockBoxTheme.typography.headlineLarge,
                color = LockBoxTheme.colors.white
            )

            if (subtitle.isNotEmpty()) {
                Text(
                    text = subtitle,
                    style = LockBoxTheme.typography.footnote,
                    color = LockBoxTheme.colors.grey500
                )
            }
        }

        Text(
            text = if (isValueSet) stringResource(R.string.change_full)
            else stringResource(R.string.add_full),
            style = LockBoxTheme.typography.headlineLarge,
            color = LockBoxTheme.colors.grey500,
            modifier = Modifier.padding(horizontal = 12.dp)
        )


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
        AccountScreen(
            accountUiState,
            {},
            {},
            {},
            {},
            {},
            {},
            Modifier.fillMaxSize()
        )
    }
}
