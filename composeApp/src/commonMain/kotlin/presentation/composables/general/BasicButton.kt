package presentation.composables.general

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import presentation.theme.LockBoxTheme

@Composable
fun BasicButton(
    onClick: () -> Unit,
    text: String,
    paddingValues: PaddingValues = PaddingValues(8.dp),
    colors: ButtonColors = ButtonDefaults.buttonColors(
        containerColor = LockBoxTheme.colors.green500,
        contentColor = LockBoxTheme.colors.grey900
    ),
    modifier: Modifier = Modifier,
) {
    Button(
        onClick = onClick::invoke,
        shape = LockBoxTheme.shape.roundedCornerShape8,
        colors = colors,
        contentPadding = paddingValues,
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
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
        )
    }
}
