package com.desitum.library.view

import com.desitum.library.drawing.Drawable
import com.desitum.library.game.World

/**
 * Created by kodyvanry on 5/1/17.
 */

open class Button @JvmOverloads constructor(world: World, layoutConstraints: LayoutConstraints? = null) : View(world, layoutConstraints) {

    // DRAWING
    var restDrawable: Drawable? = null
        set(value) {
            backgroundDrawable = backgroundDrawable ?: value
            field = value
        }


    var hoverDrawable: Drawable? = null
        set(value) {
            backgroundDrawable = backgroundDrawable ?: value
            field = value
        }

    init {
        clickable = true
    }

    override fun onTouchEvent(touchEvent: TouchEvent): Boolean {
        when (touchEvent.action) {
            TouchEvent.Action.DOWN -> if (hoverDrawable != null)
                backgroundDrawable = hoverDrawable
            TouchEvent.Action.MOVE -> if (isTouching(touchEvent) && hoverDrawable != null)
                backgroundDrawable = hoverDrawable
            else -> backgroundDrawable = restDrawable
        }
        return super.onTouchEvent(touchEvent)
    }
}
