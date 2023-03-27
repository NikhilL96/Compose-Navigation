package com.example.navigationcore

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDeepLink
import androidx.navigation.NavGraphBuilder
import com.google.accompanist.navigation.animation.composable
import java.util.*


@OptIn(ExperimentalAnimationApi::class)
inline fun <reified DESTINATION : Destination<*>> NavGraphBuilder.destinationComposable(
    arguments: List<NamedNavArgument> = emptyList(),
    deepLinks: List<NavDeepLink> = emptyList(),
    crossinline animationType: (AnimatedContentScope<NavBackStackEntry>) -> AnimationType = { AnimationType.SLIDE_HORIZONTAL },
    crossinline content: @Composable AnimatedVisibilityScope.(NavBackStackEntry, DESTINATION) -> Unit
) {
    val route = DESTINATION::class.getRoute()

    composable(
        route,
        arguments,
        deepLinks,
        { animationType(this).inEnterAnimation() },
        { animationType(this).inExitAnimation() },
        { animationType(this).inPopEnterAnimation() },
        { animationType(this).inPopExitAnimation() }
    ) { backStackEntry ->

        val navController = LocalNavController.current.values.last()
        navController.currentDestinationMap[route]?.lastOrNull()?.let{ value->
            content(backStackEntry, value as DESTINATION)
        }
    }
}