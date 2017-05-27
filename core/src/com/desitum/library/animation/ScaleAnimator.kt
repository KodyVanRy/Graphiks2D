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
 * Created by dvan6234 on 2/24/2015.
 */
class ScaleAnimator : Animator {

    private var startScale: Float = 0f
    private var endScale: Float = 0f

    var scaleSize: Float = 0f
        private set

    private var growing: Boolean = false

    private var interpolator: Interpolator? = null

    var controllingX: Boolean = false
        private set
        @JvmName("isControllingX") get

    var controllingY: Boolean = false
        private set
        @JvmName("isControllingY") get

    constructor(duration: Float, startScale: Float, endScale: Float, interpolator: Int) : this(duration, 0f, startScale, endScale, interpolator)

    constructor(duration: Float, delay: Float, startScale: Float, endScale: Float, interpolator: Int) : this(null, duration, delay, startScale, endScale, interpolator, false, false)

    constructor(sprite: Sprite?, duration: Float, delay: Float, startScale: Float, endScale: Float, interpolator: Int, controlWidth: Boolean, controlHeight: Boolean) : super(sprite, duration, delay) {
        this.startScale = startScale
        this.endScale = endScale

        this.controllingX = controlWidth
        this.controllingY = controlHeight

        growing = startScale <= endScale

        setupInterpolator(interpolator)
    }

    override fun updateAnimation() {
        if (growing) {
            scaleSize = startScale + (endScale - startScale) * interpolator!!.getInterpolation(timeInAnimation)
        } else {
            scaleSize = startScale - (startScale - endScale) * interpolator!!.getInterpolation(timeInAnimation)
        }

        if (controllingY) {
            sprite?.setScale(sprite!!.scaleX, this.scaleSize)
        }
        if (controllingX) {
            sprite?.setScale(this.scaleSize, sprite!!.scaleY)
        }

        onAnimationFinishedListener
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

    override fun duplicate(): Animator {
        return ScaleAnimator(sprite, duration, delay, startScale, endScale, Interpolation.getInterpolatorNum(interpolator), controllingX, controllingY)
    }
}
// 150