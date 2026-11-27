package org.homesharing.lockbox.presentation.uikit.composable.input

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import lockbox.sharedui.generated.resources.Res
import lockbox.sharedui.generated.resources.ic_close
import org.homesharing.lockbox.presentation.uikit.theme.HomesharingTheme
import org.jetbrains.compose.resources.painterResource

@Composable
internal fun HomesharingTimeInput(
    value: Long?,
    onValueChange: (Long?) -> Unit,
    placeholderText: String,
    modifier: Modifier = Modifier,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
) {
    var inputValue by remember(value) {
        mutableStateOf(value.toTimeInputValue())
    }

    HomesharingInput(
        value = inputValue,
        onValueChange = { input ->
            val timeInputValue = input.toTimeInputValue()

            inputValue = timeInputValue
            onValueChange(timeInputValue.toMinutesOfDayOrNull())
        },
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        visualTransformation = TimeVisualTransformation(),
        placeholderText = placeholderText,
        trailingIcon = painterResource(Res.drawable.ic_close),
        trailingIconAction = {
            inputValue = ""
            onValueChange(null)
        },
        modifier = modifier,
    )
}

private class TimeVisualTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val raw = text.text.toTimeInputValue()

        val formatted = buildString {
            raw.forEachIndexed { index, char ->
                if (index == TIME_HOURS_LENGTH) append(TIME_SEPARATOR)
                append(char)
            }
        }

        return TransformedText(
            text = AnnotatedString(formatted),
            offsetMapping = TimeOffsetMapping,
        )
    }
}

private object TimeOffsetMapping : OffsetMapping {
    override fun originalToTransformed(offset: Int): Int {
        return when {
            offset <= TIME_HOURS_LENGTH -> offset
            offset <= TIME_INPUT_MAX_LENGTH -> offset + 1
            else -> TIME_FORMATTED_MAX_LENGTH
        }
    }

    override fun transformedToOriginal(offset: Int): Int {
        return when {
            offset <= TIME_HOURS_LENGTH -> offset
            offset <= TIME_FORMATTED_MAX_LENGTH -> offset - 1
            else -> TIME_INPUT_MAX_LENGTH
        }
    }
}

private fun String.toTimeInputValue(): String {
    return filter { it.isDigit() }
        .take(TIME_INPUT_MAX_LENGTH)
}

private fun String.toMinutesOfDayOrNull(): Long? {
    val digits = toTimeInputValue()

    if (digits.length != TIME_INPUT_MAX_LENGTH) return null

    val hours = digits
        .take(TIME_HOURS_LENGTH)
        .toIntOrNull()
        ?: return null

    val minutes = digits
        .drop(TIME_HOURS_LENGTH)
        .toIntOrNull()
        ?: return null

    if (hours !in HOURS_RANGE) return null
    if (minutes !in MINUTES_RANGE) return null

    return hours * MINUTES_IN_HOUR + minutes
}

private fun Long?.toTimeInputValue(): String {
    if (this == null) return ""

    val hours = this / MINUTES_IN_HOUR
    val minutes = this % MINUTES_IN_HOUR

    return hours.toString().padStart(TIME_HOURS_LENGTH, '0') +
            minutes.toString().padStart(TIME_MINUTES_LENGTH, '0')
}

private const val TIME_SEPARATOR = ':'
private const val TIME_HOURS_LENGTH = 2
private const val TIME_MINUTES_LENGTH = 2
private const val TIME_INPUT_MAX_LENGTH = TIME_HOURS_LENGTH + TIME_MINUTES_LENGTH
private const val TIME_FORMATTED_MAX_LENGTH = TIME_INPUT_MAX_LENGTH + 1
private const val MINUTES_IN_HOUR = 60L

private val HOURS_RANGE = 0..23
private val MINUTES_RANGE = 0..59

@Preview
@Composable
private fun PreviewHomesharingTimeInput() {
    HomesharingTheme {
        HomesharingTimeInput(
            value = null,
            onValueChange = {},
            placeholderText = "Дата",
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
    }
}