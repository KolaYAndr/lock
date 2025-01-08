package org.homesharing.lockbox.presentation.change_email

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.homesharing.lockbox.R
import presentation.composables.EmailInput
import presentation.composables.TitledTopBar
import presentation.composables.general.BasicButton
import presentation.theme.LockBoxTheme

@Composable
fun ChangeEmailScreen(
    uiState: ChangeEmailUiState,
    onNavBack: () -> Unit,
    onToStep: (ChangeEmailStep) -> Unit,
    onEmailChange: (String) -> Unit,
    changeEmail: () -> Unit,
    onDone: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.background(LockBoxTheme.colors.grey950)
    ) {
        TitledTopBar(
            title = stringResource(R.string.change_email),
            onNavigate = onNavBack::invoke,
            modifier = Modifier.fillMaxWidth()
        )

        when (uiState.changeEmailStep) {
            ChangeEmailStep.ConfirmNewEmail -> {
                ConfirmNewEmailStep(onToStep, changeEmail)
            }
            
            ChangeEmailStep.EmailChangedSuccessfully -> {
                EmailChangedSuccessfullyStep(onDone)
            }

            ChangeEmailStep.NewEmailInput -> {
                NewEmailInputStep(uiState, onEmailChange, changeEmail, onToStep)
            }

            ChangeEmailStep.WantToChange -> {
                WantToChangeStep(onToStep, onNavBack)
            }
        }
    }
}

@Composable
private fun EmailChangedSuccessfullyStep(onDone: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
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

        BasicButton(
            text = stringResource(R.string.ready),
            modifier = Modifier.padding(top = 12.dp),
            onClick = { onDone() },
            paddingValues = PaddingValues(10.dp)
        )
    }
}

@Composable
private fun ConfirmNewEmailStep(
    onToStep: (ChangeEmailStep) -> Unit,
    changeEmail: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(R.drawable.mail),
            contentDescription = null,
            modifier = Modifier.size(48.dp),
            tint = LockBoxTheme.colors.grey500
        )

        Text(
            text = stringResource(R.string.confirm_new_email),
            style = LockBoxTheme.typography.titleMedium,
            color = LockBoxTheme.colors.grey100,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 12.dp)
        )

        Text(
            text = stringResource(R.string.we_sent_message_to_indicated_email),
            style = LockBoxTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            color = LockBoxTheme.colors.grey500,
            modifier = Modifier.padding(top = 8.dp)
        )

        BasicButton(
            text = stringResource(R.string.open_mail),
            modifier = Modifier.padding(top = 12.dp),
            onClick = { onToStep(ChangeEmailStep.EmailChangedSuccessfully) },
            paddingValues = PaddingValues(10.dp)
        )

        Text(
            text = stringResource(R.string.send_again),
            style = LockBoxTheme.typography.headlineMedium,
            color = LockBoxTheme.colors.grey100,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(top = 12.dp)
                .clickable { changeEmail() }
                .padding(10.dp)
        )
    }
}

@Composable
private fun NewEmailInputStep(
    uiState: ChangeEmailUiState,
    onEmailChange: (String) -> Unit,
    changeEmail: () -> Unit,
    onToStep: (ChangeEmailStep) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        val interactionSource = remember { MutableInteractionSource() }
        val focusManager = LocalFocusManager.current
        val keyboardController = LocalSoftwareKeyboardController.current

        Text(
            text = stringResource(R.string.input_new_email),
            style = LockBoxTheme.typography.titleMedium,
            color = LockBoxTheme.colors.grey100,
            textAlign = TextAlign.Start,
            modifier = Modifier
                .padding(top = 12.dp)
                .fillMaxWidth()
        )

        Text(
            text = stringResource(R.string.indicate_email_for_use),
            style = LockBoxTheme.typography.bodyMedium,
            color = LockBoxTheme.colors.grey500,
            textAlign = TextAlign.Start,
            modifier = Modifier
                .padding(top = 8.dp)
                .fillMaxWidth()
        )

        Text(
            text = stringResource(R.string.new_email),
            color = LockBoxTheme.colors.grey100,
            style = LockBoxTheme.typography.subHeadlineLarge,
            modifier = Modifier
                .padding(top = 24.dp)
                .fillMaxWidth()
        )

        EmailInput(
            email = uiState.newEmail,
            onValueChange = onEmailChange::invoke,
            onClearText = { onEmailChange("") },
            isError = false,
            errorText = "",
            interactionSource = interactionSource,
            keyboardActions = KeyboardActions(
                onDone = {
                    changeEmail()
                    keyboardController?.hide()
                    focusManager.clearFocus()
                    onToStep(ChangeEmailStep.ConfirmNewEmail)
                }
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                showKeyboardOnFocus = true,
                imeAction = ImeAction.Done
            )
        )

        BasicButton(
            text = stringResource(R.string.change_mail),
            paddingValues = PaddingValues(14.dp),
            onClick = {
                changeEmail()
                onToStep(ChangeEmailStep.ConfirmNewEmail)
            },
            modifier = Modifier
                .padding(top = 24.dp)
                .fillMaxWidth()
        )

        BasicButton(
            text = stringResource(R.string.cancel),
            paddingValues = PaddingValues(14.dp),
            onClick = {
                onToStep(ChangeEmailStep.WantToChange)
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = LockBoxTheme.colors.grey950,
                contentColor = LockBoxTheme.colors.grey100
            ),
            modifier = Modifier
                .padding(top = 12.dp)
                .fillMaxWidth()
        )
    }
}

@Composable
private fun WantToChangeStep(
    onToStep: (ChangeEmailStep) -> Unit,
    onNavBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(R.drawable.mail),
            contentDescription = null,
            modifier = Modifier.size(48.dp),
            tint = LockBoxTheme.colors.grey500
        )

        Text(
            text = stringResource(R.string.want_to_change),
            style = LockBoxTheme.typography.titleMedium,
            color = LockBoxTheme.colors.grey100,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 12.dp)
        )

        Text(
            text = stringResource(R.string.new_email_will_be_used_for),
            style = LockBoxTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            color = LockBoxTheme.colors.grey500,
            modifier = Modifier.padding(top = 8.dp)
        )

        BasicButton(
            text = stringResource(R.string.change_full),
            modifier = Modifier.padding(top = 12.dp),
            onClick = { onToStep(ChangeEmailStep.NewEmailInput) },
            paddingValues = PaddingValues(10.dp)
        )

        Text(
            text = stringResource(R.string.back),
            style = LockBoxTheme.typography.headlineMedium,
            color = LockBoxTheme.colors.grey100,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(top = 12.dp)
                .clickable { onNavBack() }
                .padding(10.dp)
        )
    }
}

@Preview
@Composable
fun PreviewWantToChange() {
    LockBoxTheme {
        val uiState = ChangeEmailUiState(changeEmailStep = ChangeEmailStep.WantToChange)
        ChangeEmailScreen(
            uiState = uiState,
            {}, {}, {}, {}, {},
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Preview
@Composable
fun PreviewNewEmailInput() {
    LockBoxTheme {
        val uiState = ChangeEmailUiState(changeEmailStep = ChangeEmailStep.NewEmailInput)
        ChangeEmailScreen(
            uiState = uiState,
            {}, {}, {}, {}, {},
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Preview
@Composable
fun PreviewConfirmNewEmail() {
    LockBoxTheme {
        val uiState = ChangeEmailUiState(changeEmailStep = ChangeEmailStep.ConfirmNewEmail)
        ChangeEmailScreen(
            uiState = uiState,
            {}, {}, {}, {}, {},
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Preview
@Composable
fun PreviewEmailChangedSuccessfully() {
    LockBoxTheme {
        val uiState = ChangeEmailUiState(changeEmailStep = ChangeEmailStep.EmailChangedSuccessfully)
        ChangeEmailScreen(
            uiState = uiState,
            {}, {}, {}, {}, {},
            modifier = Modifier.fillMaxSize()
        )
    }
}
