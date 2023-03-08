package com.example.composecustomnavigation.components

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import com.example.composecustomnavigation.navhelper.CustomNavHost
import com.example.composecustomnavigation.MainGraph
import com.example.composecustomnavigation.navhelper.navigationComposable
import com.example.composecustomnavigation.navhelper.rememberNavigator

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MainNavHost() {

    val navigator = rememberNavigator<MainGraph>()
    CustomNavHost(navigator = navigator, startDestination = MainGraph.HomeScreen()) {

        navigationComposable<MainGraph.HomeScreen> { navBackStackEntry, data ->
            HomeScreen(data.startDestination)
        }

        navigationComposable<MainGraph.OtherScreen> { navBackStackEntry, data ->
            OtherScreen(value = data.value)
        }

        navigationComposable<MainGraph.AnotherScreen> { navBackStackEntry, data ->
            AnotherScreen(value = data.value)
        }

        navigationComposable<MainGraph.SomeOtherScreen> { navBackStackEntry, data ->
            SomeotherScreen(value = data.value1, value1 = data.value2)
        }
    }
}