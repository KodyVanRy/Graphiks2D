package com.desitum.library.view

/**
 * Created by kodyvanry on 5/1/17.
 */

open class LayoutConstraints(var x: Float, var y: Float, var width: Float, var height: Float,
                             margin: Float = 0f,
                             var marginStart: Float = 0f,
                             var marginEnd: Float = 0f,
                             var marginTop: Float = 0f,
                             var marginBottom: Float = 0f,
                             val alignParentStart: Boolean = false,
                             val alignParentEnd: Boolean = false,
                             val alignParentTop: Boolean = false,
                             val alignParentBottom: Boolean = false,
                             val alignCenterInParent: Boolean = false
                             ) {

    init {
        if (margin != 0f) {
            marginStart = margin
            marginEnd = margin
            marginTop = margin
            marginBottom = margin
        }
    }

    companion object {
        val WRAP_CONTENT = -1f
        val MATCH_PARENT = -2f

        val CENTER_HORIZONTAL = -1f
        val CENTER_VERTICAL = -2f
    }
}
