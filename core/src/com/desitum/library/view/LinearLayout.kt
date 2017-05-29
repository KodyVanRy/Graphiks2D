package com.desitum.library.view

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.utils.viewport.Viewport
import com.desitum.library.game.World

/**
 * Created by kodyvanry on 5/9/17.
 */

open class LinearLayout @JvmOverloads constructor(world: World, layoutConstraints: LayoutConstraints? = null) : ViewGroup(world, layoutConstraints) {

    internal enum class Orientation {
        HORIZONTAL,
        VERTICAL
    }

    private val mOrientation = Orientation.VERTICAL

    override fun addView(v: View) {
        super.addView(v)
        v.layoutConstraints = getNewLayoutConstraints(v)
        v.layer = layer + 1
        v.parent = this
        world?.addView(v)
    }

    open protected fun getNewLayoutConstraints(v: View): LayoutConstraints {
        val layoutConstraints: LayoutConstraints = v.layoutConstraints

        if (mOrientation == Orientation.HORIZONTAL) {
            if (layoutConstraints.width == LayoutConstraints.MATCH_PARENT)
                throw RuntimeException(v.toString() + " : " + v.name + " - Parent orientation is HORIZONTAL. View cannot have width of MATCH_PARENT")
            if (layoutConstraints.width == 0f)
                layoutConstraints.width = v.width
            if (layoutConstraints.height == 0f)
                layoutConstraints.height = LayoutConstraints.MATCH_PARENT
            if (layoutConstraints.x == LayoutConstraints.CENTER_HORIZONTAL)
                throw RuntimeException(v.toString() + " : " + v.name + " - Parent orientation is HORIZONTAL. View cannot center horizontally")
            else
                layoutConstraints.x = newX
            layoutConstraints.y = 0f
        } else if (mOrientation == Orientation.VERTICAL) {
            if (layoutConstraints.height == LayoutConstraints.MATCH_PARENT)
                throw RuntimeException(v.toString() + " : " + v.name + " - Parent orientation is VERTICAL. View cannot have height of MATCH_PARENT")
            if (layoutConstraints.height == 0f)
                layoutConstraints.height = v.height
            if (layoutConstraints.width == 0f)
                layoutConstraints.width = LayoutConstraints.MATCH_PARENT
            if (layoutConstraints.y == LayoutConstraints.CENTER_VERTICAL)
                throw RuntimeException(v.toString() + " : " + v.name + " - Parent orientation is VERTICAL. View cannot center vertically")
            else
                layoutConstraints.y = newY
        }
        return layoutConstraints
    }

    private val newX: Float
        get() {
            return children.map { it.layoutConstraints.width }.sum()
        }

    private val newY: Float
        get() {
            return height - children.map { it.layoutConstraints.height }.sum() + layoutConstraints.height
        }

    companion object {
        val ALIGNMENT_TOP = 0
        val ALIGNMENT_BOTTOM = 1
        val ALIGNMENT_CENTER = 2
        val ALIGNMENT_RIGHT = 1
        val ALIGNMENT_LEFT = 0
    }
}
