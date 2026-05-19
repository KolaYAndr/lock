//package org.homesharing.lockbox.presentation.uikit.composables.core.inputs
//
//import androidx.compose.animation.AnimatedVisibility
//import androidx.compose.foundation.border
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.interaction.MutableInteractionSource
//import androidx.compose.foundation.interaction.collectIsFocusedAsState
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.RowScope
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.text.KeyboardActions
//import androidx.compose.foundation.text.KeyboardOptions
//import androidx.compose.material3.Icon
//import androidx.compose.material3.Text
//import androidx.compose.material3.TextField
//import androidx.compose.material3.TextFieldDefaults
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.remember
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.graphics.painter.Painter
//import androidx.compose.ui.text.input.ImeAction
//import androidx.compose.ui.text.input.KeyboardType
//import androidx.compose.ui.text.input.VisualTransformation
//import androidx.compose.ui.unit.dp
//import org.homesharing.lockbox.presentation.uikit.theme.LockBoxTheme
//
//@Composable
//internal fun LockboxInput(
//    value: String,
//    onValueChange: (String) -> Unit,
//    enabled: Boolean,
//    placeholderText: String,
//    modifier: Modifier = Modifier,
//    leadingPainter: Painter? = null,
//    onFieldClick: (() -> Unit)? = null,
//    keyboardOptions: KeyboardOptions = KeyboardOptions(
//        keyboardType = KeyboardType.Text,
//        showKeyboardOnFocus = true,
//        imeAction = ImeAction.Done
//    ),
//    keyboardActions: KeyboardActions = KeyboardActions(),
//    singleLine: Boolean = false,
//) {
//    val leadingIconSlot: (@Composable () -> Unit)? = leadingPainter?.let {
//        {
//            Icon(
//                painter = leadingPainter,
//                contentDescription = null,
//                tint = if (value.isNotEmpty()) LockBoxTheme.colors.grey100 else LockBoxTheme.colors.grey500
//            )
//        }
//    }
//    TextField(
//        value = value,
//        onValueChange = { newValue -> onValueChange(newValue) },
//        modifier = modifier.clickable { onFieldClick?.invoke() },
//        textStyle = LockBoxTheme.typography.bodyLarge,
//        shape = LockBoxTheme.shape.roundedCornerShape8,
//        colors = TextFieldDefaults.colors(
//            focusedTextColor = LockBoxTheme.colors.grey100,
//            unfocusedTextColor = LockBoxTheme.colors.grey100,
//            disabledTextColor = LockBoxTheme.colors.grey100,
//            cursorColor = LockBoxTheme.colors.constantWhite,
//            focusedContainerColor = LockBoxTheme.colors.grey800,
//            unfocusedContainerColor = LockBoxTheme.colors.grey800,
//            disabledContainerColor = LockBoxTheme.colors.grey800,
//            focusedIndicatorColor = Color.Transparent,
//            unfocusedIndicatorColor = Color.Transparent,
//        ),
//        enabled = enabled,
//        readOnly = !enabled,
//        placeholder = {
//            Text(
//                text = placeholderText,
//                style = LockBoxTheme.typography.bodyLarge,
//                color = LockBoxTheme.colors.grey500
//            )
//        },
//        keyboardOptions = keyboardOptions,
//        keyboardActions = keyboardActions,
//        leadingIcon = leadingIconSlot,
//        singleLine = singleLine
//    )
//}
//
//
//@Composable
//internal fun LockboxInput(
//    value: String,
//    onValueChange: (String) -> Unit,
//    placeholderText: String,
//    trailingContent: @Composable RowScope.() -> Unit,
//    visualTransformation: VisualTransformation,
//    modifier: Modifier = Modifier,
//    errorText: String? = null,
//    keyboardOptions: KeyboardOptions = KeyboardOptions(),
//    keyboardActions: KeyboardActions = KeyboardActions(),
//) {
//    val interactionSource = remember { MutableInteractionSource() }
//    Column(
//        modifier = modifier,
//        verticalArrangement = Arrangement.spacedBy(6.dp),
//        horizontalAlignment = Alignment.Start
//    ) {
//        val isFocused by interactionSource.collectIsFocusedAsState()
//        val borderModifier = Modifier.border(
//            width = 1.dp,
//            shape = LockBoxTheme.shape.roundedCornerShape8,
//            color = if (errorText != null) LockBoxTheme.colors.red600
//            else if (isFocused) LockBoxTheme.colors.grey500 else Color.Transparent
//        )
//
//        TextField(
//            interactionSource = interactionSource,
//            value = value,
//            onValueChange = onValueChange,
//            singleLine = true,
//            visualTransformation = visualTransformation,
//            keyboardOptions = keyboardOptions,
//            keyboardActions = keyboardActions,
//            trailingIcon = {
//                AnimatedVisibility(isFocused) {
//                    Row(
//                        verticalAlignment = Alignment.CenterVertically,
//                        horizontalArrangement = Arrangement.End
//                    ) {
//                        trailingContent()
//                    }
//                }
//            },
//            placeholder = {
//                Text(
//                    text = placeholderText,
//                    style = LockBoxTheme.typography.bodyLarge,
//                    color = LockBoxTheme.colors.grey500
//                )
//            },
//            textStyle = LockBoxTheme.typography.bodyLarge,
//            shape = LockBoxTheme.shape.roundedCornerShape8,
//            colors = TextFieldDefaults.colors(
//                focusedTextColor = LockBoxTheme.colors.grey100,
//                unfocusedTextColor = LockBoxTheme.colors.grey100,
//                disabledTextColor = LockBoxTheme.colors.grey100,
//                cursorColor = LockBoxTheme.colors.constantWhite,
//                focusedContainerColor = LockBoxTheme.colors.grey800,
//                unfocusedContainerColor = LockBoxTheme.colors.grey800,
//                disabledContainerColor = LockBoxTheme.colors.grey800,
//                focusedIndicatorColor = Color.Transparent,
//                unfocusedIndicatorColor = Color.Transparent,
//            ),
//            modifier = borderModifier.fillMaxWidth()
//        )
//        AnimatedVisibility(errorText != null) {
//            errorText?.let {
//                Text(
//                    text = errorText,
//                    style = LockBoxTheme.typography.footnote,
//                    color = LockBoxTheme.colors.red600,
//                )
//            }
//        }
//    }
//}
