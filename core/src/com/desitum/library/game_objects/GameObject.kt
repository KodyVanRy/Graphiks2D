package com.desitum.library.game_objects

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Vector3
import com.desitum.library.animation.Animator
import com.desitum.library.animation.MovementAnimator

import java.util.ArrayList

/**
 * Created by kody on 12/27/15.
 * can be used by kody and people in [kody}]
 */
open class GameObject : Sprite, Comparable<GameObject> {
    private var mAnimators: ArrayList<Animator>
    private var mAnimatorsToRemove: ArrayList<Animator>
    var onFinishedMovingListener: OnFinishedMovingListener? = null
    var z: Int = 0

    var speed: Float = 0f
    private var speedX: Float = 0f
    private var speedY: Float = 0f
    var gravityX: Float = 0f
    var gravityY: Float = 0f
    var rotationSpeed: Float = 0f
    var rotationResistance: Float = 0f
    private var moveTo: FloatArray? = null

    constructor(textureRegion: TextureRegion) : super(textureRegion) {
        mAnimators = ArrayList<Animator>()
        mAnimatorsToRemove = ArrayList<Animator>()
        z = DEFAULT_Z
    }

    constructor(texture: Texture) : super(texture) {
        mAnimators = ArrayList<Animator>()
        mAnimatorsToRemove = ArrayList<Animator>()
        z = DEFAULT_Z
    }

    fun update(delta: Float) {
        updateAnimators(delta)
        updatePosition(delta)
    }

    private fun updatePosition(delta: Float) {
        speedX += gravityX * delta
        speedY += gravityY * delta
        x += speedX
        y += speedY
        rotationSpeed *= rotationResistance
        if (moveTo != null) {
            updateMovement()
        }
    }

    private fun updateAnimators(delta: Float) {
        if (!mAnimators.isEmpty()) {
            mAnimators.forEach { animator -> animator.update(delta) }
            mAnimators.removeIf{ animator -> animator.didFinish() }
        }
    }

    private fun updateMovement() {
        if (speedX < 0) {
            if (x < moveTo!![0]) {
                moveFinished()
            }
        } else if (speedX > 0) {
            if (x > moveTo!![0]) {
                moveFinished()
            }
        }
        if (speedY < 0) {
            if (y < moveTo!![1]) {
                moveFinished()
            }
        } else if (speedY > 0) {
            if (y > moveTo!![1]) {
                moveFinished()
            }
        }
    }

    private fun moveFinished() {
        x = moveTo!![0]
        y = moveTo!![1]
        moveTo = null
        onFinishedMovingListener?.onFinished(this)
    }

    fun moveToPosition(x: Float, y: Float) {
        // x = cos
        // y = sin
        val deltaX = getX() - x
        val deltaY = getY() - y
        val angle = Math.atan2(deltaY.toDouble(), deltaX.toDouble()).toFloat() // in radians
        speedX = Math.cos(angle.toDouble()).toFloat() * speed
        speedY = Math.sin(angle.toDouble()).toFloat() * speed
        moveTo = floatArrayOf(x, y)
    }

    /**
     * Called when user touches the [GameObject]
     * @param touchPos position of touch in [com.badlogic.gdx.graphics.Camera] coordinates
     * *
     * @param touchDown whether the touch was down or not (touchDown, touchUp)
     */
    open fun updateTouchInput(touchPos: Vector3, touchDown: Boolean) {

    }

    fun smoothMoveTo(x: Float, y: Float, duration: Float, interpolation: Int) {
        val xAnimator = MovementAnimator(this, getX(), x, duration, 0f, interpolation, true, false)
        val yAnimator = MovementAnimator(this, getY(), y, duration, 0f, interpolation, false, true)
        xAnimator.start(true)
        yAnimator.start(true)
        this.mAnimators.add(xAnimator)
        this.mAnimators.add(yAnimator)
    }

    fun addAndStartAnimator(anim: Animator) {
        anim.sprite = this
        mAnimators.add(anim)
    }

    override fun compareTo(other: GameObject): Int {
        return this.z - other.z
    }

    fun dispose() {
        try {
            texture.dispose()
        } catch (e: Exception) {
            // Texture has been disposed of elsewhere
        }

    }

    companion object {
        val DEFAULT_Z = 0
    }
}
