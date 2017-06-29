package com.desitum.library.game

import com.desitum.library.view.TouchEvent

/**
 * Created by kodyvanry on 5/2/17.
 */

abstract class G2DSprite : com.badlogic.gdx.graphics.g2d.Sprite(), Comparable<G2DSprite> {

    var z = 0

    override fun compareTo(other: G2DSprite): Int {
        return z - other.z
    }

    abstract fun update(delta: Float)

    fun onTouchDown(touchEvent: TouchEvent): Boolean {
        return false
    }

    fun onTouchMoved(touchEvent: TouchEvent): Boolean {
        return false
    }

    fun onTouchUp(touchEvent: TouchEvent): Boolean {
        return false
    }
}
