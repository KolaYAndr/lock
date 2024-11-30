package presentation.composables.general

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import presentation.composables.EmailInput
import presentation.composables.PasswordInput
import presentation.theme.LockBoxTheme

@Composable
fun BasicCard(
    elevation: Dp = 1.dp,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit,
) {
    Card(
        shape = LockBoxTheme.shape.roundedCornerShape24,
        elevation = elevation,
        backgroundColor = LockBoxTheme.colors.grey900,
        modifier = modifier
    ) {
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            content()
        }
    }
}

@Preview
@Composable
fun PreviewCard() {
    LockBoxTheme {
        BasicCard {
            var email by remember { mutableStateOf("") }
            val emailInteractionSource = remember { MutableInteractionSource() }
            EmailInput(
                email = email,
                onValueChange = { email = it },
                onClearText = { email = "" },
                interactionSource = emailInteractionSource,
                keyboardActions = KeyboardActions(),
                modifier = Modifier.fillMaxWidth()
            )

            var value by remember { mutableStateOf("") }
            var isSeen by remember { mutableStateOf(false) }
            val interactionSource = remember { MutableInteractionSource() }

            PasswordInput(
                password = value,
                onValueChange = { value = it },
                isSeen = isSeen,
                onSeenChange = { isSeen = !isSeen },
                onClearText = { value = "" },
                modifier = Modifier.fillMaxWidth(),
                keyboardActions = KeyboardActions(),
                interactionSource = interactionSource
            )
        }
    }
}
