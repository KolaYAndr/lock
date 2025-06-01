package org.homesharing.lockbox.presentation.general.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.homesharing.lockbox.R
import org.homesharing.lockbox.presentation.general.theme.LockBoxTheme

@Composable
fun AddRow(onAdd: () -> Unit, text: String, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.clickable { onAdd() },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Icon(
            painter = painterResource(R.drawable.plus),
            contentDescription = null,
            modifier = Modifier.size(24.dp),
            tint = LockBoxTheme.colors.green500
        )

        Text(
            text = text,
            style = LockBoxTheme.typography.headlineLarge,
            color = LockBoxTheme.colors.green500,
            modifier = Modifier.padding(horizontal = 8.dp),
            maxLines = 1
        )
    }
}

@Preview
@Composable
private fun AddRowPreview() {
    LockBoxTheme {
        AddRow(
            {},
            "Добавить что-то",
            Modifier.fillMaxWidth()
        )
    }
}