package com.desitum.library.view

/**
 * Created by kodyvanry on 4/19/17.
 */

class TouchEvent {

    enum class Action {
        DOWN,
        UP,
        MOVE,
        NONE
    }

    var action: Action
    var x: Float = 0.toFloat()
    var y: Float = 0.toFloat()

    init {
        action = Action.NONE
        x = 0f
        y = 0f
    }
}
