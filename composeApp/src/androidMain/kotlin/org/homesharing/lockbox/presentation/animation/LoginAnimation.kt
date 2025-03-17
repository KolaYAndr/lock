package org.homesharing.lockbox.presentation.animation

import androidx.compose.animation.ContentTransform
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith

private const val BACKWARD_ANIMATION_DURATION = 300
private const val FORWARD_ANIMATION_DURATION = 200

val forwardAnimation: ContentTransform =
    (slideInHorizontally(
        initialOffsetX = { fullWidth -> fullWidth },
        animationSpec = tween(durationMillis = FORWARD_ANIMATION_DURATION)
    ) + fadeIn(animationSpec = tween(FORWARD_ANIMATION_DURATION))).togetherWith(
        slideOutHorizontally(
            targetOffsetX = { fullWidth -> -fullWidth },
            animationSpec = tween(FORWARD_ANIMATION_DURATION)
        ) + fadeOut(animationSpec = tween(FORWARD_ANIMATION_DURATION))
    )

val backwardAnimation: ContentTransform = (slideInHorizontally(
    initialOffsetX = { fullWidth -> -fullWidth },
    animationSpec = tween(durationMillis = BACKWARD_ANIMATION_DURATION)
) + fadeIn(animationSpec = tween(BACKWARD_ANIMATION_DURATION))).togetherWith(
    slideOutHorizontally(
        targetOffsetX = { fullWidth -> fullWidth },
        animationSpec = tween(BACKWARD_ANIMATION_DURATION)
    ) + fadeOut(animationSpec = tween(BACKWARD_ANIMATION_DURATION))
)
