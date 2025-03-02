package presentation.composables

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import presentation.theme.LockBoxTheme

@Composable
fun LockBoxPropertyInput(
    value: String,
    onValueChange: (String) -> Unit,
    onDone: () -> Unit,
    modifier: Modifier = Modifier
) {
    TextField(
        value = value,
        onValueChange = { newValue -> onValueChange(newValue) },
        modifier = modifier,
        textStyle = LockBoxTheme.typography.bodyLarge,
        shape = LockBoxTheme.shape.roundedCornerShape8,
        colors = TextFieldDefaults.colors(
            focusedTextColor = LockBoxTheme.colors.grey100,
            unfocusedTextColor = LockBoxTheme.colors.grey100,
            disabledTextColor = LockBoxTheme.colors.grey100,
            cursorColor = LockBoxTheme.colors.white,
            focusedContainerColor = LockBoxTheme.colors.grey800,
            unfocusedContainerColor = LockBoxTheme.colors.grey800,
            disabledContainerColor = LockBoxTheme.colors.grey800,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
        ),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            showKeyboardOnFocus = true,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = { onDone() }
        )
    )
}