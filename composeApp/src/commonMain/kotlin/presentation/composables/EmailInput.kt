package presentation.composables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.homesharing.lockbox.R
import presentation.composables.general.BasicInput
import presentation.theme.LockBoxTheme

@Composable
fun EmailInput(
    email: String,
    onValueChange: (String) -> Unit,
    onClearText: () -> Unit,
    isError: Boolean,
    errorText: String,
    interactionSource: MutableInteractionSource,
    keyboardActions: KeyboardActions,
    keyboardOptions: KeyboardOptions,
    modifier: Modifier = Modifier,
) {
    BasicInput(
        value = email,
        onValueChange = onValueChange::invoke,
        placeholderText = stringResource(R.string.email_placeholder),
        visualTransformation = VisualTransformation.None,
        keyboardOptions = keyboardOptions,
        isError = isError,
        errorText = errorText,
        keyboardActions = keyboardActions,
        trailingContent = {
            AnimatedVisibility(email.isNotEmpty()) {
                IconButton(onClick = onClearText::invoke) {
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
fun PreviewEmail() {
    LockBoxTheme {
        var value by remember { mutableStateOf("ivan@ivanov@mail.ru") }
        val interactionSource = remember { MutableInteractionSource() }

        EmailInput(
            email = value,
            onValueChange = { value = it },
            onClearText = { value = "" },
            interactionSource = interactionSource,
            keyboardActions = KeyboardActions(),
            isError = false,
            keyboardOptions = KeyboardOptions(),
            errorText = "",
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
    }
}
