package org.homesharing.lockbox.presentation.uikit.composables.core.buttons

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.homesharing.lockbox.R
import org.homesharing.lockbox.presentation.uikit.theme.LockBoxTheme

@Composable
internal fun LockboxCloseButton(onClick: () -> Unit, modifier: Modifier = Modifier) {
    Button (
        modifier = modifier.size(24.dp),
        onClick = onClick,
        shape = LockBoxTheme.shape.circleShape,
        colors = ButtonDefaults.buttonColors(
            containerColor = LockBoxTheme.colors.grey800
        ),
        contentPadding = PaddingValues(0.dp)
    ) {
        Icon(
            painter = painterResource(R.drawable.close),
            contentDescription = null,
            tint = LockBoxTheme.colors.grey500,
            modifier = Modifier.size(16.dp)
        )
    }
}

@Composable
@Preview
private fun PreviewLockboxCloseButton() {
    LockBoxTheme {
        LockboxCloseButton(onClick = {})
    }
}
