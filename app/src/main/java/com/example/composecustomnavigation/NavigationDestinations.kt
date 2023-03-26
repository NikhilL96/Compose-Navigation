package com.example.composecustomnavigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.navigationcore.Destination
import kotlinx.parcelize.Parcelize
import kotlin.reflect.KClass


@Parcelize
internal sealed class MainGraph(val title: String) : com.example.navigationcore.Destination<MainGraph>() {

    override val graphType: KClass<MainGraph>
        get() = MainGraph::class
    class HomeScreen(val startDestination: HomeGraph = HomeGraph.DashboardScreen) :
        MainGraph("Home") {
            companion object{
                const val BACK_RESULT_KEY = "backResultKey"
            }
        }

    class OtherScreen(val value: String) : MainGraph("Other")
    class AnotherScreen(val value: Int) : MainGraph("Another")
    class SomeOtherScreen(val value1: Int, val value2: String) : MainGraph("Some other")
}

@Parcelize
internal sealed class HomeGraph(val bottomBarData: Pair<String, ImageVector>) : Destination<HomeGraph>() {

    override val graphType: KClass<HomeGraph>
        get() = HomeGraph::class
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
internal sealed class DashboardGraph : Destination<DashboardGraph>() {
    override val graphType: KClass<DashboardGraph>
        get() = DashboardGraph::class
    object DashboardScreen1 : DashboardGraph()
    object DashboardScreen2 : DashboardGraph()
    object DashboardScreen3 : DashboardGraph()
}