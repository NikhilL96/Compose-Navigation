package com.example.composecustomnavigation.navhelper

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.compositionLocalOf
import androidx.navigation.NavHostController
import java.util.*
import kotlin.reflect.KClass

class CustomNavigator<T : NavigationGraph> (
    val navController: NavHostController,
    private val destinationStack: Stack<T>
) {

    fun setStartDestination(destination: T) {
        if (destinationStack.isEmpty())
            destinationStack.push(destination)
    }

    val currentDestination: T
        get() = destinationStack.peek()

    var previousDestination: T? = null
        private set

    fun navigateTo(
        destination: T,
        popupTo: KClass<out T>
    ) {
        previousDestination = destinationStack.peek()
        while (!destinationStack.isEmpty() && destinationStack.peek()::class != popupTo) {
            destinationStack.pop()
        }
        destinationStack.push(destination)
        navController.navigate(destination.route) {
            popUpTo(popupTo.getRoute())
            launchSingleTop = true
        }
    }

    fun navigateTo(destination: T) {
        previousDestination = destinationStack.peek()
        destinationStack.push(destination)
        navController.navigate(destination.route)
    }

    fun popBackStack() {
        previousDestination = destinationStack.pop()
        navController.popBackStack()
    }

    fun popBackStackTo(destination: KClass<out T>) {
        previousDestination = destinationStack.peek()

        while (destinationStack.peek()::class != destination) {
            destinationStack.pop()
        }

        navController.popBackStack(
            route = destination.getRoute(),
            inclusive = false,
            saveState = false
        )
    }

    fun popBackStackTo(destination: T) {
        previousDestination = destinationStack.peek()

        while (!destinationStack.isEmpty() && destinationStack.peek()::class != destination::class) {
            destinationStack.pop()
        }
        destinationStack.pop()
        destinationStack.push(destination)

        navController.navigate(
            route = destination::class.getRoute()
        ) {
            popUpTo(destination::class.getRoute()) {
                inclusive = true
            }
        }
    }
}

val LocalNavigator =
    compositionLocalOf<Map<KClass<out NavigationGraph>, CustomNavigator<out NavigationGraph>>> { mapOf() }

@Composable
fun <T : NavigationGraph> ProvidableCompositionLocal<Map<KClass<out NavigationGraph>, CustomNavigator<out NavigationGraph>>>.getNavigator(
    key: KClass<T>
): CustomNavigator<T>? {
    return this.current[key] as? CustomNavigator<T>
}