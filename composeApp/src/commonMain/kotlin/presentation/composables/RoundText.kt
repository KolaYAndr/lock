package presentation.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import presentation.theme.LockBoxTheme

@Composable
fun RoundText(text: String, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .border(
                width = 0.5.dp,
                color = LockBoxTheme.colors.grey700,
                shape = LockBoxTheme.shape.circleShape
            )
            .background(
                color = LockBoxTheme.colors.grey700,
                shape = LockBoxTheme.shape.circleShape
            )
            .size(64.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = LockBoxTheme.colors.grey500,
            textAlign = TextAlign.Center
        )
    }
}

@Preview
@Composable
fun PreviewRoundText() {
    LockBoxTheme {
        RoundText(text = "YA3", modifier = Modifier.padding(16.dp))
    }
}
