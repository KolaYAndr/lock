package presentation.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.homesharing.lockbox.R
import presentation.theme.LockBoxTheme

@Composable
fun Pin(
    value: String,
    onPinChange: (String) -> Unit,
    onConfirm: () -> Unit,
    pinLength: Int,
    keyboardOptions: KeyboardOptions = KeyboardOptions(
        imeAction = ImeAction.Done,
        keyboardType = KeyboardType.NumberPassword,
        showKeyboardOnFocus = true
    ),
    keyboardActions: KeyboardActions = KeyboardActions(
        onDone = { onConfirm() }
    ),
    modifier: Modifier = Modifier
) {
    BasicTextField(
        value = TextFieldValue(text = value, selection = TextRange(pinLength)),
        onValueChange = { input ->
            if (input.text.length <= pinLength) {
                onPinChange(input.text)
            }
        },
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        modifier = modifier,
        decorationBox = {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                repeat(pinLength) { index ->
                    val char = if (index < value.length) value[index].toString() else ""

                    OtpChar(
                        text = char,
                        isFocused = index == value.length
                    )
                }
            }
        }
    )
}

@Composable
fun OtpChar(text: String, isFocused: Boolean, modifier: Modifier = Modifier) {
    val focusedModifier = if (isFocused) {
        Modifier.border(
            width = 0.5.dp,
            shape = LockBoxTheme.shape.roundedCornerShape8,
            color = LockBoxTheme.colors.grey100
        )
    } else Modifier

    val char = text.ifEmpty { stringResource(R.string.pin_hint) }

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .size(48.dp)
            .clip(LockBoxTheme.shape.roundedCornerShape8)
            .background(LockBoxTheme.colors.grey800)
            .then(focusedModifier)
    ) {
        Text(
            text = char,
            style = LockBoxTheme.typography.titleMedium,
            color = LockBoxTheme.colors.grey500,
            textAlign = TextAlign.Center
        )
    }
}

@Preview
@Composable
private fun PreviewPin() {
    LockBoxTheme {
        val text by remember { mutableStateOf("") }
        Pin(
            text,
            { },
            { },
            6,
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        )
    }
}
