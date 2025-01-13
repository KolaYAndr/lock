package presentation.composables.general

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import presentation.theme.LockBoxTheme

@Composable
fun BasicGroup(modifier: Modifier = Modifier, content: @Composable ColumnScope.() -> Unit) {
    Column (
        modifier = modifier
            .background(
                color = LockBoxTheme.colors.grey900,
                shape = LockBoxTheme.shape.roundedCornerShape8
            )
            .border(
                width = 0.5.dp,
                color = LockBoxTheme.colors.grey800,
                shape = LockBoxTheme.shape.roundedCornerShape8
            ),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ) {
        content()
    }
}