package com.desitum.library.animation

/**
 * Created by kodyvanry on 6/30/17.
 */

class AnimatorSet(vararg animators: Animator) : Animator(animators.sortedByDescending { it.duration + it.delay }[0].totalTime, animators.sortedBy { it.delay }[0].delay) {

    var animators: Array<Animator> = animators.asList().toTypedArray()


    override fun update(delta: Float) {
        super.update(delta)
        animators.forEach { it.update(delta) }
    }

    override fun duplicate(): Animator {
        return AnimatorSet(*animators);
    }

    override fun updateAnimation() {
        // Empty. Animator set doesn't do anything itself. It just holds multiple animators.
    }

}
