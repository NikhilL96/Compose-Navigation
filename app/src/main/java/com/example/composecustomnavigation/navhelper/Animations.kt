package com.example.composecustomnavigation.navhelper

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically

enum class AnimationType {
    NONE,
    FADE,
    SLIDE_HORIZONTAL,
    SLIDE_VERTICAL
}


fun AnimationType.inEnterAnimation(): EnterTransition =
    when (this) {
        AnimationType.SLIDE_HORIZONTAL -> slideInHorizontally(
            initialOffsetX = { 800 },
            animationSpec = tween(400)
        ) + fadeIn(animationSpec = tween(400))

        AnimationType.SLIDE_VERTICAL -> slideInVertically (
            initialOffsetY = { 800 },
            animationSpec = tween(400)
        ) + fadeIn(animationSpec = tween(400))

        AnimationType.FADE -> fadeIn(animationSpec = tween(400))

        AnimationType.NONE -> EnterTransition.None
    }

fun AnimationType.inExitAnimation(): ExitTransition =
    when (this) {
        AnimationType.SLIDE_HORIZONTAL -> slideOutHorizontally(
            targetOffsetX = { -400 },
            animationSpec = tween(400)
        ) + fadeOut(animationSpec = tween(400))

        AnimationType.SLIDE_VERTICAL -> slideOutVertically (
            targetOffsetY = { -400 },
            animationSpec = tween(400)
        ) + fadeOut(animationSpec = tween(400))

        AnimationType.FADE -> fadeOut(animationSpec = tween(400))

        AnimationType.NONE -> ExitTransition.None
    }

fun AnimationType.inPopEnterAnimation(): EnterTransition =
    when (this) {
        AnimationType.SLIDE_HORIZONTAL -> slideInHorizontally(
            initialOffsetX = { -800 },
            animationSpec = tween(400)
        ) + fadeIn(animationSpec = tween(400))

        AnimationType.SLIDE_VERTICAL -> slideInVertically (
            initialOffsetY = { -800 },
            animationSpec = tween(400)
        ) + fadeIn(animationSpec = tween(400))

        AnimationType.FADE -> fadeIn(animationSpec = tween(400))

        AnimationType.NONE -> EnterTransition.None
    }

fun AnimationType.inPopExitAnimation(): ExitTransition =
    when (this) {
        AnimationType.SLIDE_HORIZONTAL -> slideOutHorizontally(
            targetOffsetX = { 400 },
            animationSpec = tween(400)
        ) + fadeOut(animationSpec = tween(400))

        AnimationType.SLIDE_VERTICAL -> slideOutVertically (
            targetOffsetY = { 400 },
            animationSpec = tween(400)
        ) + fadeOut(animationSpec = tween(400))

        AnimationType.FADE -> fadeOut(animationSpec = tween(400))

        AnimationType.NONE -> ExitTransition.None
    }