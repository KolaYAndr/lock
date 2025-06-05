package org.homesharing.lockbox.presentation.general.composables.basic

import androidx.compose.foundation.clickable
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import org.homesharing.lockbox.presentation.general.theme.LockBoxTheme

@Composable
fun BasicInput(
    value: String,
    onValueChange: (String) -> Unit,
    enabled: Boolean,
    placeholderText: String,
    onDone: () -> Unit,
    leadingPainter: Painter? = null,
    onFieldClick: (() -> Unit)? = null,
    modifier: Modifier = Modifier,
) {
    val leadingIconSlot: (@Composable () -> Unit)? = leadingPainter?.let {
        {
            Icon(
                painter = leadingPainter,
                contentDescription = null,
                tint = if (value.isNotEmpty()) LockBoxTheme.colors.grey100 else LockBoxTheme.colors.grey500
            )
        }
    }
    TextField(
        value = value,
        onValueChange = { newValue -> onValueChange(newValue) },
        modifier = modifier.clickable { onFieldClick?.invoke() },
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
        enabled = enabled,
        readOnly = !enabled,
        placeholder = {
            Text(
                text = placeholderText,
                style = LockBoxTheme.typography.bodyLarge,
                color = LockBoxTheme.colors.grey500
            )
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            showKeyboardOnFocus = true,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = { onDone() }
        ),
        leadingIcon = leadingIconSlot
    )
}