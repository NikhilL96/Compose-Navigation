package com.example.composecustomnavigation.components

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.composecustomnavigation.HomeGraph
import com.example.navigationcore.AnimationType
import com.example.navigationcore.ComposeNavHost
import com.example.navigationcore.ComposeNavHostController
import com.example.navigationcore.destinationComposable

@OptIn(ExperimentalAnimationApi::class)
@Composable
internal fun HomeBottomNavHost(navExtrasHostController: ComposeNavHostController<HomeGraph>, modifier: Modifier, startDestination: HomeGraph = HomeGraph.DashboardScreen) {

    ComposeNavHost(
        modifier = modifier,
        navController = navExtrasHostController
    ) {

        destinationComposable<HomeGraph.DashboardScreen>(animationType = AnimationType.FADE) { navBackStackEntry, data ->
            DashboardScreen()
        }

        destinationComposable<HomeGraph.NotificationScreen>(animationType = AnimationType.FADE) { navBackStackEntry, data ->
            NotificationScreen()
        }

        destinationComposable<HomeGraph.ProfileScreen>(animationType = AnimationType.FADE) { navBackStackEntry, data ->
            ProfileScreen(data.value)
        }
    }
}