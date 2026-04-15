package org.homesharing.lockbox.presentation.sign_up.steps

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import org.homesharing.lockbox.R
import org.homesharing.lockbox.presentation.sign_up.viewmodel.PasswordRepeatedError
import org.homesharing.lockbox.presentation.uikit.composables.core.buttons.LockboxButton
import org.homesharing.lockbox.presentation.uikit.composables.core.inputs.LockboxPasswordInput
import org.homesharing.lockbox.presentation.uikit.theme.LockBoxTheme

@Composable
internal fun PasswordInputStep(
    password: String,
    onPasswordChange: (String) -> Unit,
    passwordRepeated: String,
    passwordRepeatedError: PasswordRepeatedError?,
    onPasswordRepeatedChange: (String) -> Unit,
    onCreate: () -> Unit,
) {
    val passwordFocusRequester = remember { FocusRequester() }
    val passwordRepeatedFocusRequester = remember { FocusRequester() }
    val passwordInteractionSource = remember { MutableInteractionSource() }
    val passwordRepeatedInteractionSource = remember { MutableInteractionSource() }

    var isPasswordSeen by remember { mutableStateOf(false) }
    var isPasswordRepeatedSeen by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        passwordFocusRequester.requestFocus()
    }

    Text(
        text = stringResource(R.string.create_password),
        style = LockBoxTheme.typography.titleMedium,
        color = LockBoxTheme.colors.constantWhite,
        modifier = Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp)
    )

    Text(
        text = stringResource(R.string.password),
        style = LockBoxTheme.typography.subHeadlineLarge,
        color = LockBoxTheme.colors.grey100,
        modifier = Modifier.padding(top = 24.dp, start = 16.dp, end = 16.dp)
    )
    LockboxPasswordInput(
        password = password,
        onValueChange = { onPasswordChange(it) },
        onClearText = { onPasswordChange("") },
        interactionSource = passwordInteractionSource,
        errorText = null,
        keyboardActions = KeyboardActions(onNext = { passwordRepeatedFocusRequester.requestFocus() }),
        keyboardOptions = KeyboardOptions(
            autoCorrectEnabled = false,
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Next
        ),
        isSeen = isPasswordSeen,
        onSeenChange = { isPasswordSeen = !isPasswordSeen },
        modifier = Modifier
            .focusRequester(passwordFocusRequester)
            .padding(top = 6.dp, start = 16.dp, end = 16.dp)
    )

    Text(
        text = stringResource(R.string.password_confirmation),
        style = LockBoxTheme.typography.subHeadlineLarge,
        color = LockBoxTheme.colors.grey100,
        modifier = Modifier.padding(top = 24.dp, start = 16.dp, end = 16.dp)
    )
    LockboxPasswordInput(
        password = passwordRepeated,
        onValueChange = { onPasswordRepeatedChange(it) },
        onClearText = { onPasswordRepeatedChange("") },
        interactionSource = passwordRepeatedInteractionSource,
        errorText = passwordRepeatedError?.let {parsePasswordRepeatedErrorText(it) },
        isSeen = isPasswordRepeatedSeen,
        onSeenChange = { isPasswordRepeatedSeen = !isPasswordRepeatedSeen },
        keyboardActions = KeyboardActions(onDone = { onCreate() }),
        keyboardOptions = KeyboardOptions(
            autoCorrectEnabled = false,
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Done
        ),
        modifier = Modifier
            .focusRequester(passwordRepeatedFocusRequester)
            .padding(top = 6.dp, start = 16.dp, end = 16.dp)
    )

    LockboxButton(
        onClick = onCreate,
        text = stringResource(R.string.set_password),
        paddingValues = PaddingValues(14.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .padding(top = 24.dp, bottom = 12.dp)
    )
}

@Composable
@ReadOnlyComposable
private fun parsePasswordRepeatedErrorText(passwordRepeatedError: PasswordRepeatedError) =
    stringResource(
        when (passwordRepeatedError) {
            PasswordRepeatedError.PasswordsDontMatch -> R.string.passwords_dont_match
            PasswordRepeatedError.Unexpected -> R.string.unknown_error
            PasswordRepeatedError.UserExists -> R.string.user_already_exists
        }
    )
