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
import com.desitum.library.view.LayoutConstraints
import com.desitum.library.view.View

/**
 * Created by kody on 2/24/15.
 * can be used by kody and people in []
 */
class MarginAnimator @JvmOverloads constructor(
        sprite: Sprite?,
        startMargins: LayoutConstraints,
        endMargins: LayoutConstraints,
        duration: Float,
        interpolator: Int,
        delay: Float = 0f
        ) : Animator(sprite, duration, delay) {

    var startMargins: LayoutConstraints = startMargins
        private set
    var endMargins: LayoutConstraints = endMargins
        private set
    var distance: Float = 0f
        private set
    var currentMargins: LayoutConstraints = startMargins
        private set

    var distanceMarginStart = endMargins.marginStart - startMargins.marginStart
    var distanceMarginEnd = endMargins.marginEnd - startMargins.marginEnd
    var distanceMarginTop = endMargins.marginTop - startMargins.marginTop
    var distanceMarginBottom = endMargins.marginBottom - startMargins.marginBottom

    private var interpolator: Interpolator? = null

    init {
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
        currentMargins = startMargins
    }

    override fun updateAnimation() {
        currentMargins.marginStart = interpolator!!.getInterpolation(timeInAnimation) * distanceMarginStart + startMargins.marginStart
        currentMargins.marginEnd = interpolator!!.getInterpolation(timeInAnimation) * distanceMarginEnd + startMargins.marginEnd
        currentMargins.marginTop = interpolator!!.getInterpolation(timeInAnimation) * distanceMarginTop + startMargins.marginTop
        currentMargins.marginBottom = interpolator!!.getInterpolation(timeInAnimation) * distanceMarginBottom + startMargins.marginBottom
        if (sprite is View)
            (sprite as View).layoutConstraints = currentMargins
    }

    override fun duplicate(): Animator {
        return MarginAnimator(this.sprite!!, this.startMargins, this.endMargins, duration, Interpolation.getInterpolatorNum(this.interpolator), delay)
    }
}
