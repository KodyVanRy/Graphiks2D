package com.desitum.library.particles

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch

import java.util.ArrayList
import java.util.Random

/**
 * Created by kody mIsOn 5/25/15.
 * can be used by kody and people in [kody}]
 */
class ParticleEmitter {

    var x: Float = 0f
    var y: Float = 0f
    var width: Float = 0f
    var height: Float = 0f
    var particlesPerSecond: Float = 0f
    private var currentTime: Float = 0f

    private val randomParticleX: Float
        get() = Math.random().toFloat() * width + x

    private val randomParticleY: Float
        get() = Math.random().toFloat() * height + y

    var particles: ArrayList<Particle>
        private set
    private var deadParticles: ArrayList<Particle>
    var particleTexture: Texture? = null
    var particleSettingsArrayList: ArrayList<ParticleSettings>
    private var randomSettingsChooser: Random
    private var color: Color

    var on: Boolean = false
        private set
        @JvmName("isOn") get

    constructor(x: Float, y: Float, particlesPerSecond: Float) {
        this.particles = ArrayList<Particle>()
        this.deadParticles = ArrayList<Particle>()
        this.particleSettingsArrayList = ArrayList<ParticleSettings>()
        this.randomSettingsChooser = Random()
        this.particlesPerSecond = particlesPerSecond

        this.x = x
        this.y = y

        this.width = 1f
        this.height = 1f
        this.color = Color(1f, 1f, 1f, 1f)
    }

    constructor() {
        this.particles = ArrayList<Particle>()
        this.deadParticles = ArrayList<Particle>()
        this.particleSettingsArrayList = ArrayList<ParticleSettings>()
        this.randomSettingsChooser = Random()
        this.particlesPerSecond = 1f

        this.x = 0f
        this.y = 0f
        this.width = 0f
        this.height = 0f
        this.color = Color(1f, 1f, 1f, 1f)
    }

    fun update(delta: Float) {
        updateParticles(delta)
        updateEmitter(delta)
    }

    private fun updateParticles(delta: Float) {
        particles.forEach {
            it.update(delta)
            if (it.remove)
                deadParticles.add(it)
        }
        particles.removeIf { it.remove }
    }

    private fun updateEmitter(delta: Float) {
        if (on) {
            currentTime += delta
            while (currentTime >= 1.0f / particlesPerSecond) {
                addParticle(createNewParticle())
                currentTime -= 1.0f / particlesPerSecond
            }
        }
    }

    /**
     * Recycles old particle or if there are no spare mParticles, it creates a new one
     * @return [Particle]
     */
    fun createNewParticle(): Particle {
        val particleSettings = particleSettingsArrayList[randomSettingsChooser.nextInt(particleSettingsArrayList.size)]
        if (deadParticles.size > 0) {
            val returnParticle: Particle = deadParticles[0]
            deadParticles.removeAt(0)
            returnParticle.setup(particleSettings.lifespan,
                    particleSettings.gravityX,
                    particleSettings.gravityY,
                    particleSettings.velocityX,
                    particleSettings.velocityY,
                    particleSettings.rotationAmount,
                    particleSettings.opacity,
                    particleSettings.isFadeOut)
            returnParticle.setPosition(randomParticleX, randomParticleY)
            return returnParticle
        }
        return Particle(particleTexture!!, randomParticleX, randomParticleY,
                particleSettings.width, particleSettings.height, particleSettings.lifespan,
                particleSettings.gravityX, particleSettings.gravityY,
                particleSettings.velocityX, particleSettings.velocityY,
                particleSettings.rotationAmount, particleSettings.opacity,
                particleSettings.isFadeOut)
    }

    fun draw(batch: Batch) {
        particles.forEach { it.draw(batch) }
    }

    fun turnOn() {
        on = true
    }

    fun turnOff() {
        on = false
    }

    fun toggleOnOff() {
        on = !on
    }

    fun addParticleSettings(ps: ParticleSettings) {
        particleSettingsArrayList!!.add(ps)
    }

    fun addParticle(particle: Particle) {
        particle.color = color!!
        particles!!.add(particle)
    }

    fun clearAndAddSettings(particleSettings: ParticleSettings) {
        this.particleSettingsArrayList.clear()
        particleSettingsArrayList.add(particleSettings)
    }

    fun setColor(color: Color) {
        this.color = color
    }

    fun dispose() {
        try {
            particleTexture!!.dispose()
        } catch (n: Exception) {
            // Particle has already been disposed elsewhere
        }

    }
}
