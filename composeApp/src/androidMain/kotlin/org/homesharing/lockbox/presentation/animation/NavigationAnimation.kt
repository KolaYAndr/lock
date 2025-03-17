package org.homesharing.lockbox.presentation.animation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.navigation.NavBackStackEntry

private const val EXIT_ANIMATION_DURATION = 300
private const val ENTER_ANIMATION_DURATION = 200

val exitAnimation: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition?) =
    {
        fadeOut(
            animationSpec = tween(
                EXIT_ANIMATION_DURATION, easing = LinearEasing
            )
        ) + slideOutOfContainer(
            animationSpec = tween(EXIT_ANIMATION_DURATION, easing = LinearEasing),
            towards = AnimatedContentTransitionScope.SlideDirection.Start,
            targetOffset = { -it / 4 }
        )
    }
val enterAnimation: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition?) =
    {
        fadeIn(
            animationSpec = tween(
                ENTER_ANIMATION_DURATION, easing = LinearEasing
            )
        ) + slideIntoContainer(
            animationSpec = tween(ENTER_ANIMATION_DURATION, easing = LinearEasing),
            towards = AnimatedContentTransitionScope.SlideDirection.Start,
            initialOffset = { it }
        )
    }
