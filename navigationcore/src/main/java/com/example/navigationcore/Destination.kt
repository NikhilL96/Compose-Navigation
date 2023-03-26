package com.example.navigationcore

import android.os.Parcelable
import kotlin.reflect.KClass


abstract class Destination<D: Destination<D>> : Parcelable  {

    val route: String
        get() = this::class.getRoute()

    abstract val graphType: KClass<D>

}