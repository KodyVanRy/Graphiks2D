package com.desitum.library.animation

import com.badlogic.gdx.graphics.Color

/**
 * Created by dvan6234 on 2/17/2015.
 */
class ColorEffects : Animator {
    private var startRed: Float = 0f
    private var startGreen: Float = 0f
    private var startBlue: Float = 0f
    private var startAlpha: Float = 0f

    private var slopeRed: Float = 0f
    private var slopeGreen: Float = 0f
    private var slopeBlue: Float = 0f
    private var slopeAlpha: Float = 0f

    private var endRed: Float = 0f
    private var endGreen: Float = 0f
    private var endBlue: Float = 0f
    private var endAlpha: Float = 0f

    var currentRed: Float = 0f
        private set
    var currentGreen: Float = 0f
        private set
    var currentBlue: Float = 0f
        private set
    var currentAlpha: Float = 0f
        private set

    constructor(startColor: Color, endColor: Color, duration: Float) : super(duration, 0f) {

        setupColors(startColor, endColor, duration)
    }

    constructor(startColor: Color, endColor: Color, duration: Float, delay: Float) : super(duration, delay) {
        setupColors(startColor, endColor, duration)
    }

    private fun setupColors(startColor: Color, endColor: Color, duration: Float) {
        if (duration <= 0) {
            currentRed = endColor.r
            endRed = endColor.r.toInt().toFloat()
            slopeRed = 0f
            currentGreen = endColor.r
            endGreen = endColor.g.toInt().toFloat()
            slopeGreen = 0f
            currentBlue = endColor.b
            endBlue = endColor.b.toInt().toFloat()
            slopeBlue = 0f
            currentAlpha = endColor.a
            endAlpha = endColor.a.toInt().toFloat()
            slopeAlpha = 0f
            return
        }

        startRed = startColor.r
        startGreen = startColor.g
        startBlue = startColor.b
        startAlpha = startColor.a

        slopeRed = endColor.r - startColor.r
        slopeGreen = endColor.g - startColor.g
        slopeBlue = endColor.b - startColor.b
        slopeAlpha = endColor.a - startColor.a

        currentRed = startColor.r
        currentGreen = startColor.g
        currentBlue = startColor.b
        currentAlpha = startColor.a

        endRed = endColor.r
        endGreen = endColor.g
        endBlue = endColor.b
        endAlpha = endColor.a
    }

    val currentColor: Color
        get() = Color(currentRed, currentGreen, currentBlue, currentAlpha)

    override fun duplicate(): Animator {
        return ColorEffects(Color(startRed, startGreen, startBlue, startAlpha), Color(endRed, endGreen, endBlue, endAlpha), duration)
    }

    override fun updateAnimation() {
        currentRed = slopeRed * timeInAnimation + startRed
        currentGreen = slopeGreen * timeInAnimation + startGreen
        currentBlue = slopeBlue * timeInAnimation + startBlue
        currentAlpha = slopeAlpha * timeInAnimation + startAlpha

        sprite?.color = currentColor
    }

    companion object {

        fun colorsMatch(color1: Color, color2: Color, marginOfError: Float): Boolean {
            if (color1 == color2) return true

            var error = 0f

            error += Math.abs(color1.r - color2.r)
            error += Math.abs(color1.g - color2.g)
            error += Math.abs(color1.b - color2.b)
            error += Math.abs(color1.a - color2.a)

            return error < marginOfError
        }
    }
}

