package com.example.composecustomnavigation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.composecustomnavigation.DashboardGraph
import com.example.composecustomnavigation.MainGraph
import com.example.composecustomnavigation.navhelper.LocalNavigator
import com.example.composecustomnavigation.navhelper.getNavigator


@Composable
fun DashboardScreen1() {

    val navigator = LocalNavigator.getNavigator(DashboardGraph::class)

    Column(
        modifier = Modifier.fillMaxSize().background(Color.LightGray.copy(alpha = 0.3f)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Dashboard screen 1")
        Button({
            navigator?.navigateTo(destination = DashboardGraph.DashboardScreen2)
        }) {
            Text(text = "Go to DashboardScreen2")
        }
    }
}

@Composable
fun DashboardScreen2() {
    val navigator = LocalNavigator.getNavigator(DashboardGraph::class)

    Column(modifier = Modifier.fillMaxSize().background(Color.Gray.copy(alpha = 0.3f)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "Dashboard screen 2")
        Button({
            navigator?.navigateTo(destination = DashboardGraph.DashboardScreen3)
        }) {
            Text(text = "Go to DashboardScreen3")
        }

        Button({
            navigator?.popBackStack()
        }) {
            Text(text = "Back")
        }
    }
}

@Composable
fun DashboardScreen3() {
    val navigator = LocalNavigator.getNavigator(DashboardGraph::class)
    val mainNavigator = LocalNavigator.getNavigator(MainGraph::class)

    Column(modifier = Modifier.fillMaxSize().background(Color.DarkGray.copy(alpha = 0.3f)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "Dashboard screen 3")

        Button({
            mainNavigator?.navigateTo(MainGraph.OtherScreen("Value passed"))
        }) {
            Text(text = "Go to Other screen")
        }

        Button({
            navigator?.popBackStack()
        }) {
            Text(text = "Back")
        }
    }
}