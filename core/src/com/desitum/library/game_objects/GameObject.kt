package com.desitum.library.game_objects

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.utils.viewport.Viewport
import com.desitum.library.animation.Animator
import com.desitum.library.animation.MovementAnimator
import com.desitum.library.drawing.Drawable
import com.desitum.library.game.G2DSprite
import com.desitum.library.game.World
import com.desitum.library.view.TouchEvent
import java.util.*

/**
 * Created by kody on 12/27/15.
 * can be used by kody and people in [kody}]
 */
open class GameObject(textureRegion: TextureRegion?, world: World?) : G2DSprite() {
    var animators: ArrayList<Animator>
        private set
    var onFinishedMovingListener: OnFinishedMovingListener? = null

    var speed: Float = 0f
    private var speedX: Float = 0f
    private var speedY: Float = 0f
    var gravityX: Float = 0f
    var gravityY: Float = 0f
    var rotationSpeed: Float = 0f
    var rotationResistance: Float = 0f
    var focus: Boolean = false
    var focusable = false
        @JvmName("isFocusable") get
    var drawable: Drawable? = null
    private var moveTo: FloatArray? = null
    private var world: World? = null

    init {
        this.world = world
        textureRegion?.let { setRegion(it) }
    }

    override fun update(delta: Float) {
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
        if (!animators.isEmpty()) {
            animators.forEach { animator -> animator.update(delta) }
            animators.removeIf { animator -> animator.didFinish() }
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

    fun smoothMoveTo(x: Float, y: Float, duration: Float, interpolation: Int) {
        val xAnimator = MovementAnimator(this, getX(), x, duration, 0f, interpolation, true, false)
        val yAnimator = MovementAnimator(this, getY(), y, duration, 0f, interpolation, false, true)
        xAnimator.start(true)
        yAnimator.start(true)
        this.animators.add(xAnimator)
        this.animators.add(yAnimator)
    }

    fun addAndStartAnimator(anim: Animator) {
        anim.sprite = this
        animators.add(anim)
        anim.start(false)
    }

    fun addAnimator(anim: Animator) {
        anim.sprite = this
        animators.add(anim)
    }

    fun dispose() {
        try {
            texture.dispose()
        } catch (e: Exception) {
            // Texture has been disposed of elsewhere
        }
    }

    // -----------------------------
    // region Input methods
    // -----------------------------
    fun dispatchTouchEvent(touchEvent: TouchEvent): Boolean {
        if (touchEvent.action === TouchEvent.Action.DOWN && focusable) {
            world?.requestFocus(this)
        }
        return onTouchEvent(touchEvent)
    }

    open fun onTouchEvent(touchEvent: TouchEvent): Boolean {
        return true
    }

    fun isTouching(touchEvent: TouchEvent): Boolean {
        return boundingRectangle.contains(touchEvent.x, touchEvent.y)
    }
    // -----------------------------
    // endregion
    // -----------------------------

//    // -----------------------------
//    // region Drawing methods
//    // -----------------------------
    fun draw(batch: Batch, viewport: Viewport) {
        if (texture != null) draw(batch)
        /*
        Need to draw in the following order

        1. Background
        2. Draw view itself
        3. Draw any possible children
        4. Draw foreground
         */
        // 1. Draw background
        drawable?.draw(batch, x, y, width, height)

        // 2. Draw view itself
        onDraw(batch, viewport)

        // 3. Draw any possible children
        dispatchDraw(batch, viewport)

        // 4. Draw the foreground / view decorations
        drawForeground(batch, viewport)
    }

    open fun onDraw(spriteBatch: Batch, viewport: Viewport) {

    }

    fun dispatchDraw(spriteBatch: Batch, viewport: Viewport) {

    }

    fun drawForeground(spriteBatch: Batch, viewport: Viewport) {

    }
//    // -----------------------------
//    // endregion
//    // -----------------------------

    fun clearFocus() {
        focus = false
    }

    companion object {
        val DEFAULT_Z = 0
    }

    init {
        animators = ArrayList<Animator>()
        z = DEFAULT_Z
    }
}
