package presentation.composables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import org.homesharing.lockbox.R
import presentation.composables.general.BasicInput
import presentation.theme.LockBoxTheme

@Composable
fun PasswordInput(
    password: String,
    onValueChange: (String) -> Unit,
    isSeen: Boolean,
    onSeenChange: () -> Unit,
    onClearText: () -> Unit,
    isError: Boolean,
    errorText: String,
    interactionSource: MutableInteractionSource,
    keyboardActions: KeyboardActions,
    keyboardOptions: KeyboardOptions,
    modifier: Modifier = Modifier,
) {
    BasicInput(
        interactionSource = interactionSource,
        value = password,
        onValueChange = onValueChange::invoke,
        visualTransformation = if (isSeen) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        trailingContent = {
            IconButton(onClick = onSeenChange::invoke) {
                if (isSeen) {
                    Icon(
                        painter = painterResource(R.drawable.eye_off),
                        null,
                        tint = LockBoxTheme.colors.grey100
                    )
                } else Icon(
                    painter = painterResource(R.drawable.eye),
                    null,
                    tint = LockBoxTheme.colors.grey100
                )
            }

            AnimatedVisibility(password.isNotEmpty()) {
                IconButton(onClick = onClearText::invoke) {
                    Icon(
                        painter = painterResource(R.drawable.close),
                        null,
                        tint = LockBoxTheme.colors.grey100
                    )
                }
            }
        },
        placeholderText = stringResource(R.string.password_placeholder),
        isError = isError,
        errorText = errorText,
        modifier = modifier
    )
}

@Composable
@Preview
fun PreviewPasswordInput() {
    LockBoxTheme {
        var value by remember { mutableStateOf("password") }
        var isSeen by remember { mutableStateOf(false) }
        val interactionSource = remember { MutableInteractionSource() }

        PasswordInput(
            password = value,
            onValueChange = { value = it },
            isSeen = isSeen,
            onSeenChange = { isSeen = !isSeen },
            onClearText = { value = "" },
            modifier = Modifier.fillMaxWidth(),
            interactionSource = interactionSource,
            isError = false,
            errorText = "",
            keyboardActions = KeyboardActions(),
            keyboardOptions = KeyboardOptions()
        )
    }
}
