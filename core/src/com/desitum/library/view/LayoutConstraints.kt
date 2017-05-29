package com.desitum.library.view

/**
 * Created by kodyvanry on 5/1/17.
 */

open class LayoutConstraints(var x: Float, var y: Float, var width: Float, var height: Float) {

    companion object {
        val WRAP_CONTENT = -1f
        val MATCH_PARENT = -2f

        val CENTER_HORIZONTAL = -1f
        val CENTER_VERTICAL = -2f
    }
}
