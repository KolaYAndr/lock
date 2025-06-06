package org.homesharing.lockbox.presentation.general.animation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.navigation.NavBackStackEntry

private const val EXIT_ANIMATION_DURATION = 250
private const val ENTER_ANIMATION_DURATION = 200
private const val ANIMATION_OFFSET = 0.92f

internal val exitAnimation: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition?) =
    {
        slideOutOfContainer(
            animationSpec = tween(EXIT_ANIMATION_DURATION, easing = LinearEasing),
            towards = AnimatedContentTransitionScope.SlideDirection.Start,
            targetOffset = { -it }
        )
    }
internal val enterAnimation: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition?) =
    {
        slideIntoContainer(
            animationSpec = tween(ENTER_ANIMATION_DURATION, easing = LinearEasing),
            towards = AnimatedContentTransitionScope.SlideDirection.Start,
            initialOffset = { it }
        )
    }

internal val globalEnterAnimation: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition?) =
    {
        scaleIn(animationSpec = tween(ENTER_ANIMATION_DURATION), initialScale = ANIMATION_OFFSET)
    }

internal val globalExitAnimation: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition?) =
    {
        scaleOut(animationSpec = tween(ENTER_ANIMATION_DURATION), targetScale = ANIMATION_OFFSET)
    }
