package com.example.composecustomnavigation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
internal fun DashboardScreen() {
    Box(modifier = Modifier.fillMaxSize()) {
        DashboardNavHost(modifier = Modifier.fillMaxSize())
    }
}



@Composable
internal fun NotificationScreen() {
    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color.Yellow.copy(alpha = 0.3f))) {
        Text(modifier = Modifier.align(Alignment.Center), text = "Notifications screen")
    }
}


@Composable
internal fun ProfileScreen(data: String) {
    Column(modifier = Modifier
        .fillMaxSize()
        .background(Color.Blue.copy(alpha = 0.3f)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Text(modifier = Modifier, text = "Profile screen")
        Text(modifier = Modifier, text = "Data passed: $data")
    }
}