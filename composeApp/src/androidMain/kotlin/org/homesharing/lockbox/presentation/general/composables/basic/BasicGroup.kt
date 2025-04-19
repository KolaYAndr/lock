package org.homesharing.lockbox.presentation.general.composables.basic

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.homesharing.lockbox.presentation.general.theme.LockBoxTheme

@Composable
fun BasicGroup(
    shape: Shape = LockBoxTheme.shape.roundedCornerShape8,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = modifier
            .background(
                color = LockBoxTheme.colors.grey900,
                shape = shape
            )
            .border(
                width = 0.5.dp,
                color = LockBoxTheme.colors.grey800,
                shape = shape
            ),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ) {
        content()
    }
}

@Preview
@Composable
private fun PreviewBasicGroup() {
    LockBoxTheme{
        BasicGroup {
            BasicButton({}, "", modifier = Modifier.padding(16.dp))
        }
    }
}