package com.example.composecustomnavigation.components

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.composecustomnavigation.HomeGraph
import com.example.composecustomnavigation.navhelper.AnimationType
import com.example.composecustomnavigation.navhelper.CustomNavHost
import com.example.composecustomnavigation.navhelper.CustomNavigator
import com.example.composecustomnavigation.navhelper.navigationComposable

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun HomeBottomNavHost(navigator: CustomNavigator<HomeGraph>, modifier: Modifier, startDestination: HomeGraph = HomeGraph.DashboardScreen) {

    CustomNavHost(modifier = modifier, navigator = navigator, startDestination = startDestination) {

        navigationComposable<HomeGraph.DashboardScreen>(animationType = AnimationType.FADE) { navBackStackEntry, data ->
            DashboardScreen()
        }

        navigationComposable<HomeGraph.NotificationScreen>(animationType = AnimationType.FADE) { navBackStackEntry, data ->
            NotificationScreen()
        }

        navigationComposable<HomeGraph.ProfileScreen>(animationType = AnimationType.FADE) { navBackStackEntry, data ->
            ProfileScreen(data.value)
        }
    }
}