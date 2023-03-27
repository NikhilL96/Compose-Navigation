package com.example.composecustomnavigation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.composecustomnavigation.HomeGraph
import com.example.composecustomnavigation.MainGraph
import com.example.navigationcore.LocalNavController
import com.example.navigationcore.getNavController
import com.example.navigationcore.getRoute
import com.example.navigationcore.rememberDestinationNavController


@Composable
internal fun HomeScreen(startBottomNavDestination: HomeGraph = HomeGraph.DashboardScreen) {

    val mainNavController = LocalNavController.getNavController(graph = MainGraph::class)

    val navController =
        rememberDestinationNavController(startDestination = HomeGraph.DashboardScreen)

    Scaffold(bottomBar = {
        BottomNavigation {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination
            val items = HomeGraph.getBottomNavDataList()
            items.forEach { item ->
                BottomNavigationItem(
                    icon = { Icon(item.bottomBarData.second, "") },
                    label = { Text(item.bottomBarData.first, color = Color.Black) },
                    selected = currentDestination?.hierarchy?.any { it.route == item.route } == true,
                    onClick = {
                        navController.navigate(item) {
                            popUpTo(HomeGraph.DashboardScreen.route)
                            launchSingleTop = true
                        }
                    }
                )
            }
        }

    }) {
        HomeBottomNavHost(navController, modifier = Modifier.padding(it), startBottomNavDestination)
    }


    LaunchedEffect(key1 = Unit) {
        mainNavController
            .currentBackStackEntry
            ?.savedStateHandle
            ?.getLiveData<HomeGraph>(MainGraph.HomeScreen.BACK_RESULT_KEY)
            ?.value?.let {
                navController.navigate(it) {
                    popUpTo(HomeGraph.DashboardScreen.route)
                    launchSingleTop = true
                }
            }
        mainNavController
            .currentBackStackEntry
            ?.savedStateHandle?.remove<HomeGraph>(MainGraph.HomeScreen.BACK_RESULT_KEY)
    }
}

@Composable
internal fun OtherScreen(value: String) {
    val navController = LocalNavController.getNavController(MainGraph::class)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Cyan.copy(alpha = 0.3f)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Other screen")
        Text(modifier = Modifier, text = "Data passed: $value")
        Button({
            navController.navigate(destination = MainGraph.AnotherScreen(1))
        }) {
            Text(text = "Go to Another Screen")
        }

        Button({
            navController.popBackStack()
        }) {
            Text(text = "Back")
        }
    }
}

@Composable
internal fun AnotherScreen(value: Int) {
    val navController = LocalNavController.getNavController(MainGraph::class)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Magenta.copy(alpha = 0.3f)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Another screen")
        Text(modifier = Modifier, text = "Data passed: $value")

        Button({
            navController.navigate(destination = MainGraph.SomeOtherScreen(1, "some-value"))
        }) {
            Text(text = "Go to Some other screen")
        }

        Button({
            navController.popBackStack(
                destinationTo = MainGraph.HomeScreen::class,
                sendBackData = Pair(
                    MainGraph.HomeScreen.BACK_RESULT_KEY,
                    HomeGraph.ProfileScreen("Passed data from Another screen")
                )
            )
        }) {
            Text(text = "Go to Profile")
        }

        Button({
            navController.popBackStack()
        }) {
            Text(text = "Back")
        }
    }
}


@Composable
internal fun SomeotherScreen(value: Int, value1: String) {
    val navController = LocalNavController.getNavController(MainGraph::class)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Red.copy(alpha = 0.3f)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(modifier = Modifier, text = "Someother screen")
        Text(modifier = Modifier, text = "Data passed: $value , $value1")

        Button({
            navController.popBackStack(MainGraph.OtherScreen::class.getRoute(), false)
        }) {
            Text(text = "Back to Other screen")
        }
    }
}