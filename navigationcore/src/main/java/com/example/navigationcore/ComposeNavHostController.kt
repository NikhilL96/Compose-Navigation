package com.example.navigationcore

import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.coroutineScope
import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.compose.DialogNavigator
import androidx.navigation.navOptions
import com.google.accompanist.navigation.animation.AnimatedComposeNavigator
import kotlinx.coroutines.launch
import kotlin.reflect.KClass

class ComposeNavHostController<D : Destination<D>>(
    lifecycleOwner: LifecycleOwner,
    context: Context,
    val startDestination: D,
    private val graphClass: KClass<D>
) : NavHostController(context) {

    private val _currentDestinationMap: MutableMap<String, MutableList<D>> =
        mutableMapOf(startDestination.route to mutableListOf(startDestination))

    val currentDestinationMap: Map<String, MutableList<D>> = _currentDestinationMap

    init {
        lifecycleOwner.lifecycle.coroutineScope.launch {
            visibleEntries.collect {
                if (it.size == 1) {
                    with(backQueue.fold(mutableMapOf<String, Int>()) { acc, entry ->
                        val entryElementRoute = entry.destination.route ?: return@fold acc
                        val routeCount = acc[entry.destination.route] ?: 0
                        acc[entryElementRoute] = routeCount + 1
                        acc
                    }) {
                        currentDestinationMap.keys.forEach { route ->
                            val backStackDestCount = this[route]?:0
                            val currentDestCount = currentDestinationMap[route]?.count()?:0

                            repeat(currentDestCount - backStackDestCount) {
                                currentDestinationMap[route]?.removeLastOrNull()
                            }
                        }
                    }
                }
            }
        }
    }

    fun navigate(destination: D, builder: NavOptionsBuilder.() -> Unit = {}) {
        _currentDestinationMap[destination.route]?.add(destination) ?: run {
            _currentDestinationMap[destination.route] = mutableListOf(destination)
        }

        navigate(route = destination.route, navOptions = navOptions(builder))
    }


    fun <T> popBackStack(sendBackData: Pair<String, T>): Boolean {
        previousBackStackEntry?.savedStateHandle?.apply {
            set(sendBackData.first, sendBackData.second)
        }
        return super.popBackStack()
    }

    fun <T> popBackStack(
        route: String,
        inclusive: Boolean,
        sendBackData: Pair<String, T>,
        savedState: Boolean = false
    ): Boolean {
        backQueue.lastOrNull {
            it.destination.route == route
        }?.savedStateHandle?.apply {
            set(sendBackData.first, sendBackData.second)
        }
        return super.popBackStack(route, inclusive, savedState)
    }


    override fun saveState(): Bundle? {
        val bundle = super.saveState()
        val destinationList = _currentDestinationMap.values.flatten()
        bundle?.putParcelableArrayList("stackMap", ArrayList<D>(destinationList))
        return bundle
    }

    @Suppress("DEPRECATION")
    override fun restoreState(navState: Bundle?) {
        val destinationList = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
            navState?.getParcelableArrayList("stackMap", graphClass.java)
        else
            navState?.getParcelableArrayList("stackMap")

        destinationList?.forEach { screen ->
            _currentDestinationMap[screen.route]?.add(screen) ?: run {
                _currentDestinationMap[screen.route] = mutableListOf(screen)
            }
        }

        super.restoreState(navState)
    }
}

private fun <D : Destination<D>> navExtrasControllerSaver(
    lifecycleOwner: LifecycleOwner,
    context: Context,
    graphType: KClass<D>,
    startDestination: D
): Saver<ComposeNavHostController<D>, *> = Saver(
    save = { it.saveState() },
    restore = {
        createNavExtrasController(
            lifecycleOwner,
            context,
            graphType,
            startDestination
        ).apply { restoreState(it) }
    }
)

private fun <D : Destination<D>> createNavExtrasController(
    lifecycleOwner: LifecycleOwner,
    context: Context,
    graphType: KClass<D>,
    startDestination: D
): ComposeNavHostController<D> =
    ComposeNavHostController(lifecycleOwner, context, startDestination = startDestination, graphType).apply {
        navigatorProvider.addNavigator(ComposeNavigator())
        navigatorProvider.addNavigator(DialogNavigator())
    }

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun <D : Destination<D>> rememberDestinationNavController(
    startDestination: D,
): ComposeNavHostController<D> {
    val graphType = startDestination.graphType
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val animatedNavigator = remember { AnimatedComposeNavigator() }

    return rememberSaveable(
        inputs = arrayOf(animatedNavigator),
        saver = navExtrasControllerSaver(lifecycleOwner, context, graphType, startDestination)
    ) {
        createNavExtrasController(lifecycleOwner, context, graphType, startDestination)
    }.apply {
        navigatorProvider.addNavigator(animatedNavigator)
    }
}