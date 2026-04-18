package org.homesharing.lockbox.presentation.uikit.composables.core.inputs

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
import org.homesharing.lockbox.presentation.uikit.theme.LockBoxTheme

@Composable
internal fun LockboxPasswordInput(
    password: String,
    onValueChange: (String) -> Unit,
    isSeen: Boolean,
    onSeenChange: () -> Unit,
    onClearText: () -> Unit,
    errorText: String?,
    modifier: Modifier = Modifier,
    keyboardActions: KeyboardActions = KeyboardActions(),
    keyboardOptions: KeyboardOptions = KeyboardOptions(),
) {
    val interactionSource = remember { MutableInteractionSource() }
    LockboxInput(
        interactionSource = interactionSource,
        value = password,
        onValueChange = onValueChange,
        visualTransformation = if (isSeen) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        trailingContent = {
            IconButton(
                onClick = onSeenChange
            ) {
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
                IconButton(
                    onClick = onClearText
                ) {
                    Icon(
                        painter = painterResource(R.drawable.close),
                        null,
                        tint = LockBoxTheme.colors.grey100
                    )
                }
            }
        },
        placeholderText = stringResource(R.string.password_placeholder),
        errorText = errorText,
        modifier = modifier
    )
}

@Composable
@Preview
private fun PreviewLockboxPasswordInput() {
    LockBoxTheme {
        var isSeen by remember { mutableStateOf(false) }

        LockboxPasswordInput(
            password = "1234",
            onValueChange = { },
            isSeen = isSeen,
            onSeenChange = { isSeen = !isSeen },
            onClearText = { },
            modifier = Modifier.fillMaxWidth(),
            errorText = "error",
            keyboardActions = KeyboardActions(),
            keyboardOptions = KeyboardOptions()
        )
    }
}
