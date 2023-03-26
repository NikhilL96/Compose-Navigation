package com.example.composecustomnavigation.components

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import com.example.composecustomnavigation.MainGraph
import com.example.navigationcore.ComposeNavHost
import com.example.navigationcore.destinationComposable
import com.example.navigationcore.rememberDestinationNavController

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MainNavHost() {

    val navController =
        rememberDestinationNavController(startDestination = MainGraph.HomeScreen())

    ComposeNavHost(navController = navController) {

        destinationComposable<MainGraph.HomeScreen> { navBackStackEntry, data ->
            HomeScreen(data.startDestination)
        }

        destinationComposable<MainGraph.OtherScreen> { navBackStackEntry, data ->
            OtherScreen(value = data.value)
        }

        destinationComposable<MainGraph.AnotherScreen> { navBackStackEntry, data ->
            AnotherScreen(value = data.value)
        }

        destinationComposable<MainGraph.SomeOtherScreen> { navBackStackEntry, data ->
            SomeotherScreen(value = data.value1, value1 = data.value2)
        }
    }
}