package org.homesharing.lockbox.presentation.change_email

import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.homesharing.lockbox.R
import org.homesharing.lockbox.presentation.uikit.composables.TitledTopBar
import org.homesharing.lockbox.presentation.uikit.composables.core.buttons.BasicButtonStyle
import org.homesharing.lockbox.presentation.uikit.composables.core.buttons.LockboxButton
import org.homesharing.lockbox.presentation.uikit.composables.core.inputs.LockboxEmailInput
import org.homesharing.lockbox.presentation.uikit.theme.LockBoxTheme
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun ChangeEmailScreen(
    onNavBack: () -> Unit,
    changeEmailViewModel: ChangeEmailViewModel = koinViewModel(),
) {
    val changeEmailUiState by changeEmailViewModel.uiState.collectAsStateWithLifecycle()
    ChangeEmailScreen(
        uiState = changeEmailUiState,
        onNavBack = onNavBack,
        onToStep = changeEmailViewModel::toStep,
        onEmailChange = changeEmailViewModel::onEmailChange,
        changeEmail = changeEmailViewModel::changeEmail,
        modifier = Modifier.fillMaxSize()
    )
}

@Composable
private fun ChangeEmailScreen(
    uiState: ChangeEmailUiState,
    onNavBack: () -> Unit,
    onToStep: (ChangeEmailStep) -> Unit,
    onEmailChange: (String) -> Unit,
    changeEmail: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.background(LockBoxTheme.colors.grey950)
    ) {
        TitledTopBar(
            title = stringResource(R.string.change_email),
            onNavigate = onNavBack,
            modifier = Modifier.fillMaxWidth()
        )

        when (uiState.changeEmailStep) {
            ChangeEmailStep.ConfirmNewEmail -> {
                ConfirmNewEmailStep(changeEmail = changeEmail)
            }

            ChangeEmailStep.NewEmailInput -> {
                NewEmailInputStep(
                    email = uiState.newEmail,
                    emailError = uiState.emailError != null,
                    onEmailChange = onEmailChange,
                    changeEmail = changeEmail,
                    onToStep = onToStep
                )
            }

            ChangeEmailStep.WantToChange -> {
                WantToChangeStep(onToStep = onToStep, onNavBack = onNavBack)
            }
        }
    }
}

@Composable
private fun ConfirmNewEmailStep(
    changeEmail: () -> Unit,
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

        val launcher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.StartActivityForResult(),
            onResult = {}
        )

        LockboxButton(
            text = stringResource(R.string.open_mail),
            modifier = Modifier.padding(top = 12.dp),
            onClick = {
                val intent = Intent
                    .makeMainSelectorActivity(Intent.ACTION_MAIN, Intent.CATEGORY_APP_EMAIL)
                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                launcher.launch(Intent.createChooser(intent, "choose email app"))
            },
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
    email: String,
    emailError: Boolean,
    onEmailChange: (String) -> Unit,
    changeEmail: () -> Unit,
    onToStep: (ChangeEmailStep) -> Unit,
) {
    val focusRequester = remember { FocusRequester() }
    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

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

        LockboxEmailInput(
            email = email,
            onValueChange = onEmailChange,
            onClearText = { onEmailChange("") },
            errorText = if (emailError) stringResource(R.string.email_doesnt_match_pattern) else null,
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
                autoCorrectEnabled = false,
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Done,
                showKeyboardOnFocus = true
            ),
            modifier = Modifier
                .padding(top = 6.dp)
                .focusRequester(focusRequester)
        )

        LockboxButton(
            text = stringResource(R.string.change_mail),
            paddingValues = PaddingValues(14.dp),
            onClick = changeEmail,
            modifier = Modifier
                .padding(top = 24.dp)
                .fillMaxWidth()
        )

        LockboxButton(
            text = stringResource(R.string.cancel),
            paddingValues = PaddingValues(14.dp),
            onClick = {
                onToStep(ChangeEmailStep.WantToChange)
            },
            style = BasicButtonStyle.darkerGrey(),
            modifier = Modifier
                .padding(top = 12.dp)
                .fillMaxWidth()
        )
    }
}

@Composable
private fun WantToChangeStep(
    onToStep: (ChangeEmailStep) -> Unit,
    onNavBack: () -> Unit,
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
            text = stringResource(R.string.want_to_change_email_question),
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

        LockboxButton(
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
private fun PreviewWantToChange() {
    LockBoxTheme {
        val uiState = ChangeEmailUiState(changeEmailStep = ChangeEmailStep.WantToChange)
        ChangeEmailScreen(
            uiState = uiState,
            {}, {}, {}, {},
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Preview
@Composable
private fun PreviewNewEmailInput() {
    LockBoxTheme {
        val uiState = ChangeEmailUiState(changeEmailStep = ChangeEmailStep.NewEmailInput)
        ChangeEmailScreen(
            uiState = uiState,
            {}, {}, {}, {},
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Preview
@Composable
private fun PreviewConfirmNewEmail() {
    LockBoxTheme {
        val uiState = ChangeEmailUiState(changeEmailStep = ChangeEmailStep.ConfirmNewEmail)
        ChangeEmailScreen(
            uiState = uiState,
            {}, {}, {}, {},
            modifier = Modifier.fillMaxSize()
        )
    }
}
