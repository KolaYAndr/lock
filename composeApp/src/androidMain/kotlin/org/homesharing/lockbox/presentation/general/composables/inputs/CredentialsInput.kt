package org.homesharing.lockbox.presentation.general.composables.inputs

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import org.homesharing.lockbox.presentation.general.theme.LockBoxTheme

@Composable
internal fun CredentialsInput(
    text: String,
    onTextChange: (String) -> Unit,
    placeholderText: String,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    modifier: Modifier = Modifier,
) {
    TextField(
        value = text,
        onValueChange = { newValue -> onTextChange(newValue) },
        modifier = modifier,
        textStyle = LockBoxTheme.typography.headlineLarge,
        keyboardActions = keyboardActions,
        keyboardOptions = keyboardOptions,
        shape = LockBoxTheme.shape.roundedCornerShape8,
        placeholder = {
            Text(
                text = placeholderText,
                style = LockBoxTheme.typography.headlineLarge,
                color = LockBoxTheme.colors.grey500
            )
        },
        singleLine = true,
        colors = TextFieldDefaults.colors(
            focusedTextColor = LockBoxTheme.colors.constantWhite,
            unfocusedTextColor = LockBoxTheme.colors.constantWhite,
            disabledTextColor = LockBoxTheme.colors.constantWhite,
            cursorColor = LockBoxTheme.colors.constantWhite,
            focusedContainerColor = LockBoxTheme.colors.grey900,
            unfocusedContainerColor = LockBoxTheme.colors.grey900,
            disabledContainerColor = LockBoxTheme.colors.grey900,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
        )
    )
}

@Preview
@Composable
private fun PreviewLighterCredentialsInput() {
    LockBoxTheme {
        CredentialsInput(
            text = "Aga",
            onTextChange = {},
            placeholderText = "Vashe imya",
        )
    }
}

@Preview
@Composable
private fun PreviewDarkerCredentialsInput() {
    LockBoxTheme {
        CredentialsInput(text = "Aga", onTextChange = {}, placeholderText = "Vashe imya")
    }
}
