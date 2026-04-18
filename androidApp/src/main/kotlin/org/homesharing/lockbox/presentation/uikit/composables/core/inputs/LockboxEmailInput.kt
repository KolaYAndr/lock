package org.homesharing.lockbox.presentation.uikit.composables.core.inputs

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.homesharing.lockbox.R
import org.homesharing.lockbox.presentation.uikit.theme.LockBoxTheme

@Composable
internal fun LockboxEmailInput(
    email: String,
    onValueChange: (String) -> Unit,
    onClearText: () -> Unit,
    errorText: String?,
    keyboardActions: KeyboardActions,
    keyboardOptions: KeyboardOptions,
    modifier: Modifier = Modifier,
) {
    val interactionSource = remember { MutableInteractionSource() }
    LockboxInput(
        value = email,
        onValueChange = onValueChange,
        placeholderText = stringResource(R.string.email_placeholder),
        visualTransformation = VisualTransformation.None,
        keyboardOptions = keyboardOptions,
        errorText = errorText,
        keyboardActions = keyboardActions,
        trailingContent = {
            AnimatedVisibility(email.isNotEmpty()) {
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
        interactionSource = interactionSource,
        modifier = modifier
    )
}

@Composable
@Preview
private fun PreviewLockboxEmail() {
    LockBoxTheme {
        LockboxEmailInput(
            email = "ivan@ivanov@mail.ru",
            onValueChange = { },
            onClearText = { },
            keyboardActions = KeyboardActions(),
            keyboardOptions = KeyboardOptions(),
            errorText = null,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
    }
}
