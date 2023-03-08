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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.composecustomnavigation.HomeGraph
import com.example.composecustomnavigation.MainGraph
import com.example.composecustomnavigation.navhelper.LocalNavigator
import com.example.composecustomnavigation.navhelper.getNavigator
import com.example.composecustomnavigation.navhelper.rememberNavigator


@Composable
fun HomeScreen(startBottomNavDestination: HomeGraph = HomeGraph.DashboardScreen) {

    val navigator = rememberNavigator<HomeGraph>()

    Scaffold(bottomBar = {
        BottomNavigation {
            val navBackStackEntry by navigator.navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination
            val items = HomeGraph.getBottomNavDataList()
            items.forEach { item ->
                BottomNavigationItem(
                    icon = { Icon(item.bottomBarData.second, "") },
                    label = { Text(item.bottomBarData.first, color = Color.Black) },
                    selected = currentDestination?.hierarchy?.any { it.route == item.route } == true,
                    onClick = {
                        navigator.navigateTo(item, popupTo = HomeGraph.DashboardScreen::class)
                    }
                )
            }
        }

    }) {
        HomeBottomNavHost(navigator, modifier = Modifier.padding(it), startBottomNavDestination)
    }
}

@Composable
fun OtherScreen(value: String) {
    val navigator = LocalNavigator.getNavigator(MainGraph::class)

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
            navigator?.navigateTo(destination = MainGraph.AnotherScreen(1))
        }) {
            Text(text = "Go to Another Screen")
        }

        Button({
            navigator?.popBackStack()
        }) {
            Text(text = "Back")
        }
    }
}

@Composable
fun AnotherScreen(value: Int) {
    val navigator = LocalNavigator.getNavigator(MainGraph::class)

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
            navigator?.navigateTo(destination = MainGraph.SomeOtherScreen(1, "some-value"))
        }) {
            Text(text = "Go to Some other screen")
        }

        Button({
            navigator?.popBackStackTo(
                MainGraph.HomeScreen(HomeGraph.ProfileScreen("Passed data from Another screen"))
            )
        }) {
            Text(text = "Go to Profile")
        }

        Button({
            navigator?.popBackStack()
        }) {
            Text(text = "Back")
        }
    }
}


@Composable
fun SomeotherScreen(value: Int, value1: String) {
    val navigator = LocalNavigator.getNavigator(MainGraph::class)

    Column(modifier = Modifier
        .fillMaxSize()
        .background(Color.Red.copy(alpha = 0.3f)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Text(modifier = Modifier, text = "Someother screen")
        Text(modifier = Modifier, text = "Data passed: $value , $value1")

        Button({
            navigator?.popBackStackTo(MainGraph.OtherScreen::class)
        }) {
            Text(text = "Back to Other screen")
        }
    }
}