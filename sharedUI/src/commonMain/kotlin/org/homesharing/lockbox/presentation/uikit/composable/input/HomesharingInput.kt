package org.homesharing.lockbox.presentation.uikit.composable.input

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.homesharing.lockbox.presentation.uikit.modifier.paddedBorder
import org.homesharing.lockbox.presentation.uikit.theme.HomesharingTheme

@Composable
internal fun HomesharingInput(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    isError: Boolean = false,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    singleLine: Boolean = true,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    placeholderText: String? = null,
    leadingIcon: Painter? = null,
    trailingIcon: Painter? = null,
    trailingIconAction: (() -> Unit)? = null,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()

    val borderColor by animateColorAsState(
        targetValue = if (isFocused) {
            HomesharingTheme.colors.black.copy(alpha = .5f)
        } else {
            Color.Transparent
        }
    )

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        isError = isError,
        enabled = enabled,
        readOnly = readOnly,
        interactionSource = interactionSource,
        singleLine = singleLine,
        keyboardActions = keyboardActions,
        keyboardOptions = keyboardOptions,
        shape = HomesharingTheme.shape.roundedCornerShape16,
        visualTransformation = visualTransformation,
        placeholder = {
            placeholderText?.let { placeholder ->
                Text(
                    text = placeholder,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        },
        leadingIcon = if (isFocused && leadingIcon != null) {
            {
                Icon(
                    painter = leadingIcon,
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                )
            }
        } else {
            null
        },
        trailingIcon = if (isFocused && trailingIcon != null && trailingIconAction != null) {
            {
                IconButton(
                    onClick = trailingIconAction,
                    colors = IconButtonDefaults.iconButtonColors(
                        contentColor = HomesharingTheme.colors.black.copy(alpha = .5f)
                    )
                ) {
                    Icon(
                        painter = trailingIcon,
                        contentDescription = null,
                        modifier = Modifier.size(24.dp),
                    )
                }
            }
        } else {
            null
        },
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = HomesharingTheme.colors.white,
            focusedBorderColor = HomesharingTheme.colors.black.copy(alpha = .2f),
            focusedPlaceholderColor = HomesharingTheme.colors.black.copy(alpha = .5f),
            focusedTextColor = HomesharingTheme.colors.black,
            focusedLabelColor = HomesharingTheme.colors.black.copy(alpha = .5f),
            focusedLeadingIconColor = HomesharingTheme.colors.black.copy(alpha = .3f),
            focusedTrailingIconColor = HomesharingTheme.colors.black.copy(alpha = .3f),
            cursorColor = HomesharingTheme.colors.black,
            disabledTextColor = HomesharingTheme.colors.black,
            disabledLabelColor = HomesharingTheme.colors.black.copy(alpha = .5f),
            disabledPlaceholderColor = HomesharingTheme.colors.black.copy(alpha = .5f),
            disabledBorderColor = HomesharingTheme.colors.black.copy(alpha = .5f),
            disabledLeadingIconColor = HomesharingTheme.colors.black.copy(alpha = .3f),
            disabledTrailingIconColor = HomesharingTheme.colors.black.copy(alpha = .3f),
            disabledContainerColor = HomesharingTheme.colors.black.copy(alpha = .05f),
            unfocusedTextColor = HomesharingTheme.colors.black,
            unfocusedLabelColor = HomesharingTheme.colors.black.copy(alpha = .5f),
            unfocusedPlaceholderColor = HomesharingTheme.colors.black.copy(alpha = .5f),
            unfocusedBorderColor = HomesharingTheme.colors.black.copy(alpha = .1f),
            unfocusedContainerColor = HomesharingTheme.colors.white,
            unfocusedLeadingIconColor = HomesharingTheme.colors.black.copy(alpha = .3f),
            unfocusedTrailingIconColor = HomesharingTheme.colors.black.copy(alpha = .3f),
            errorTextColor = HomesharingTheme.colors.black,
            errorLabelColor = HomesharingTheme.colors.black.copy(alpha = .5f),
            errorCursorColor = HomesharingTheme.colors.black,
            errorBorderColor = HomesharingTheme.colors.red,
            errorContainerColor = HomesharingTheme.colors.white,
            errorPlaceholderColor = HomesharingTheme.colors.black.copy(alpha = .5f),
            errorLeadingIconColor = HomesharingTheme.colors.black.copy(alpha = .3f),
            errorTrailingIconColor = HomesharingTheme.colors.black.copy(alpha = .3f)
        ),
        modifier = modifier.paddedBorder(borderColor),
    )
}

@Preview(name = "All states", showBackground = true, backgroundColor = 0xFFF5F5F0, heightDp = 560)
@Composable
private fun HomesharingInputPreview_AllStates() {
    HomesharingTheme {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Text("Empty · unfocused")
            HomesharingInput(
                value = "",
                onValueChange = {},
                placeholderText = "Email address",
                modifier = Modifier.fillMaxWidth(),
            )

            Text("Filled")
            var filled by remember { mutableStateOf("yurii@homesharing.app") }
            HomesharingInput(
                value = filled,
                onValueChange = { filled = it },
                placeholderText = "Email address",
                modifier = Modifier.fillMaxWidth(),
            )

            Text("With icons")
            var iconValue by remember { mutableStateOf("yurii@homesharing.app") }
            HomesharingInput(
                value = iconValue,
                onValueChange = { iconValue = it },
                placeholderText = "Email address",
                modifier = Modifier.fillMaxWidth(),
            )

            Text("Error")
            HomesharingInput(
                value = "not-an-email",
                onValueChange = {},
                placeholderText = "Email address",
                isError = true,
                modifier = Modifier.fillMaxWidth(),
            )

            Text("Disabled")
            HomesharingInput(
                value = "",
                onValueChange = {},
                placeholderText = "Email address",
                enabled = false,
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}