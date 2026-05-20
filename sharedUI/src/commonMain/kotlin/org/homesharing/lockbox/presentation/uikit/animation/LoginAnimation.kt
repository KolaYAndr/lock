package org.homesharing.lockbox.presentation.uikit.animation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith

private const val ANIMATION_DURATION = 300

internal val loginAnimation: AnimatedContentTransitionScope<Boolean>.() -> ContentTransform = {
    if (targetState) {
        slideInVertically(
                animationSpec = tween(ANIMATION_DURATION, easing = LinearEasing),
                initialOffsetY = { it }
            ) togetherWith slideOutVertically(
                animationSpec = tween(ANIMATION_DURATION, easing = LinearEasing),
                targetOffsetY = { -it }
            )
    } else {
        slideInVertically(
                animationSpec = tween(ANIMATION_DURATION, easing = LinearEasing),
                initialOffsetY = { -it }
            ) togetherWith slideOutVertically(
                animationSpec = tween(ANIMATION_DURATION, easing = LinearEasing),
                targetOffsetY = { it }
            )
    }
}
