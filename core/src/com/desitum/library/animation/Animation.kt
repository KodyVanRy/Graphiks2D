package com.desitum.library.animation

import com.badlogic.gdx.graphics.Color
import com.desitum.library.interpolation.Interpolation
import com.desitum.library.interpolation.Interpolator

/**
 * Created by kodyvanry on 6/29/17.
 */

enum class Animation private constructor(var animator: Animator) {
    FADE_AWAY(ColorEffects(Color.WHITE, Color(1f, 1f, 1f, 0f), Interpolator.ANIMATION_LONG)),
    FADE_AWAY_FAST(ColorEffects(Color.WHITE, Color(1f, 1f, 1f, 0f), Interpolator.ANIMATION_SHORT)),
    FADE_IN(ColorEffects(Color(1f, 1f, 1f, 0f), Color.WHITE, Interpolator.ANIMATION_LONG)),
    FADE_IN_FAST(ColorEffects(Color(1f, 1f, 1f, 0f), Color.WHITE, Interpolator.ANIMATION_SHORT)),
    SLIDE_OUT_BOTTOM(MovementAnimator(0f, 0f, Interpolator.ANIMATION_SHORT, Interpolation.ACCELERATE_INTERPOLATOR)),
    SLIDE_OUT_TOP(MovementAnimator(0f, 0f, Interpolator.ANIMATION_SHORT, Interpolation.ACCELERATE_INTERPOLATOR)),
    SLIDE_OUT_RIGHT(MovementAnimator(0f, 0f, Interpolator.ANIMATION_SHORT, Interpolation.ACCELERATE_INTERPOLATOR)),
    SLIDE_OUT_LEFT(MovementAnimator(0f, 0f, Interpolator.ANIMATION_SHORT, Interpolation.ACCELERATE_INTERPOLATOR)),
    SHRINK_AWAY(ScaleAnimator(1f, 0f, Interpolator.ANIMATION_SHORT, Interpolation.ACCELERATE_INTERPOLATOR)),
    GROW_FROM_NOTHING(ScaleAnimator(0f, 1f, Interpolator.ANIMATION_SHORT, Interpolation.DECELERATE_INTERPOLATOR)),
}
