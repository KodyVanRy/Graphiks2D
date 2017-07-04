package com.desitum.library.particles

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import com.desitum.library.animation.ColorEffects

/**
 * Created by kody on 12/26/15.
 * can be used by kody and people in [kody}]
 */
class Particle : Sprite {
    /*

    - mLifespan
    - mGravityX
    - gravityY
    - mVelocityX
    - mVelocityY
    - mRotationAmount
    - mOpacity
    - boundaryRectangle

    By just giving the Particle these values you end up dealing with more primitives and less objects.
    This also cuts down on expensive calls such as update(delta) for 4 separate animators
    Animators aren't the cheapest thing in the world, they're cool and useful, but shouldn't be
    used everywhere.
     */

    var currentLife: Float = 0f
    var lifespan: Float = 0f
    var gravityX: Float = 0f
    var gravityY: Float = 0f
    var velocityX: Float = 0f
    var velocityY: Float = 0f
    var rotationAmount: Float = 0f
    var opacity: Float = 0f
    var fadeOut: Boolean = false
        @JvmName("isFadeOut") get
    var remove: Boolean = false
        @JvmName("needToRemove") get
        private set

    private var colorEffects: ColorEffects? = null

    constructor(texture: Texture, width: Float, height: Float, lifespan: Float) : super(texture) {

        setSize(width, height)
        setup(lifespan, 0f, 0f, 0f, 0f, 0f, 1f, true)
    }

    constructor(texture: Texture, x: Float, y: Float, width: Float, height: Float, lifespan: Float, gravityX: Float,
                gravityY: Float, velocityX: Float, velocityY: Float, rotationAmount: Float,
                opacity: Float, fadeOut: Boolean) : super(texture) {

        setSize(width, height)
        setPosition(x, y)
        setup(lifespan, gravityX, gravityY, velocityX, velocityY, rotationAmount, opacity, fadeOut)
    }

    fun update(delta: Float) {
        currentLife += delta
        if (currentLife >= lifespan) {
            currentLife = lifespan
            remove = true
        }
        velocityX += gravityX * delta
        velocityY += gravityY * delta
        this.x += velocityX * delta
        this.y += velocityY * delta
        this.rotation = rotationAmount * currentLife / lifespan
        if (fadeOut) {
            colorEffects?.let {
                it.update(delta)
                setColor(
                        it.currentRed,
                        it.currentGreen,
                        it.currentBlue,
                        it.currentAlpha)
            }
        }
    }

    fun setup(lifespan: Float, gravityX: Float, gravityY: Float, velocityX: Float,
              velocityY: Float, rotationAmount: Float, opacity: Float, fadeOut: Boolean) {

        this.remove = false
        this.currentLife = 0f
        this.lifespan = lifespan
        this.gravityX = gravityX
        this.gravityY = gravityY
        this.velocityX = velocityX
        this.velocityY = velocityY
        this.rotationAmount = rotationAmount
        this.opacity = opacity
        this.fadeOut = fadeOut
        if (fadeOut) {
            colorEffects = ColorEffects(Color(1f, 1f, 1f, 1f), Color(1f, 1f, 1f, 0.0f), lifespan)
            colorEffects!!.start()
        }

        setOrigin(width / 2, height / 2)
    }
}
