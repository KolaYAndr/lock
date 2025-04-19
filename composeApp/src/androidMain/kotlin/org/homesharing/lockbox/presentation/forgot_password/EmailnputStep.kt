package org.homesharing.lockbox.presentation.forgot_password

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import org.homesharing.lockbox.R
import org.homesharing.lockbox.presentation.general.composables.EmailInput
import org.homesharing.lockbox.presentation.general.composables.PolicyAndConditions
import org.homesharing.lockbox.presentation.general.composables.basic.BasicButton
import org.homesharing.lockbox.presentation.general.theme.LockBoxTheme

@Composable
fun EmailInputStep(
    email: String,
    emailError: String?,
    onEmailChange: (String) -> Unit,
    onConfirmEmail: () -> Unit,
    onDownloadPdf: () -> Unit,
) {
    val emailInteractionSource = remember { MutableInteractionSource() }
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) { focusRequester.requestFocus() }

    Text(
        text = stringResource(R.string.type_in_email),
        style = LockBoxTheme.typography.titleMedium,
        color = LockBoxTheme.colors.white,
        modifier = Modifier
            .padding(top = 16.dp, start = 16.dp, end = 16.dp)
    )
    Text(
        text = stringResource(R.string.we_will_send_recovery_link),
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
        keyboardActions = KeyboardActions(onDone = {}),
        isError = emailError != null,
        errorText = emailError ?: "",
        modifier = Modifier
            .focusRequester(focusRequester)
            .padding(
                top = 6.dp,
                start = 16.dp,
                end = 16.dp,
                bottom = 12.dp
            )
    )

    BasicButton(
        onClick = { onConfirmEmail() },
        text = stringResource(R.string.confirm_email),
        paddingValues = PaddingValues(14.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 12.dp, start = 16.dp, end = 16.dp)
    )

    PolicyAndConditions(
        onClick = { onDownloadPdf() },
        modifier = Modifier.padding(top = 2.dp, start = 16.dp, end = 16.dp)
    )
}
