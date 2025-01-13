package org.homesharing.lockbox.presentation.add_phone_number

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
import androidx.compose.runtime.LaunchedEffect
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
import org.homesharing.lockbox.R
import presentation.composables.PhoneNumberInput
import presentation.composables.Pin
import presentation.composables.TitledTopBar
import presentation.composables.general.BasicButton
import presentation.theme.LockBoxTheme

@Composable
fun AddPhoneNumberScreen(
    uiState: AddPhoneNumberUiState,
    onNavBack: () -> Unit,
    onToStep: (AddPhoneNumberStep) -> Unit,
    onNumberChange: (String) -> Unit,
    onConfirmationCodeChanged: (String) -> Unit,
    onConfirm: () -> Unit,
    onSendCode: () -> Unit,
    onDone: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.background(LockBoxTheme.colors.grey950)
    ) {
        TitledTopBar(
            title = stringResource(R.string.change_phone_number),
            onNavigate = onNavBack::invoke,
            modifier = Modifier.fillMaxWidth()
        )

        when (uiState.step) {
            AddPhoneNumberStep.ConfirmPhoneNumber -> {
                ConfirmNumberStep(
                    uiState.confirmationCode,
                    onConfirmationCodeChanged,
                    onConfirm,
                    onToStep,
                    onSendCode
                )
            }

            AddPhoneNumberStep.NewNumberInput -> {
                NewNumberInputStep(uiState.phoneNumber, onNumberChange, onToStep, onSendCode)
            }

            AddPhoneNumberStep.PhoneNumberChangedSuccessfully -> {
                PhoneNumberChangedSuccessfullyStep(onDone)
            }

            AddPhoneNumberStep.WantToChange -> {
                WantToChangeStep(onNavBack, onToStep)
            }
        }
    }
}

@Composable
fun ConfirmNumberStep(
    value: String,
    onCodeChange: (String) -> Unit,
    onConfirm: () -> Unit,
    onToStep: (AddPhoneNumberStep) -> Unit,
    onSendAgain: () -> Unit,
) {
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 32.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(R.drawable.phone),
            contentDescription = null,
            tint = LockBoxTheme.colors.grey500,
            modifier = Modifier
                .size(48.dp)
                .padding(top = 48.dp)
        )

        Text(
            text = stringResource(R.string.confirm_phone_number),
            style = LockBoxTheme.typography.titleMedium,
            color = LockBoxTheme.colors.grey100,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 12.dp)
        )

        Text(
            text = stringResource(R.string.we_sent_message_to_indicated_number),
            style = LockBoxTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            color = LockBoxTheme.colors.grey500,
            modifier = Modifier.padding(top = 8.dp)
        )

        Pin(
            value = value,
            pinLength = 6,
            onPinChange = onCodeChange,
            onConfirm = {
                onConfirm()
                onToStep(AddPhoneNumberStep.PhoneNumberChangedSuccessfully)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 32.dp)
                .focusRequester(focusRequester)
        )

        BasicButton(
            onClick = {
                onConfirm()
                onToStep(AddPhoneNumberStep.PhoneNumberChangedSuccessfully)
            },
            text = stringResource(R.string.confirm),
            paddingValues = PaddingValues(10.dp),
            modifier = Modifier.padding(top = 42.dp)
        )

        BasicButton(
            text = stringResource(R.string.send_again),
            paddingValues = PaddingValues(10.dp),
            onClick = {
                onSendAgain()
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = LockBoxTheme.colors.grey950,
                contentColor = LockBoxTheme.colors.grey100
            ),
            modifier = Modifier.padding(top = 12.dp)
        )
    }
}

@Composable
fun PhoneNumberChangedSuccessfullyStep(onDone: () -> Unit) {
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
            text = stringResource(R.string.phone_number_successfully_changed),
            style = LockBoxTheme.typography.titleMedium,
            color = LockBoxTheme.colors.grey100,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 12.dp)
        )

        Text(
            text = stringResource(R.string.now_you_can_use_new_phone_number),
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
fun NewNumberInputStep(
    number: String,
    onNumberChange: (String) -> Unit,
    onToStep: (AddPhoneNumberStep) -> Unit,
    changePhoneNumber: () -> Unit
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
            text = stringResource(R.string.add_new_phone_number_suggestion),
            style = LockBoxTheme.typography.titleMedium,
            color = LockBoxTheme.colors.grey100,
            textAlign = TextAlign.Start,
            modifier = Modifier
                .padding(top = 12.dp)
                .fillMaxWidth()
        )

        Text(
            text = stringResource(R.string.indicate_phone_number_for_use),
            style = LockBoxTheme.typography.bodyMedium,
            color = LockBoxTheme.colors.grey500,
            textAlign = TextAlign.Start,
            modifier = Modifier
                .padding(top = 8.dp)
                .fillMaxWidth()
        )

        Text(
            text = stringResource(R.string.phone_number),
            color = LockBoxTheme.colors.grey100,
            style = LockBoxTheme.typography.subHeadlineLarge,
            modifier = Modifier
                .padding(top = 24.dp)
                .fillMaxWidth()
        )

        PhoneNumberInput(
            number = number,
            onValueChange = onNumberChange::invoke,
            onClearText = { onNumberChange("") },
            isError = false,
            errorText = "",
            interactionSource = interactionSource,
            keyboardActions = KeyboardActions(
                onDone = {
                    changePhoneNumber()
                    keyboardController?.hide()
                    focusManager.clearFocus()
                    onToStep(AddPhoneNumberStep.ConfirmPhoneNumber)
                }
            ),
            keyboardOptions = KeyboardOptions(
                autoCorrectEnabled = false,
                keyboardType = KeyboardType.Phone,
                imeAction = ImeAction.Done,
                showKeyboardOnFocus = true
            ),
            modifier = Modifier
                .padding(top = 6.dp)
                .focusRequester(focusRequester)
        )

        BasicButton(
            text = stringResource(R.string.add_phone_number),
            paddingValues = PaddingValues(14.dp),
            onClick = {
                changePhoneNumber()
                onToStep(AddPhoneNumberStep.ConfirmPhoneNumber)
            },
            modifier = Modifier
                .padding(top = 24.dp)
                .fillMaxWidth()
        )

        BasicButton(
            text = stringResource(R.string.cancel),
            paddingValues = PaddingValues(14.dp),
            onClick = {
                onToStep(AddPhoneNumberStep.WantToChange)
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
private fun WantToChangeStep(onNavBack: () -> Unit, onToStep: (AddPhoneNumberStep) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(R.drawable.phone),
            contentDescription = null,
            modifier = Modifier.size(48.dp),
            tint = LockBoxTheme.colors.grey500
        )

        Text(
            text = stringResource(R.string.want_to_change_phone_number_question),
            style = LockBoxTheme.typography.titleMedium,
            color = LockBoxTheme.colors.grey100,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 12.dp)
        )

        Text(
            text = stringResource(R.string.new_phone_number_will_be_used_for),
            style = LockBoxTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            color = LockBoxTheme.colors.grey500,
            modifier = Modifier.padding(top = 8.dp)
        )

        BasicButton(
            text = stringResource(R.string.change_full),
            modifier = Modifier.padding(top = 12.dp),
            onClick = { onToStep(AddPhoneNumberStep.NewNumberInput) },
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
fun PreviewConfirmNumberStep() {
    LockBoxTheme {
        val uiState =
            AddPhoneNumberUiState(step = AddPhoneNumberStep.ConfirmPhoneNumber)
        AddPhoneNumberScreen(uiState, {}, {}, {}, {}, {}, {}, {}, Modifier.fillMaxSize())
    }
}


@Preview
@Composable
fun PreviewSuccessfullyChangedStep() {
    LockBoxTheme {
        val uiState =
            AddPhoneNumberUiState(step = AddPhoneNumberStep.PhoneNumberChangedSuccessfully)
        AddPhoneNumberScreen(uiState, {}, {}, {}, {}, {}, {}, {}, Modifier.fillMaxSize())
    }
}


@Preview
@Composable
fun PreviewWantToChangeStep() {
    LockBoxTheme {
        val uiState = AddPhoneNumberUiState()
        AddPhoneNumberScreen(uiState, {}, {}, {}, {}, {}, {}, {}, Modifier.fillMaxSize())
    }
}

@Preview
@Composable
fun PreviewInputStep() {
    LockBoxTheme {
        val uiState = AddPhoneNumberUiState(step = AddPhoneNumberStep.NewNumberInput)
        AddPhoneNumberScreen(uiState, {}, {}, {}, {}, {}, {}, {}, Modifier.fillMaxSize())
    }
}