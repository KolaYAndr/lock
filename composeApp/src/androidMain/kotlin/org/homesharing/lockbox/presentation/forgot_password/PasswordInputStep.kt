package org.homesharing.lockbox.presentation.forgot_password

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import org.homesharing.lockbox.R
import presentation.composables.PasswordInput
import presentation.composables.general.BasicButton
import presentation.theme.LockBoxTheme

@Composable
fun PasswordInputStep(
    password: String,
    onPasswordChange: (String) -> Unit,
    isPasswordSeen: Boolean,
    onPasswordSeenChanged: () -> Unit,
    passwordRepeated: String,
    onPasswordRepeatedChange: (String) -> Unit,
    isPasswordRepeatedSeen: Boolean,
    onPasswordRepeatedSeenChange: () -> Unit,
    passwordRepeatedError: String?,
    onSetPassword: () -> Unit,
) {
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val passwordInteractionSource = remember { MutableInteractionSource() }
    val passwordRepeatedInteractionSource = remember { MutableInteractionSource() }
    Text(
        text = stringResource(R.string.create_new_password),
        style = LockBoxTheme.typography.titleMedium,
        color = LockBoxTheme.colors.white,
        modifier = Modifier
            .padding(top = 16.dp, start = 16.dp, end = 16.dp)
    )
    Text(
        text = stringResource(R.string.password),
        style = LockBoxTheme.typography.subHeadlineLarge,
        color = LockBoxTheme.colors.grey100,
        modifier = Modifier
            .padding(top = 24.dp, start = 16.dp, end = 16.dp)
    )
    PasswordInput(
        password = password,
        onValueChange = { onPasswordChange(it) },
        onClearText = { onPasswordChange("") },
        interactionSource = passwordInteractionSource,
        isError = false,
        errorText = "",
        keyboardActions = KeyboardActions(onNext = { focusRequester.requestFocus() }),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Next
        ),
        isSeen = isPasswordSeen,
        onSeenChange = { onPasswordSeenChanged() },
        modifier = Modifier.padding(top = 6.dp, start = 16.dp, end = 16.dp)
    )

    Text(
        text = stringResource(R.string.password_confirmation),
        style = LockBoxTheme.typography.subHeadlineLarge,
        color = LockBoxTheme.colors.grey100,
        modifier = Modifier
            .padding(top = 24.dp, start = 16.dp, end = 16.dp)
    )
    PasswordInput(
        password = passwordRepeated,
        onValueChange = { onPasswordRepeatedChange(it) },
        onClearText = { onPasswordRepeatedChange("") },
        interactionSource = passwordRepeatedInteractionSource,
        isError = passwordRepeatedError != null,
        errorText = passwordRepeatedError ?: "",
        isSeen = isPasswordRepeatedSeen,
        onSeenChange = { onPasswordRepeatedSeenChange() },
        keyboardActions = KeyboardActions(
            onDone = {
                keyboardController?.hide()
                focusManager.clearFocus()
            }
        ),
        keyboardOptions = KeyboardOptions(
            autoCorrectEnabled = false,
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Done
        ),
        modifier = Modifier
            .focusRequester(focusRequester)
            .padding(top = 6.dp, start = 16.dp, end = 16.dp)
    )

    BasicButton(
        onClick = { onSetPassword() },
        text = stringResource(R.string.set_password),
        paddingValues = PaddingValues(14.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .padding(top = 24.dp, bottom = 12.dp)
    )
}
