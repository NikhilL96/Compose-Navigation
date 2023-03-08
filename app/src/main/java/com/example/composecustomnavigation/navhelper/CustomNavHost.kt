package com.example.composecustomnavigation.navhelper

import androidx.activity.compose.BackHandler
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import com.google.accompanist.navigation.animation.AnimatedNavHost


@Composable
@ExperimentalAnimationApi
inline fun <reified D : NavigationGraph> CustomNavHost(
    navigator: CustomNavigator<D>,
    startDestination: D,
    modifier: Modifier = Modifier,
    contentAlignment: Alignment = Alignment.Center,
    route: String? = null,
    animationType: AnimationType = AnimationType.FADE,
    noinline builder: NavGraphBuilder.() -> Unit
) {
    LaunchedEffect(key1 = Unit, block = {
        navigator.setStartDestination(startDestination)
    })
    
    CompositionLocalProvider(
        LocalNavigator provides LocalNavigator.current.toMutableMap().apply {
            if(!this.containsKey(D::class))
                this[D::class] = navigator
        }
    ) {

        BackHandler {
            navigator.popBackStack()
        }

        AnimatedNavHost(
            navigator.navController,
            startDestination.route,
            modifier,
            contentAlignment,
            route,
            { animationType.inEnterAnimation() },
            { animationType.inExitAnimation() },
            { animationType.inPopEnterAnimation() },
            { animationType.inPopExitAnimation() },
            builder
        )
    }
}