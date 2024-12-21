package presentation.composables.general

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import presentation.composables.EmailInput
import presentation.composables.PasswordInput
import presentation.theme.LockBoxTheme

@Composable
fun BasicCard(
    elevation: CardElevation = CardDefaults.cardElevation(1.dp),
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit,
) {
    Card(
        shape = LockBoxTheme.shape.roundedCornerShape24,
        elevation = elevation,
        colors = CardDefaults.cardColors(containerColor = LockBoxTheme.colors.grey900),
        modifier = modifier
    ) {
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start,
            modifier = Modifier.padding(vertical = 4.dp)
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
                isError = false,
                errorText = "",
                keyboardOptions = KeyboardOptions(),
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
                isError = false,
                errorText = "",
                keyboardActions = KeyboardActions(),
                keyboardOptions = KeyboardOptions(),
                interactionSource = interactionSource
            )
        }
    }
}
