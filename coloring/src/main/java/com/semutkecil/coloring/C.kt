package com.semutkecil.coloring

import android.graphics.Color

/**
 * Created by Acenosekai on 9/1/2017.
 * Rock On
 */
class C(var name: String, var color: Int) {
    fun withAlpha(factor: Float): C {
        val alpha = Math.round(255F * factor)
        val red = Color.red(color)
        val green = Color.green(color)
        val blue = Color.blue(color)
        color =  Color.argb(alpha, red, green, blue)
        return this
    }
}