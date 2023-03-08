package com.example.composecustomnavigation.components

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.composecustomnavigation.navhelper.CustomNavHost
import com.example.composecustomnavigation.DashboardGraph
import com.example.composecustomnavigation.navhelper.navigationComposable
import com.example.composecustomnavigation.navhelper.rememberNavigator

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun DashboardNavHost(
    modifier: Modifier,
    startDestination: DashboardGraph = DashboardGraph.DashboardScreen1
) {
    val navigator = rememberNavigator<DashboardGraph>()

    CustomNavHost(modifier = modifier, navigator = navigator, startDestination = startDestination) {

        navigationComposable<DashboardGraph.DashboardScreen1> { navBackStackEntry, data ->
            DashboardScreen1()
        }

        navigationComposable<DashboardGraph.DashboardScreen2> { navBackStackEntry, data ->
            DashboardScreen2()
        }

        navigationComposable<DashboardGraph.DashboardScreen3> { navBackStackEntry, data ->
            DashboardScreen3()
        }
    }
}