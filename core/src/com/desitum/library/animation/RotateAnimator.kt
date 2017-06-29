package com.desitum.library.animation

import com.badlogic.gdx.graphics.g2d.Sprite
import com.desitum.library.interpolation.AccelerateDecelerateInterpolator
import com.desitum.library.interpolation.AccelerateInterpolator
import com.desitum.library.interpolation.AnticipateInterpolator
import com.desitum.library.interpolation.BounceInterpolator
import com.desitum.library.interpolation.DecelerateInterpolator
import com.desitum.library.interpolation.Interpolation
import com.desitum.library.interpolation.Interpolator
import com.desitum.library.interpolation.LinearInterpolator
import com.desitum.library.interpolation.OvershootInterpolator

/**
 * Created by kody on 12/21/15.
 * can be used by kody and people in [kody}]
 */
class RotateAnimator : Animator {

    private var amountToRotate: Float = 0f
    private var startRotation: Float = 0f
    private var endRotation: Float = 0f
    var currentRotation: Float = 0f
        private set

    private var interpolator: Interpolator? = null

    constructor(duration: Float, delay: Float, amountToRotate: Float, interpolator: Int) : this(null, duration, delay, 0f, amountToRotate, interpolator)

    constructor(sprite: Sprite?, duration: Float, delay: Float, startRotation: Float, endRotation: Float, interpolator: Int) : super(sprite, duration, delay) {
        this.startRotation = startRotation
        this.endRotation = endRotation
        this.amountToRotate = endRotation - startRotation
        setupInterpolator(interpolator)
    }

    override fun updateAnimation() {
        currentRotation = startRotation + amountToRotate * interpolator!!.getInterpolation(timeInAnimation)

        sprite?.rotation = currentRotation
    }

    override fun duplicate(): Animator {
        return RotateAnimator(sprite, duration, delay, startRotation, endRotation, Interpolation.getInterpolatorNum(interpolator))
    }

    private fun setupInterpolator(interpolator: Int) {
        when (interpolator) {
            Interpolation.ACCELERATE_DECELERATE_INTERPOLATOR -> this.interpolator = AccelerateDecelerateInterpolator.`$`()
            Interpolation.ACCELERATE_INTERPOLATOR -> this.interpolator = AccelerateInterpolator.`$`()
            Interpolation.DECELERATE_INTERPOLATOR -> this.interpolator = DecelerateInterpolator.`$`()
            Interpolation.ANTICIPATE_INTERPOLATOR -> this.interpolator = AnticipateInterpolator.`$`()
            Interpolation.OVERSHOOT_INTERPOLATOR -> this.interpolator = OvershootInterpolator.`$`()
            Interpolation.BOUNCE_INTERPOLATOR -> this.interpolator = BounceInterpolator.`$`()
            Interpolation.LINEAR_INTERPOLATOR -> this.interpolator = LinearInterpolator.`$`()
        }
    }
}