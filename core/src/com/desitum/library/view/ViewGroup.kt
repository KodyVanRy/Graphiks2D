package com.desitum.library.view

import com.desitum.library.game.World

import java.util.ArrayList

/**
 * Created by kodyvanry on 5/1/17.
 */

open class ViewGroup @JvmOverloads constructor(world: World, layoutConstraints: LayoutConstraints? = null) : View(world, layoutConstraints) {

    var children: MutableList<View> = ArrayList<View>()

    open fun addView(v: View) {
        children.add(v)
        v.z = z + 1
        v.parent = this
        world?.addView(v)
    }

    open fun removeView(v: View) {
        children.remove(v)
        world?.removeView(v)
    }

    override fun invalidate() {
        super.invalidate()
        children.forEach { it.invalidate() }
    }

    fun hideViews(vararg ids: String) {
        ids.forEach {
        }
    }
}
