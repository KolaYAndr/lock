package presentation.composables.general

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import presentation.theme.LockBoxTheme

@Composable
fun BasicButton(onClick: () -> Unit, text: String, modifier: Modifier = Modifier) {
    Button(
        onClick = onClick::invoke,
        shape = LockBoxTheme.shape.roundedCornerShape8,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = LockBoxTheme.colors.green500,
            contentColor = LockBoxTheme.colors.grey900
        ),
        contentPadding = PaddingValues(horizontal = 8.dp),
        modifier = modifier
    ) {
        Text(
            text = text,
            style = LockBoxTheme.typography.headlineLarge,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
@Preview
fun PreviewButton() {
    LockBoxTheme {
        BasicButton(
            onClick = { },
            text = "Voyti",
            modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp)
        )
    }
}
