package org.homesharing.lockbox.presentation.uikit.modifier

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawOutline
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.homesharing.lockbox.presentation.uikit.theme.roundedCornerShape18

internal fun Modifier.paddedBorder(
    borderColor: Color,
    gap: Dp = 2.dp,
    strokeWidth: Dp = 3.dp,
): Modifier = this.drawBehind {
    if (borderColor.alpha <= 0f) return@drawBehind

    val strokePx = strokeWidth.toPx()
    val outsetPx = gap.toPx() + strokePx / 2f
    val expandedSize = Size(
        width = size.width + outsetPx * 2,
        height = size.height + outsetPx * 2,
    )
    val outline = roundedCornerShape18.createOutline(
        size = expandedSize,
        layoutDirection = layoutDirection,
        density = this,
    )

    translate(left = -outsetPx, top = -outsetPx) {
        drawOutline(
            outline = outline,
            color = borderColor,
            style = Stroke(width = strokePx),
        )
    }
}
