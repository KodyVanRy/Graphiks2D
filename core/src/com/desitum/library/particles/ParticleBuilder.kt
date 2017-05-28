package com.desitum.library.particles

import com.badlogic.gdx.files.FileHandle
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.desitum.library.drawing.Drawing

import org.json.simple.JSONArray
import org.json.simple.JSONObject
import org.json.simple.parser.JSONParser
import org.json.simple.parser.ParseException

/**
 * Created by kody on 12/27/15.
 * can be used by kody and people in [kody}]
 */
object ParticleBuilder {
    val PARTICLES_PER_SECOND = "particles_per_second"
    val WIDTH = "width"
    val HEIGHT = "height"
    val MAKE_SQUARE = "make_square"
    val X = "x"
    val Y = "y"
    val PARTICLE_TEXTURE = "particle_texture"
    val PARTICLE_SETTINGS = "particle_settings"

    /*
    particle settings :
            minWidth, maxWidth, minHeight, maxHeight, minGravityX, maxGravityX, minGravityY,
            maxGravityY, minVelocityX, maxVelocityX, minVelocityY, maxVelocityY,
            minRotationAmount, maxRotationAmount, opacity;
     */
    val MIN_PARTICLE_WIDTH = "min_width"
    val MAX_PARTICLE_WIDTH = "max_width"
    val MIN_PARTICLE_HEIGHT = "min_height"
    val MAX_PARTICLE_HEIGHT = "max_height"
    val MIN_PARTICLE_GRAVITY_X = "min_gravity_x"
    val MAX_PARTICLE_GRAVITY_X = "max_gravity_x"
    val MIN_PARTICLE_GRAVITY_Y = "min_gravity_y"
    val MAX_PARTICLE_GRAVITY_Y = "max_gravity_y"
    val MIN_VELOCITY_X = "min_velocity_x"
    val MAX_VELOCITY_X = "max_velocity_x"
    val MIN_VELOCITY_Y = "min_velocity_y"
    val MAX_VELOCITY_Y = "max_velocity_y"
    val MIN_PARTICLE_ROTATION = "min_rotation"
    val MAX_PARTICLE_ROTATION = "max_rotation"
    val OPACITY = "opacity"
    val LIFESPAN = "lifespan"
    val FADE_OUT = "fade_out"

    fun buildParticleEmitter(file: FileHandle): ParticleEmitter {
        val json = file.readString()
        val jsonParser = JSONParser()
        val jsonObject = jsonParser.parse(json) as JSONObject
        return getEmitter(jsonObject)
    }

    private fun getEmitter(jsonObject: JSONObject): ParticleEmitter {
        val particleEmitter = ParticleEmitter()

        if (jsonObject[X] != null) {
            particleEmitter.x = java.lang.Float.parseFloat(jsonObject[X] as String)
        }
        if (jsonObject[Y] != null) {
            particleEmitter.y = java.lang.Float.parseFloat(jsonObject[Y] as String)
        }
        if (jsonObject[WIDTH] != null) {
            particleEmitter.width = java.lang.Float.parseFloat(jsonObject[WIDTH] as String)
        }
        if (jsonObject[HEIGHT] != null) {
            particleEmitter.height = java.lang.Float.parseFloat(jsonObject[HEIGHT] as String)
        }
        if (jsonObject[PARTICLES_PER_SECOND] != null) {
            particleEmitter.particlesPerSecond = java.lang.Float.parseFloat(jsonObject[PARTICLES_PER_SECOND] as String)
        }
        if (jsonObject[PARTICLE_TEXTURE] != null) {
            val textureString = jsonObject[PARTICLE_TEXTURE] as String

            if (textureString.startsWith("#")) {
                particleEmitter.particleTexture = Drawing.getFilledRectangle(50, 50, Color.valueOf(textureString))
            } else {
                particleEmitter.particleTexture = Texture(textureString)
            }
        }

        val children = jsonObject[PARTICLE_SETTINGS] as JSONArray
        for (i in children.indices) {
            particleEmitter.addParticleSettings(getParticleSettings(children[i] as JSONObject))
        }

        return particleEmitter
    }

    private fun getParticleSettings(jsonObject: JSONObject): ParticleSettings {
        var minWidth: Float = 0f
        var maxWidth: Float = 0f
        var minHeight: Float = 0f
        var maxHeight: Float = 0f
        var minGravityX: Float = 0f
        var maxGravityX: Float = 0f
        var minGravityY: Float = 0f
        var maxGravityY: Float = 0f
        var minVelocityX: Float = 0f
        var maxVelocityX: Float = 0f
        var minVelocityY: Float = 0f
        var maxVelocityY: Float = 0f
        var minRotationAmount: Float = 0f
        var maxRotationAmount: Float = 0f
        var opacity: Float = 0f
        var lifespan: Float = 0f
        var fadeOut: Boolean = false
        var square: Boolean = false


        jsonObject[MIN_PARTICLE_WIDTH]?.let { minWidth = (it as String).toFloat() }
        jsonObject[MAX_PARTICLE_WIDTH]?.let { maxWidth = (it as String).toFloat() }
        jsonObject[MIN_PARTICLE_HEIGHT]?.let { minHeight = (it as String).toFloat() }
        jsonObject[MAX_PARTICLE_HEIGHT]?.let { maxHeight = (it as String).toFloat() }
        jsonObject[MIN_PARTICLE_GRAVITY_X]?.let { minGravityX = (it as String).toFloat() }
        jsonObject[MAX_PARTICLE_GRAVITY_X]?.let { maxGravityX = (it as String).toFloat() }
        jsonObject[MIN_PARTICLE_GRAVITY_Y]?.let { minGravityY = (it as String).toFloat() }
        jsonObject[MAX_PARTICLE_GRAVITY_Y]?.let { maxGravityY = (it as String).toFloat() }
        jsonObject[MIN_VELOCITY_X]?.let { minVelocityX = (it as String).toFloat() }
        jsonObject[MAX_VELOCITY_X]?.let { maxVelocityX = (it as String).toFloat() }
        jsonObject[MIN_VELOCITY_Y]?.let { minVelocityY = (it as String).toFloat() }
        jsonObject[MAX_VELOCITY_Y]?.let { maxVelocityY = (it as String).toFloat() }
        jsonObject[MIN_PARTICLE_ROTATION]?.let { minRotationAmount = (it as String).toFloat() }
        jsonObject[MAX_PARTICLE_ROTATION]?.let { maxRotationAmount = (it as String).toFloat() }
        jsonObject[OPACITY]?.let { opacity = (it as String).toFloat() }
        jsonObject[LIFESPAN]?.let { lifespan = (jsonObject[LIFESPAN] as String).toFloat() }
        jsonObject[FADE_OUT]?.let { fadeOut = (jsonObject[FADE_OUT] as String).toBoolean() }
        jsonObject[MAKE_SQUARE]?.let { square = (jsonObject[MAKE_SQUARE] as String).toBoolean() }

        return ParticleSettings(minWidth, maxWidth, minHeight, maxHeight, minGravityX, maxGravityX, minGravityY, maxGravityY, minVelocityX, maxVelocityX, minVelocityY, maxVelocityY, minRotationAmount, maxRotationAmount, opacity, lifespan, fadeOut, square)
    }
}
