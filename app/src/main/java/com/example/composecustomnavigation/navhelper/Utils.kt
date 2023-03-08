package com.example.composecustomnavigation.navhelper

import kotlin.reflect.KClass

fun <D : Destination> KClass<out D>.getRoute(): String {
    return this.simpleName ?: ""
}

fun <D : NavigationGraph> KClass<out D>.getGraph(): KClass<out NavigationGraph> {
    return NavigationGraph.graphDestinationMapping.keys.first {
        NavigationGraph.graphDestinationMapping.getValue(it).contains(this)
    }
}