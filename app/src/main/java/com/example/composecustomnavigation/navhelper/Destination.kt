package com.example.composecustomnavigation.navhelper

import android.os.Parcelable
import com.example.composecustomnavigation.DashboardGraph
import com.example.composecustomnavigation.HomeGraph
import com.example.composecustomnavigation.MainGraph
import kotlin.reflect.KClass

interface Destination : Parcelable {
    val route: String
        get() = this::class.getRoute()
}

interface NavigationGraph : Destination, Parcelable {
    companion object {
        val graphDestinationMapping = mapOf (
            MainGraph::class to listOf(
                MainGraph.HomeScreen::class,
                MainGraph.OtherScreen::class,
                MainGraph.AnotherScreen::class,
                MainGraph.SomeOtherScreen::class
            ),
            HomeGraph::class to listOf(
                HomeGraph.DashboardScreen::class,
                HomeGraph.ProfileScreen::class,
                HomeGraph.NotificationScreen::class
            ),
            DashboardGraph::class to listOf(
                DashboardGraph.DashboardScreen1::class,
                DashboardGraph.DashboardScreen2::class,
                DashboardGraph.DashboardScreen3::class
            )
        )
    }

    val graph: KClass<out NavigationGraph>
        get() = this::class.getGraph()
}