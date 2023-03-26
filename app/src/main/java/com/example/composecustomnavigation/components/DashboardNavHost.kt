package com.example.composecustomnavigation.components

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.composecustomnavigation.DashboardGraph
import com.example.navigationcore.ComposeNavHost
import com.example.navigationcore.destinationComposable
import com.example.navigationcore.rememberDestinationNavController

@OptIn(ExperimentalAnimationApi::class)
@Composable
internal fun DashboardNavHost(
    modifier: Modifier,
    startDestination: DashboardGraph = DashboardGraph.DashboardScreen1
) {
    val navController =
        rememberDestinationNavController(startDestination = startDestination)

    ComposeNavHost(modifier = modifier, navController = navController) {

        destinationComposable<DashboardGraph.DashboardScreen1> { navBackStackEntry, data ->
            DashboardScreen1()
        }

        destinationComposable<DashboardGraph.DashboardScreen2> { navBackStackEntry, data ->
            DashboardScreen2()
        }

        destinationComposable<DashboardGraph.DashboardScreen3> { navBackStackEntry, data ->
            DashboardScreen3()
        }
    }
}