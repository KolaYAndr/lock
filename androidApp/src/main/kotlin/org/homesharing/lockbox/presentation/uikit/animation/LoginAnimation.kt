//package org.homesharing.lockbox.presentation.uikit.animation
//
//import androidx.compose.animation.AnimatedContentTransitionScope
//import androidx.compose.animation.ContentTransform
//import androidx.compose.animation.core.LinearEasing
//import androidx.compose.animation.core.tween
//import androidx.compose.animation.fadeIn
//import androidx.compose.animation.fadeOut
//import androidx.compose.animation.slideInVertically
//import androidx.compose.animation.slideOutVertically
//import androidx.compose.animation.togetherWith
//import org.homesharing.lockbox.LoginState
//
//private const val ANIMATION_DURATION = 300
//private const val ENTER_ANIMATION_DURATION = 400
//private const val INITIAL_ALPHA = .95f
//private const val TARGET_ALPHA = .05f
//
//internal val loginAnimation: AnimatedContentTransitionScope<LoginState>.() -> ContentTransform = {
//    if (initialState == LoginState.Loading) enterAnimation
//    else when (targetState) {
//        LoginState.Loading -> {
//            enterAnimation
//        }
//
//        LoginState.LoggedIn -> {
//            slideInVertically(
//                animationSpec = tween(ANIMATION_DURATION, easing = LinearEasing),
//                initialOffsetY = { it }
//            ) togetherWith slideOutVertically(
//                animationSpec = tween(ANIMATION_DURATION, easing = LinearEasing),
//                targetOffsetY = { -it }
//            )
//        }
//
//        LoginState.NotLoggedIn -> {
//            slideInVertically(
//                animationSpec = tween(ANIMATION_DURATION, easing = LinearEasing),
//                initialOffsetY = { -it }
//            ) togetherWith slideOutVertically(
//                animationSpec = tween(ANIMATION_DURATION, easing = LinearEasing),
//                targetOffsetY = { it }
//            )
//        }
//    }
//}
//
//private val enterAnimation =
//    fadeIn(
//        animationSpec = tween(ENTER_ANIMATION_DURATION, easing = LinearEasing),
//        initialAlpha = INITIAL_ALPHA
//    ) togetherWith
//            fadeOut(
//                animationSpec = tween(ENTER_ANIMATION_DURATION, easing = LinearEasing),
//                targetAlpha = TARGET_ALPHA
//            )
