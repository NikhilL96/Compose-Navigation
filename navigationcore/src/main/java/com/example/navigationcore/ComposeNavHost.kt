package com.example.navigationcore

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import com.google.accompanist.navigation.animation.AnimatedNavHost


@Composable
@ExperimentalAnimationApi
inline fun <reified D : Destination<D>> ComposeNavHost(
    navController: ComposeNavHostController<D>,
    modifier: Modifier = Modifier,
    contentAlignment: Alignment = Alignment.Center,
    route: String? = null,
    animationType: AnimationType = AnimationType.FADE,
    noinline builder: NavGraphBuilder.() -> Unit
) {


    CompositionLocalProvider(
        LocalNavController provides LocalNavController.current.appendNavController(
            D::class.getRoute(),
            navController
        )
    ) {

        AnimatedNavHost(
            navController,
            navController.startDestination.route,
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

internal typealias NavControllerMap = LinkedHashMap<String, ComposeNavHostController<out Destination<*>>>

fun <D : Destination<D>> NavControllerMap.appendNavController(
    key: String,
    navController: ComposeNavHostController<D>
): NavControllerMap {
    val original = this
    return NavControllerMap().apply {
        original.forEach { (t, u) -> this[t] = u }
        this[key] = navController
    }
}

val LocalNavController = compositionLocalOf<NavControllerMap> { linkedMapOf() }

