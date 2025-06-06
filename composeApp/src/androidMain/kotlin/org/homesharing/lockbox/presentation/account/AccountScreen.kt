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
import org.homesharing.lockbox.presentation.general.composables.inputs.CredentialsInput
import org.homesharing.lockbox.presentation.general.composables.NetworkImage
import org.homesharing.lockbox.presentation.general.composables.RoundText
import org.homesharing.lockbox.presentation.general.composables.TitledTopBar
import org.homesharing.lockbox.presentation.general.composables.basic.BasicButton
import org.homesharing.lockbox.presentation.general.composables.basic.BasicButtonStyle
import org.homesharing.lockbox.presentation.general.composables.basic.BasicDialog
import org.homesharing.lockbox.presentation.general.composables.basic.BasicDialogContent
import org.homesharing.lockbox.presentation.general.composables.basic.BasicGroup
import org.homesharing.lockbox.presentation.general.composables.basic.BasicHorizontalDivider
import org.homesharing.lockbox.presentation.general.theme.LockBoxTheme

@Composable
internal fun AccountScreen(
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
            if (accountUiState.imageUrl.isNotEmpty()) NetworkImage(url = accountUiState.imageUrl)
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
            ContactInfo(
                title = stringResource(R.string.email),
                subtitle = accountUiState.email.ifEmpty {
                    stringResource(R.string.email_not_set)
                },
                isValueSet = accountUiState.email.isNotEmpty(),
                modifier = Modifier.fillMaxWidth(),
                onClick = { onNavigate(AccountRoutes.ChangeEmail) })

//            BasicHorizontalDivider(modifier = Modifier.fillMaxWidth())
//            TODO: вернуть, когда будет телефон
//            ContactInfo(title = stringResource(R.string.phone_number),
//                subtitle = accountUiState.phoneNumber.ifEmpty {
//                    stringResource(R.string.phone_number_not_set)
//                },
//                isValueSet = accountUiState.phoneNumber.isNotEmpty(),
//                modifier = Modifier.fillMaxWidth(),
//                onClick = { onNavigate(AccountRoutes.ChangePhoneNumber) })
        }

        BasicButton(
            onClick = { showDeleteAccountDialog = true },
            text = stringResource(R.string.delete_account),
            paddingValues = PaddingValues(14.dp),
            style = BasicButtonStyle.red(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(top = 24.dp)
        )
    }

    if (showDeleteAccountDialog) {
        DeleteAccountDialog(
            onHideDialog = { showDeleteAccountDialog = false },
            onDelete = onDelete
        )
    }
}

@Composable
private fun DeleteAccountDialog(
    onHideDialog: () -> Unit,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier,
) {
    BasicDialog(
        onDismiss = onHideDialog,
        modifier = modifier
    ) {
        BasicDialogContent(
            title = stringResource(R.string.delete_account_question),
            subtitle = stringResource(R.string.not_cancellable_delete_account),
            onDismiss = onHideDialog,
            onDangerButtonAction = {
                onDelete()
                onHideDialog()
            },
            dangerButtonText = stringResource(R.string.delete)
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
    Row(
        modifier = modifier
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
private fun PreviewAccount() {
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
