package com.desitum.library.animation

/**
 * Created by kodyvanry on 6/30/17.
 */

class AnimatorSet(vararg animators: Animator) : Animator(animators.sortedByDescending { it.duration + it.delay }[0].totalTime, animators.sortedBy { it.delay }[0].delay) {

    var animators: Array<Animator> = animators.asList().toTypedArray()

    val size: Int
        get() = animators.size

    val totalDuration: Float
        get() {
            animators.maxBy { it.duration + it.delay }?.let {
                return it.duration + it.delay
            }
            return 0f
        }

    override fun update(delta: Float) {
        super.update(delta)
        animators.forEach { it.update(delta) }
    }

    override fun start() {
        super.start()
        animators.forEach { it.start() }
    }

    override fun duplicate(): Animator {
        return AnimatorSet(*animators);
    }

    override fun updateAnimation() {
        // Empty. Animator set doesn't do anything itself. It just holds multiple animators.
    }
}
