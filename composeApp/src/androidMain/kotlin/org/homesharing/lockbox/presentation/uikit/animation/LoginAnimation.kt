package org.homesharing.lockbox.presentation.uikit.animation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith

private const val ANIMATION_DURATION = 400

internal val loginAnimation : AnimatedContentTransitionScope<Boolean>.() -> ContentTransform = {
    fadeIn(animationSpec = tween(ANIMATION_DURATION)) togetherWith
            fadeOut(animationSpec = tween(ANIMATION_DURATION))
}