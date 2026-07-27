package org.homesharing.lockbox.presentation.uikit.composable.button

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.homesharing.lockbox.presentation.uikit.modifier.paddedBorder
import org.homesharing.lockbox.presentation.uikit.theme.HomesharingTheme
import org.homesharing.lockbox.presentation.uikit.theme.WHITE

@Composable
internal fun HomesharingButton(
    title: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    subtitle: String? = null,
    loading: Boolean = false,
    enabled: Boolean = true,
    style: HomesharingButtonStyle = HomesharingButtonDefaults.primary()
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isHovered by interactionSource.collectIsHoveredAsState()
    val isPressed by interactionSource.collectIsPressedAsState()
    val isFocused by interactionSource.collectIsFocusedAsState()

    val borderColor by animateColorAsState(
        if (isPressed) {
            HomesharingTheme.colors.black.copy(alpha = .5f)
        } else {
            Color.Transparent
        }
    )

    val backgroundColor by animateColorAsState(
        if (enabled) {
            when {
                isHovered -> style.hoveredBackgroundColor
                isPressed -> style.pressedBackgroundColor
                isFocused -> style.focusedBackgroundColor
                else -> style.backgroundColor
            }
        } else style.disabledBackgroundColor
    )

    Button(
        onClick = onClick,
        enabled = enabled && !loading,
        interactionSource = interactionSource,
        shape = HomesharingTheme.shape.roundedCornerShape16,
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor,
            contentColor = style.titleColor,
            disabledContainerColor = backgroundColor,
            disabledContentColor = style.disabledTitleColor
        ),
        elevation = null,
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        modifier = modifier
            .paddedBorder(borderColor)
            .height(56.dp)
    ) {
        AnimatedContent(loading) { isLoading ->
            if (isLoading) {
                CircularProgressIndicator(
                    color = style.titleColor,
                    modifier = Modifier.size(24.dp)
                )
            } else {
                Column(
                    verticalArrangement = Arrangement.spacedBy(0.dp, Alignment.CenterVertically),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = title,
                        color = if (enabled) style.titleColor else style.disabledTitleColor,
                        style = HomesharingTheme.typography.bodyMedium
                    )

                    subtitle?.let {
                        Text(
                            text = it,
                            color = if (enabled) style.subtitleColor else style.disabledSubtitleColor,
                            style = HomesharingTheme.typography.bodyXSmall,
                        )
                    }
                }
            }
        }
    }
}

@Immutable
internal data class HomesharingButtonStyle(
    val backgroundColor: Color,
    val titleColor: Color,
    val subtitleColor: Color,
    val disabledBackgroundColor: Color,
    val disabledTitleColor: Color,
    val disabledSubtitleColor: Color,
    val hoveredBackgroundColor: Color,
    val focusedBackgroundColor: Color,
    val pressedBackgroundColor: Color,
)

@Immutable
internal object HomesharingButtonDefaults {
    @Composable
    fun primary() = HomesharingButtonStyle(
        backgroundColor = HomesharingTheme.colors.black,
        titleColor = HomesharingTheme.colors.white,
        subtitleColor = HomesharingTheme.colors.white.copy(alpha = .5f),
        disabledBackgroundColor = HomesharingTheme.colors.black.copy(alpha = .1f),
        disabledTitleColor = HomesharingTheme.colors.black.copy(alpha = .3f),
        disabledSubtitleColor = HomesharingTheme.colors.black.copy(alpha = .3f),
        hoveredBackgroundColor = HomesharingTheme.colors.black.copy(alpha = .9f),
        focusedBackgroundColor = HomesharingTheme.colors.black.copy(alpha = .95f),
        pressedBackgroundColor = HomesharingTheme.colors.black.copy(alpha = .7f)
    )

    @Composable
    fun secondary() = HomesharingButtonStyle(
        backgroundColor = HomesharingTheme.colors.black.copy(alpha = .05f),
        titleColor = HomesharingTheme.colors.black,
        subtitleColor = HomesharingTheme.colors.black.copy(alpha = .5f),
        disabledBackgroundColor = HomesharingTheme.colors.black.copy(alpha = .05f),
        disabledTitleColor = HomesharingTheme.colors.black.copy(alpha = .3f),
        disabledSubtitleColor = HomesharingTheme.colors.black.copy(alpha = .3f),
        hoveredBackgroundColor = HomesharingTheme.colors.black.copy(alpha = .1f),
        focusedBackgroundColor = HomesharingTheme.colors.black.copy(alpha = .08f),
        pressedBackgroundColor = HomesharingTheme.colors.black.copy(alpha = .15f)
    )

    @Composable
    fun danger() = HomesharingButtonStyle(
        backgroundColor = HomesharingTheme.colors.red.copy(alpha = .1f),
        titleColor = HomesharingTheme.colors.red,
        subtitleColor = HomesharingTheme.colors.black.copy(alpha = .5f),
        disabledBackgroundColor = HomesharingTheme.colors.red.copy(alpha = .05f),
        disabledTitleColor = HomesharingTheme.colors.black.copy(alpha = .3f),
        disabledSubtitleColor = HomesharingTheme.colors.black.copy(alpha = .3f),
        hoveredBackgroundColor = HomesharingTheme.colors.red.copy(alpha = .15f),
        focusedBackgroundColor = HomesharingTheme.colors.red.copy(alpha = .12f),
        pressedBackgroundColor = HomesharingTheme.colors.red.copy(alpha = .2f)
    )

    @Composable
    fun ghost() = HomesharingButtonStyle(
        backgroundColor = Color.Transparent,
        titleColor = HomesharingTheme.colors.black,
        subtitleColor = HomesharingTheme.colors.black.copy(alpha = .5f),
        disabledBackgroundColor = Color.Transparent,
        disabledTitleColor = HomesharingTheme.colors.black.copy(alpha = .3f),
        disabledSubtitleColor = HomesharingTheme.colors.black.copy(alpha = .3f),
        hoveredBackgroundColor = HomesharingTheme.colors.black.copy(alpha = .05f),
        focusedBackgroundColor = HomesharingTheme.colors.black.copy(alpha = .03f),
        pressedBackgroundColor = HomesharingTheme.colors.black.copy(alpha = .1f)
    )

    @Composable
    fun dangerGhost() = HomesharingButtonStyle(
        backgroundColor = Color.Transparent,
        titleColor = HomesharingTheme.colors.red,
        subtitleColor = HomesharingTheme.colors.black.copy(alpha = .5f),
        disabledBackgroundColor = Color.Transparent,
        disabledTitleColor = HomesharingTheme.colors.black.copy(alpha = .3f),
        disabledSubtitleColor = HomesharingTheme.colors.black.copy(alpha = .3f),
        hoveredBackgroundColor = HomesharingTheme.colors.red.copy(alpha = .05f),
        focusedBackgroundColor = HomesharingTheme.colors.red.copy(alpha = .03f),
        pressedBackgroundColor = HomesharingTheme.colors.red.copy(alpha = .1f)
    )
}

@Preview
@Composable
private fun PreviewHomesharingButton() {
    HomesharingTheme {
        val styles = listOf(
            "primary" to HomesharingButtonDefaults.primary(),
            "secondary" to HomesharingButtonDefaults.secondary(),
            "danger" to HomesharingButtonDefaults.danger(),
            "ghost" to HomesharingButtonDefaults.ghost(),
            "dangerGhost" to HomesharingButtonDefaults.dangerGhost(),
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.background(WHITE).padding(8.dp)
        ) {
            styles.forEach { (name, style) ->
                HomesharingButton(
                    title = name,
                    subtitle = "Subtitle",
                    onClick = {},
                    style = style,
                    modifier = Modifier.fillMaxWidth()
                )

                HomesharingButton(
                    title = "$name · disabled",
                    subtitle = "Subtitle",
                    enabled = false,
                    onClick = {},
                    style = style,
                    modifier = Modifier.fillMaxWidth()
                )

                HomesharingButton(
                    title = "$name · loading",
                    subtitle = "Subtitle",
                    loading = true,
                    onClick = {},
                    style = style,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}