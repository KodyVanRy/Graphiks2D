package com.desitum.library.drawing

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.NinePatch
import com.badlogic.gdx.graphics.g2d.TextureRegion

/**
 * Created by kodyvanry on 5/1/17.
 */

class Drawable {

    // THESE SHOULD BE COPIED WHEN GETTING A DRAWABLE FROM A DRAWABLE
    var textureRegion: TextureRegion? = null
        private set
    var ninePatch: NinePatch? = null
        private set
    var color: Color = Color.WHITE
        set(value) {
            ninePatch?.let { ninePatch = NinePatch(it, value) }
            field = value
        }

    val width: Float
        get() = textureRegion?.regionWidth?.plus(0f) ?: ninePatch?.totalWidth!!

    val height: Float
        get() = textureRegion?.regionWidth?.plus(0f) ?: ninePatch?.totalWidth!!


    // THESE SHOULD **NOT** BE COPIED WHEN GETTING A DRAWABLE FROM A DRAWABLE
    var rotation = 0f

    constructor(texture: Texture) {
        this.textureRegion = TextureRegion(texture)
    }

    constructor(textureRegion: TextureRegion) {
        this.textureRegion = textureRegion
    }

    constructor(ninePatch: NinePatch) {
        this.ninePatch = ninePatch
    }

    constructor(drawable: Drawable) {
        this.textureRegion = drawable.textureRegion
        this.ninePatch = drawable.ninePatch
        this.color = drawable.color
    }

    @JvmOverloads
    fun draw(batch: Batch, x: Float, y: Float, width: Float, height: Float,
             originX: Float = width / 2, originY: Float = height / 2,
             scaleX: Float = 1f, scaleY: Float = 1f,
             rotation: Float = 0f) {
        ninePatch?.draw(batch, x, y, Math.max(width, ninePatch!!.totalWidth), Math.max(height, ninePatch!!.totalHeight))
        val originalColor = batch.color
        batch.color = color
        textureRegion?.let { batch.draw(it, x, y, originX, originY, width, height, scaleX, scaleY, rotation) }
        batch.color = originalColor
    }

    companion object {

        @JvmStatic fun loadDrawable(filePath: String): Drawable {
            return loadDrawable(Texture(filePath), false)
        }

        @JvmStatic fun loadDrawable(filePath: String, isNinePatch: Boolean): Drawable {
            return loadDrawable(Texture(filePath), isNinePatch)
        }

        @JvmStatic fun loadDrawable(texture: Texture, isNinePatch: Boolean): Drawable {
            if (isNinePatch) {
                val width = texture.width
                val height = texture.height
                return Drawable(
                        NinePatch(
                                texture,
                                width / 3,
                                width / 3,
                                height / 3,
                                height / 3
                        )
                )
            }
            return Drawable(texture)
        }
    }
}
