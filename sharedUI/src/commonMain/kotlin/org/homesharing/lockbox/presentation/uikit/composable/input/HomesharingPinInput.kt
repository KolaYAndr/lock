package org.homesharing.lockbox.presentation.uikit.composable.input

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.homesharing.lockbox.presentation.uikit.modifier.paddedBorder
import org.homesharing.lockbox.presentation.uikit.theme.HomesharingTheme

@Composable
internal fun HomesharingPinInput(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    isError: Boolean = false,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    BasicTextField(
        value = TextFieldValue(
            text = value,
            selection = TextRange(value.length),
        ),
        onValueChange = { input ->
            onValueChange(input.text)
        },
        singleLine = true,
        interactionSource = interactionSource,
        keyboardActions = keyboardActions,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.NumberPassword,
            imeAction = ImeAction.Done,
        ),
        decorationBox = {
            Row(
                modifier = Modifier.fillMaxWidth(),
            ) {
                repeat(PIN_LENGTH) { index ->
                    PinCell(
                        symbol = value.getOrNull(index),
                        isActive = isFocused && index == value.length.coerceAtMost(PIN_LENGTH - 1),
                        isError = isError,
                        modifier = Modifier.weight(1f),
                    )
                }
            }
        },
        modifier = modifier.focusRequester(focusRequester),
    )
}

@Composable
private fun PinCell(
    symbol: Char?,
    isActive: Boolean,
    isError: Boolean,
    modifier: Modifier = Modifier,
) {
    val activeBorderColor by animateColorAsState(
        targetValue = if (isActive) {
            HomesharingTheme.colors.black.copy(alpha = .5f)
        } else {
            Color.Transparent
        }
    )

    val cellBorderColor by animateColorAsState(
        targetValue = when {
            isError -> HomesharingTheme.colors.red
            isActive || symbol != null -> HomesharingTheme.colors.black.copy(alpha = .2f)
            else -> HomesharingTheme.colors.black.copy(alpha = .1f)
        }
    )

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .paddedBorder(activeBorderColor)
            .height(PIN_CELL_HEIGHT)
            .clip(HomesharingTheme.shape.roundedCornerShape16)
            .background(
                HomesharingTheme.colors.white
            )
            .border(
                width = 1.dp,
                color = cellBorderColor,
                shape = HomesharingTheme.shape.roundedCornerShape16,
            )
    ) {
        Text(
            text = symbol?.toString().orEmpty(),
            style = HomesharingTheme.typography.titleMedium,
            color = HomesharingTheme.colors.black,
            textAlign = TextAlign.Center,
        )
    }
}

private const val PIN_LENGTH = 6
private val PIN_CELL_HEIGHT = 56.dp

@Preview
@Composable
private fun PreviewHomesharingPinInput() {
    HomesharingTheme {
        HomesharingPinInput(
            value = "123456",
            onValueChange = {},
            isError = true,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}
