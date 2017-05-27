package com.desitum.library.game

import com.badlogic.gdx.graphics.g2d.NinePatch
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable
import com.desitum.library.view.TouchEvent

/**
 * Created by kodyvanry on 5/2/17.
 */

abstract class G2DSprite : com.badlogic.gdx.graphics.g2d.Sprite(), Comparable<G2DSprite> {

    var layer = 0

    override fun compareTo(other: G2DSprite): Int {
        return layer - other.layer
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
