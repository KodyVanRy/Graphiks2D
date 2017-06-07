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

    var textureRegion: TextureRegion? = null
    private set
    private var ninePatch: NinePatch? = null
    private var color: Color? = null

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

    fun setColor(color: Color) {
        this.color = color
        ninePatch?.let { ninePatch = NinePatch(it, color) }
    }

    fun draw(batch: Batch, x: Float, y: Float, width: Float, height: Float) {
        ninePatch?.draw(batch, x, y, Math.max(width, ninePatch!!.totalWidth), Math.max(height, ninePatch!!.totalHeight))
        val originalColor = batch.color
        color?.let { batch.color = it }
        textureRegion?.let { batch.draw(it, x, y, width, height) }
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
