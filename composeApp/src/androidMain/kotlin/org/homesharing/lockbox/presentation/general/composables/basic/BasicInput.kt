package org.homesharing.lockbox.presentation.general.composables.basic

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import org.homesharing.lockbox.presentation.general.theme.LockBoxTheme

@Composable
fun BasicInput(
    value: String,
    onValueChange: (String) -> Unit,
    placeholderText: String,
    trailingContent: @Composable RowScope.() -> Unit,
    visualTransformation: VisualTransformation,
    interactionSource: MutableInteractionSource,
    isError: Boolean,
    errorText: String = "",
    keyboardOptions: KeyboardOptions,
    keyboardActions: KeyboardActions,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(6.dp),
        horizontalAlignment = Alignment.Start
    ) {
        val isFocused = interactionSource.collectIsFocusedAsState()
        val borderModifier = if (isError) Modifier.border(
            width = 1.dp,
            color = LockBoxTheme.colors.red600,
            shape = LockBoxTheme.shape.roundedCornerShape8
        ) else if (isFocused.value) Modifier.border(
            width = 1.dp,
            color = LockBoxTheme.colors.grey500,
            shape = LockBoxTheme.shape.roundedCornerShape8
        ) else Modifier

        TextField(
            interactionSource = interactionSource,
            value = value,
            onValueChange = onValueChange::invoke,
            singleLine = true,
            visualTransformation = visualTransformation,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            trailingIcon = {
                if (isFocused.value) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.End
                    ) {
                        trailingContent()
                    }
                }
            },
            placeholder = {
                Text(
                    text = placeholderText,
                    style = LockBoxTheme.typography.bodyLarge,
                    color = LockBoxTheme.colors.grey500
                )
            },
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
            modifier = borderModifier.fillMaxWidth()
        )
        AnimatedVisibility(isError) {
            Text(
                text = errorText,
                style = LockBoxTheme.typography.footnote,
                color = LockBoxTheme.colors.red600,
                maxLines = 1
            )
        }
    }
}
