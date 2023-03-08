package com.example.composecustomnavigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.composecustomnavigation.navhelper.NavigationGraph
import kotlinx.parcelize.Parcelize


@Parcelize
sealed class MainGraph(val title: String) : NavigationGraph {
    class HomeScreen(val startDestination: HomeGraph = HomeGraph.DashboardScreen) :
        MainGraph("Home")

    class OtherScreen(val value: String) : MainGraph("Other")
    class AnotherScreen(val value: Int) : MainGraph("Another")
    class SomeOtherScreen(val value1: Int, val value2: String) : MainGraph("Some other")
}

@Parcelize
sealed class HomeGraph(val bottomBarData: Pair<String, ImageVector>) : NavigationGraph {
    object DashboardScreen : HomeGraph(Pair("Dashboard", Icons.Filled.Home))
    object NotificationScreen : HomeGraph(Pair("Notifications", Icons.Filled.Notifications))
    class ProfileScreen(val value: String) : HomeGraph(Pair("Profile", Icons.Filled.Person))

    companion object {
        fun getBottomNavDataList() = listOf(
            DashboardScreen, NotificationScreen, ProfileScreen("Nothing value")
        )
    }
}

@Parcelize
sealed class DashboardGraph : NavigationGraph {
    object DashboardScreen1 : DashboardGraph()
    object DashboardScreen2 : DashboardGraph()
    object DashboardScreen3 : DashboardGraph()
}