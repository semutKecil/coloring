package com.semutkecil.coloring

import android.graphics.Color

/**
 * Created by Acenosekai on 8/30/2017.
 * Rock On
 */

class Coloring {
    var primary = Color.parseColor("#03A9F4")
        set(value) {
            set("primary", value)
            field = value
        }
        get() = get("primary")

    var base = Color.parseColor("#FFFFFF")
        set(value) {
            set("base", value)
            field = value
        }
        get() = get("base")

    var accent = Color.parseColor("#4CAF50")
        set(value) {
            set("accent", value)
            field = value
        }
        get() = get("accent")

    var primaryText = Color.parseColor("#FFFFFF")
        set(value) {
            set("primaryText", value)
            field = value
        }
        get() = get("primaryText")

    var baseText = Color.parseColor("#212121")
        set(value) {
            set("baseText", value)
            field = value
        }
        get() = get("baseText")

    var accentText = Color.parseColor("#FFFFFF")
        set(value) {
            set("accentText", value)
            field = value
        }
        get() = get("accentText")

    fun set(name: String, color: Int) {
        if (!colors.any { it.name == name }) {
            colors.add(C(name, color))
        } else {
            colors.first { it.name == name }.color = color
        }
        refresh(name, "")

    }

    fun get(name: String): Int {
        return try {
            colors.first { it.name == name }.color
        } catch (e: Exception) {
            Color.parseColor("#FFFFFF")
        }
    }

    fun refresh(vararg colorNames: String = emptyArray()) {
        if (colorNames.isEmpty()) {
            onChange.forEach { it.act() }
        } else {
            colorNames.forEach {
                onChange.filter { ev -> it == ev.colorName }.forEach { ev -> ev.act() }
            }
        }

    }

    fun addOnColorChange(act: () -> Unit, vararg colorNames: String = emptyArray()) {
        if (colorNames.isEmpty()) {
            val ev = Ev("", act)
            if(!onChange.contains(ev)) onChange.add(Ev("", act))
        } else {
            colorNames.forEach {
                val ev = Ev(it, act)
                if(!onChange.contains(ev)) onChange.add(Ev(it, act))
            }
        }
    }

    private object Holder {
        var instance: Coloring? = null
    }

    companion object {
        @Synchronized
        fun instance(): Coloring {
            if (Holder.instance == null) {
                val t = Coloring()
                t.primary = Color.parseColor("#03A9F4")
                t.base = Color.parseColor("#FFFFFF")
                t.accent = Color.parseColor("#4CAF50")
                t.primaryText = Color.parseColor("#FFFFFF")
                t.baseText = Color.parseColor("#212121")
                t.accentText = Color.parseColor("#FFFFFF")

                Holder.instance = t
            }

            return Holder.instance!!
        }
    }


    private var colors = mutableListOf<C>()
    private var onChange = mutableListOf<Ev>()

    private class Ev(var colorName: String, var act: () -> Unit)
    private class C(var name: String, var color: Int)
}
