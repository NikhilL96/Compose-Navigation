package com.example.navigationcore

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidableCompositionLocal
import kotlin.reflect.KClass

fun <D : Destination<*>> KClass<out D>.getRoute(): String {
    return this.simpleName ?: ""
}

@Suppress("UNCHECKED_CAST")
@Composable
fun <T : Destination<T>> ProvidableCompositionLocal<NavControllerMap>.getNavController(
    graph: KClass<T>
): ComposeNavHostController<T> {
    return this.current[graph.simpleName] as ComposeNavHostController<T>
}