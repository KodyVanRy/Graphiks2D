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
 * Created by kody on 2/24/15.
 * can be used by kody and people in []
 */
class MovementAnimator : Animator {

    var startPos: Float = 0f
        private set
    var endPos: Float = 0f
        private set
    var distance: Float = 0f
        private set
    var currentPos: Float = 0f
        private set

    var controllingX: Boolean = false
        @JvmName("controllingX") get
        private set
    var controllingY: Boolean = false
        @JvmName("controllingY") get
        private set
    private var interpolator: Interpolator? = null

    constructor(startPos: Float, endPos: Float, duration: Float, interpolator: Int) : super(duration, 0f) {
        this.startPos = startPos
        this.endPos = endPos
        this.distance = endPos - startPos
        this.currentPos = startPos

        setupInterpolator(interpolator)
    }

    constructor(sprite: Sprite, startPos: Float, endPos: Float, duration: Float, delay: Float, interpolator: Int, controlX: Boolean, controlY: Boolean) : super(sprite, duration, delay) {
        this.startPos = startPos
        this.endPos = endPos
        this.distance = endPos - startPos
        this.currentPos = startPos

        this.controllingX = controlX
        this.controllingY = controlY

        setupInterpolator(interpolator)
    }

    private fun setupInterpolator(interpolator: Int) {
        if (interpolator == Interpolation.ACCELERATE_INTERPOLATOR) {
            this.interpolator = AccelerateInterpolator.`$`()
        } else if (interpolator == Interpolation.DECELERATE_INTERPOLATOR) {
            this.interpolator = DecelerateInterpolator.`$`()
        } else if (interpolator == Interpolation.ANTICIPATE_INTERPOLATOR) {
            this.interpolator = AnticipateInterpolator.`$`()
        } else if (interpolator == Interpolation.OVERSHOOT_INTERPOLATOR) {
            this.interpolator = OvershootInterpolator.`$`()
        } else if (interpolator == Interpolation.ACCELERATE_DECELERATE_INTERPOLATOR) {
            this.interpolator = AccelerateDecelerateInterpolator.`$`()
        } else if (interpolator == Interpolation.BOUNCE_INTERPOLATOR) {
            this.interpolator = BounceInterpolator.`$`()
        } else if (interpolator == Interpolation.LINEAR_INTERPOLATOR) {
            this.interpolator = LinearInterpolator.`$`()
        }
    }

    override fun reset() {
        super.reset()
        currentPos = startPos
    }

    override fun updateAnimation() {
        currentPos = interpolator!!.getInterpolation(timeInAnimation) * distance + startPos

        if (this.controllingX) {
            this.sprite?.x = currentPos
        }
        if (this.controllingY) {
            this.sprite?.y = currentPos
        }
    }

    override fun duplicate(): Animator {
        return MovementAnimator(this.sprite!!, this.startPos, this.endPos, duration, delay, Interpolation.getInterpolatorNum(this.interpolator), this.controllingX, this.controllingY)
    }
}
