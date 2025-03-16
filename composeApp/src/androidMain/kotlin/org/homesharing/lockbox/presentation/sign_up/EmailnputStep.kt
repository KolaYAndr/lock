package org.homesharing.lockbox.presentation.sign_up

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import org.homesharing.lockbox.R
import presentation.composables.EmailInput
import presentation.composables.PolicyAndConditions
import presentation.composables.general.BasicButton
import presentation.theme.LockBoxTheme

@Composable
fun EmailInputStep(
    email: String,
    emailError: String?,
    onEmailChange: (String) -> Unit,
    toNextStep: () -> Unit,
    onSignIn: () -> Unit,
    onDownload: () -> Unit,
) {
    val emailInteractionSource = remember { MutableInteractionSource() }
    val emailFocusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    val onToNextStep = {
        keyboardController?.hide()
        focusManager.clearFocus()
        toNextStep()
    }

    LaunchedEffect(Unit) { emailFocusRequester.requestFocus() }

    Text(
        text = stringResource(R.string.type_in_email),
        style = LockBoxTheme.typography.titleMedium,
        color = LockBoxTheme.colors.white,
        modifier = Modifier
            .padding(top = 16.dp, start = 16.dp, end = 16.dp)
    )
    Text(
        text = stringResource(R.string.promise_to_text_only_important_info),
        style = LockBoxTheme.typography.bodyMedium,
        color = LockBoxTheme.colors.grey500,
        modifier = Modifier
            .padding(top = 8.dp, start = 16.dp, end = 16.dp, bottom = 12.dp)
    )

    Text(
        text = stringResource(R.string.email),
        style = LockBoxTheme.typography.subHeadlineLarge,
        color = LockBoxTheme.colors.grey100,
        modifier = Modifier
            .padding(top = 12.dp, start = 16.dp, end = 16.dp)
    )
    EmailInput(
        email = email,
        onValueChange = { onEmailChange(it) },
        onClearText = { onEmailChange("") },
        interactionSource = emailInteractionSource,
        keyboardOptions = KeyboardOptions(
            autoCorrectEnabled = false,
            keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(onDone = { onToNextStep() }
        ),
        isError = emailError != null,
        errorText = emailError ?: "",
        modifier = Modifier
            .focusRequester(emailFocusRequester)
            .padding(
                top = 6.dp,
                start = 16.dp,
                end = 16.dp,
                bottom = 12.dp
            )
    )

    BasicButton(
        onClick = onToNextStep,
        text = stringResource(R.string.confirm_email),
        paddingValues = PaddingValues(14.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 12.dp, start = 16.dp, end = 16.dp)
    )

    PolicyAndConditions(
        onClick = { onDownload() },
        modifier = Modifier.padding(top = 2.dp, start = 16.dp, end = 16.dp)
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally)
    ) {
        Text(
            text = stringResource(R.string.have_an_account),
            color = LockBoxTheme.colors.grey500,
            style = LockBoxTheme.typography.subHeadlineMedium,
            modifier = Modifier.padding(vertical = 14.dp)
        )

        Text(
            text = stringResource(R.string.log_in),
            color = LockBoxTheme.colors.grey100,
            style = LockBoxTheme.typography.subHeadlineLarge,
            textDecoration = TextDecoration.Underline,
            modifier = Modifier
                .padding(vertical = 14.dp)
                .clickable { onSignIn() }
        )
    }
}
