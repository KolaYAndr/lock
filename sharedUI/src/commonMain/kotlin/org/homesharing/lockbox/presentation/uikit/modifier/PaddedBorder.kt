package org.homesharing.lockbox.presentation.uikit.modifier

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.homesharing.lockbox.presentation.uikit.theme.roundedCornerShape18

internal fun Modifier.paddedBorder(
    borderColor: Color,
    padding: PaddingValues = PaddingValues(5.dp)
): Modifier = this
    .border(
        width = 3.dp,
        color = borderColor,
        shape = roundedCornerShape18
    )
    .padding(padding)
