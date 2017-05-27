@file:JvmName("Animator")

package com.desitum.library.animation

import com.badlogic.gdx.graphics.g2d.Sprite

/**
 * Created by kody on 2/24/2015
 */
abstract class Animator {

    var sprite: Sprite? = null
    var duration: Float = 0f
    var timeInAnimation: Float = 0f
    var delay: Float = 0f
    var currentDelay: Float = 0f
    var isRunning: Boolean = false
    var isRan: Boolean = false
    var onAnimationFinishedListener: OnAnimationFinishedListener? = null

    constructor(sprite: Sprite?, duration: Float, delay: Float) {
        this.sprite = sprite
        this.duration = duration
        this.delay = delay
    }

    constructor(duration: Float, delay: Float) : this(null, duration, delay)

    fun update(delta: Float) {
        if (!isRunning) {
            return
        }
        currentDelay += delta
        if (currentDelay >= delay) {
            timeInAnimation += delta / duration
        }
        if (timeInAnimation >= 1) {
            timeInAnimation = 1f
            isRunning = false
            isRan = true
            onAnimationFinishedListener?.onAnimationFinished(this)
        }
        updateAnimation()
    }

    fun start(isProtectedWhileRunning: Boolean) {
        if (isProtectedWhileRunning && isRunning) {
            reset()
            isRunning = true
        } else if (!isProtectedWhileRunning) {
            reset()
            isRunning = true
        }
    }

    open fun reset() {
        this.isRunning = false
        this.timeInAnimation = 0f
        this.currentDelay = 0f
    }

    abstract fun duplicate(): Animator

    fun didFinish(): Boolean {
        return isRan && !isRunning
    }

    protected abstract fun updateAnimation()
    //endregion
}
