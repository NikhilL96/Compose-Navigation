package com.example.composecustomnavigation.navhelper

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDeepLink
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import java.util.*


@OptIn(ExperimentalAnimationApi::class)
inline fun <reified DESTINATION : NavigationGraph> NavGraphBuilder.navigationComposable(
    arguments: List<NamedNavArgument> = emptyList(),
    deepLinks: List<NavDeepLink> = emptyList(),
    animationType: AnimationType = AnimationType.SLIDE_HORIZONTAL,
    crossinline content: @Composable AnimatedVisibilityScope.(NavBackStackEntry, DESTINATION) -> Unit
) {
    val route = DESTINATION::class.getRoute()
    composable(
        route,
        arguments,
        deepLinks,
        { animationType.inEnterAnimation() },
        { animationType.inExitAnimation() },
        { animationType.inPopEnterAnimation() },
        { animationType.inPopExitAnimation() }
    ) { backStackEntry ->

        val navigator = LocalNavigator.current[DESTINATION::class.getGraph()]
        (navigator?.currentDestination as? DESTINATION)?.let { value ->
            content(backStackEntry, value)
        } ?:
        (navigator?.previousDestination as? DESTINATION)?.let { value ->
            if (this.transition.targetState.name == "PostExit") {
                content(backStackEntry, value)
            }
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun <GRAPH: NavigationGraph> rememberNavigator(
    navController: NavHostController = rememberAnimatedNavController(),
): CustomNavigator<GRAPH> {

    val destinationStack = rememberSaveable {
        Stack<GRAPH>()
    }

    return remember {
        CustomNavigator(navController, destinationStack)
    }
}