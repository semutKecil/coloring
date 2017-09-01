package com.semutkecil.coloring

import android.graphics.Color

/**
 * Created by Acenosekai on 8/30/2017.
 * Rock On
 */

class Coloring {
    fun set(name: String, color: Int) {
        setFunc(name, color, {
            if (unappliedColor.any { c -> c.name == it.name }) {
                unappliedColor.first { c -> c.name == it.name }.color = color
            } else {
                unappliedColor.add(it)
            }
        })
    }

    fun setAndApply(name: String, color: Int) {
        setFunc(name, color, { applySpecific(it, false, name, "") })
    }

    private fun setFunc(name: String, color: Int, cb: (c: C) -> Unit) {
        var c = C(name, color)
        if (!colors.any { it.name == name }) {
            colors.add(c)
            setFunc(name, color, { applySpecific(it, true, name, "") })
            cb(c)
        } else {
            c = colors.first { it.name == name }
            if (c.color != color) {
                c.color = color
                setFunc(name, color, { applySpecific(it, true, name, "") })
                cb(c)
            }
        }
    }


    private fun applySpecific(c: C, auto: Boolean, vararg colorNames: String) {
        colorNames.forEach {
            onChange.filter { it.auto == auto }.filter { it.listen.any { s -> colorNames.any { s2 -> s2 == s } } }.forEach { it.act(mutableListOf(c)) }
        }
    }

    fun get(name: String): C {
        return try {
            colors.first { it.name == name }
        } catch (e: Exception) {
            colors[0]
        }
    }

    fun apply() {
        onChange.filter { unappliedColor.map { u -> u.name }.any { s -> it.listen.any { s2 -> s2 == s } } || it.listen.any { s -> s == "" } }.forEach {
            it.act(unappliedColor)
        }
        unappliedColor.clear()
    }

    fun addOnColorChange(onApply: OnApply) {
        if (!onChange.contains(onApply)) onChange.add(onApply)
    }

    fun removeOnColorChange(onApply: OnApply) {
        onChange.remove(onApply)
    }

    private object Holder {
        var instance: Coloring? = null
    }

    companion object {
        @Synchronized
        fun instance(): Coloring {
            if (Holder.instance == null) {
                val t = Coloring()
                t.set("primary", Color.parseColor("#03A9F4"))
                t.set("base", Color.parseColor("#FFFFFF"))
                t.set("accent", Color.parseColor("#4CAF50"))
                t.set("primaryText", Color.parseColor("#FFFFFF"))
                t.set("baseText", Color.parseColor("#212121"))
                t.set("accentText", Color.parseColor("#FFFFFF"))
                Holder.instance = t
            }

            return Holder.instance!!
        }


    }

    private var unappliedColor = mutableListOf<C>()
    private var colors = mutableListOf<C>()
    private var onChange = mutableListOf<OnApply>()

    class OnApply(var act: (c: MutableList<C>) -> Unit, var listen: List<String>, var auto: Boolean = false)
}
