package com.desitum.library.particles

/**
 * Created by kody on 4/8/15.
 * can be used by kody and people in []
 */
class ParticleSettings(var minWidth: Float, var maxWidth: Float, private var minHeight: Float, private var maxHeight: Float,
                       var minGravityX: Float, var maxGravityX: Float, var minGravityY: Float, var maxGravityY: Float,
                       var minVelocityX: Float, var maxVelocityX: Float, var minVelocityY: Float, var maxVelocityY: Float,
                       var minRotationAmount: Float, var maxRotationAmount: Float, var opacity: Float, var lifespan: Float,
                       var isFadeOut: Boolean, private var square: Boolean) {

    val width: Float
        get() {
            lastWidth = Math.random().toFloat() * (maxWidth - minWidth) + minWidth
            return lastWidth
        }

    val height: Float
        get() {
            if (square)
                return lastWidth
            return Math.random().toFloat() * (maxHeight - minHeight) + minHeight
        }

    val gravityX: Float
        get() = Math.random().toFloat() * (maxGravityX - minGravityX) + minGravityX

    val gravityY: Float
        get() = Math.random().toFloat() * (maxGravityY - minGravityY) + minGravityY

    val velocityX: Float
        get() = Math.random().toFloat() * (maxVelocityX - minVelocityX) + minVelocityX

    val velocityY: Float
        get() = Math.random().toFloat() * (maxVelocityY - minVelocityY) + minVelocityY

    val rotationAmount: Float
        get() = Math.random().toFloat() * (maxRotationAmount - minRotationAmount) + minRotationAmount

    companion object {
        private var lastWidth: Float = 0f
    }
}
