package org.homesharing.lockbox.presentation.uikit.animation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.togetherWith
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.scene.Scene

private const val EXIT_ANIMATION_DURATION = 250
private const val ENTER_ANIMATION_DURATION = 200

internal val enterTransition:
        AnimatedContentTransitionScope<Scene<NavKey>>.() -> ContentTransform = {
    val enter = slideIntoContainer(
        towards = AnimatedContentTransitionScope.SlideDirection.Start,
        animationSpec = tween(ENTER_ANIMATION_DURATION, easing = LinearEasing),
        initialOffset = { it }
    )
    val exit = slideOutOfContainer(
        towards = AnimatedContentTransitionScope.SlideDirection.End,
        animationSpec = tween(ENTER_ANIMATION_DURATION, easing = LinearEasing),
        targetOffset = { -it }
    )
    enter togetherWith exit
}

internal val exitTransition:
        AnimatedContentTransitionScope<Scene<NavKey>>.() -> ContentTransform = {
    val enter = slideIntoContainer(
        towards = AnimatedContentTransitionScope.SlideDirection.Start,
        animationSpec = tween(EXIT_ANIMATION_DURATION, easing = LinearEasing),
        initialOffset = { -it }
    )
    val exit = slideOutOfContainer(
        towards = AnimatedContentTransitionScope.SlideDirection.End,
        animationSpec = tween(EXIT_ANIMATION_DURATION, easing = LinearEasing),
        targetOffset = { it }
    )
    enter togetherWith exit
}

internal val popExitTransition:
        AnimatedContentTransitionScope<Scene<NavKey>>.(Int) -> ContentTransform = { swipeEdge ->
    val enter = slideIntoContainer(
        towards = AnimatedContentTransitionScope.SlideDirection.Start,
        animationSpec = tween(EXIT_ANIMATION_DURATION, easing = LinearEasing),
        initialOffset = { -it + swipeEdge }
    )
    val exit = slideOutOfContainer(
        towards = AnimatedContentTransitionScope.SlideDirection.End,
        animationSpec = tween(EXIT_ANIMATION_DURATION, easing = LinearEasing),
        targetOffset = { it + swipeEdge }
    )
    enter togetherWith exit
}
