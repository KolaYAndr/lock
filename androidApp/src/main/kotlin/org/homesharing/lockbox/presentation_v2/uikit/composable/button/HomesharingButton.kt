package org.homesharing.lockbox.presentation_v2.uikit.composable.button

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.homesharing.lockbox.presentation_v2.uikit.modifier.paddedBorder
import org.homesharing.lockbox.presentation_v2.uikit.theme.HomesharingTheme
import org.homesharing.lockbox.presentation_v2.uikit.theme.WHITE

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
            if (isPressed || isFocused) {
                style.interactionColor
            } else {
                style.backgroundColor
            }
        } else style.disabledBackgroundColor
    )

    Column(
        verticalArrangement = Arrangement.spacedBy(0.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .paddedBorder(borderColor)
            .clip(HomesharingTheme.shape.roundedCornerShape16)
            .height(56.dp)
            .background(backgroundColor)
            .clickable(
                enabled = enabled && !loading,
                interactionSource = interactionSource,
                onClick = onClick,
                role = Role.Button
            )
    ) {
        if (loading) {
            CircularProgressIndicator(
                color = style.titleColor,
                modifier = Modifier.size(24.dp)
            )
        } else {
            Text(
                text = title,
                color = if (enabled) style.titleColor else style.disabledTitleColor,
                style = HomesharingTheme.typography.bodyLarge,
            )

            subtitle?.let {
                Text(
                    text = it,
                    color = if (enabled) style.subtitleColor else style.disabledSubtitleColor,
                    style = HomesharingTheme.typography.captionLarge,
                )
            }
        }
    }
}

internal data class HomesharingButtonStyle(
    val backgroundColor: Color,
    val titleColor: Color,
    val subtitleColor: Color,
    val disabledBackgroundColor: Color,
    val disabledTitleColor: Color,
    val disabledSubtitleColor: Color,
    val interactionColor: Color
)

internal object HomesharingButtonDefaults {
    @Composable
    fun primary() = HomesharingButtonStyle(
        backgroundColor = HomesharingTheme.colors.black,
        titleColor = HomesharingTheme.colors.white,
        subtitleColor = HomesharingTheme.colors.white.copy(alpha = .5f),
        disabledBackgroundColor = HomesharingTheme.colors.black.copy(alpha = .1f),
        disabledTitleColor = HomesharingTheme.colors.black.copy(alpha = .3f),
        disabledSubtitleColor = HomesharingTheme.colors.black.copy(alpha = .3f),
        interactionColor = HomesharingTheme.colors.black.copy(alpha = .9f)
    )

    @Composable
    fun secondary() = HomesharingButtonStyle(
        backgroundColor = Color.Transparent,
        titleColor = HomesharingTheme.colors.black,
        subtitleColor = HomesharingTheme.colors.black.copy(alpha = .5f),
        disabledBackgroundColor = Color.Transparent,
        disabledTitleColor = HomesharingTheme.colors.black.copy(alpha = .3f),
        disabledSubtitleColor = HomesharingTheme.colors.black.copy(alpha = .3f),
        interactionColor = HomesharingTheme.colors.black.copy(alpha = .05f)
    )

    @Composable
    fun error() = HomesharingButtonStyle(
        backgroundColor = Color.Transparent,
        titleColor = HomesharingTheme.colors.red,
        subtitleColor = HomesharingTheme.colors.black.copy(alpha = .5f),
        disabledBackgroundColor = Color.Transparent,
        disabledTitleColor = HomesharingTheme.colors.black.copy(alpha = .3f),
        disabledSubtitleColor = HomesharingTheme.colors.black.copy(alpha = .3f),
        interactionColor = HomesharingTheme.colors.red.copy(alpha = .05f)
    )
}

@Preview
@Composable
private fun PreviewHomesharingButton() {
    HomesharingTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.background(WHITE).padding(8.dp)
        ) {
            HomesharingButton(
                title = "Button Text",
                subtitle = "Subtitle",
                loading = true,
                onClick = {},
                modifier = Modifier.fillMaxWidth()
            )

            HomesharingButton(
                title = "Button Text",
                subtitle = "Subtitle",
                enabled = false,
                onClick = {},
                modifier = Modifier.fillMaxWidth()
            )

            HomesharingButton(
                title = "Button Text",
                subtitle = "Subtitle",
                onClick = {},
                modifier = Modifier.fillMaxWidth()
            )

            HomesharingButton(
                title = "Button Text",
                subtitle = "Subtitle",
                loading = true,
                onClick = {},
                style = HomesharingButtonDefaults.secondary(),
                modifier = Modifier.fillMaxWidth()
            )

            HomesharingButton(
                title = "Button Text",
                subtitle = "Subtitle",
                enabled = false,
                onClick = {},
                style = HomesharingButtonDefaults.secondary(),
                modifier = Modifier.fillMaxWidth()
            )

            HomesharingButton(
                title = "Button Text",
                subtitle = "Subtitle",
                onClick = {},
                style = HomesharingButtonDefaults.secondary(),
                modifier = Modifier.fillMaxWidth()
            )

            HomesharingButton(
                title = "Button Text",
                subtitle = "Subtitle",
                loading = true,
                onClick = {},
                style = HomesharingButtonDefaults.error(),
                modifier = Modifier.fillMaxWidth()
            )

            HomesharingButton(
                title = "Button Text",
                subtitle = "Subtitle",
                enabled = false,
                onClick = {},
                style = HomesharingButtonDefaults.error(),
                modifier = Modifier.fillMaxWidth()
            )

            HomesharingButton(
                title = "Button Text",
                subtitle = "Subtitle",
                onClick = {},
                style = HomesharingButtonDefaults.error(),
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}