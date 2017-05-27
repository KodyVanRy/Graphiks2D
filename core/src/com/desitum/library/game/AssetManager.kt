package com.desitum.library.game

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.files.FileHandle
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.NinePatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.desitum.library.drawing.Drawable

import java.util.ArrayList
import java.util.HashMap

/**
 * Created by kodyvanry on 5/11/17.
 */

class AssetManager private constructor() {

    private var mTextureList: MutableList<Texture>? = null
    private val mDrawableMap: HashMap<Int, Drawable>

    init {
        mTextureList = ArrayList<Texture>()
        mDrawableMap = HashMap<Int, Drawable>()
        //        new Texture(new FileHandle(Gdx.files.internal()))
    }

    fun addTexture(texture: Texture) {
        mTextureList!!.add(texture)
    }

    fun addTexture(texture: String) {
        this.addTexture(Texture(texture))
    }

    fun getTexture(text: Int): Texture {
        return mTextureList!![text]
    }

    fun addDrawable(key: Int, textureRegion: TextureRegion) {
        mDrawableMap.put(key, Drawable(textureRegion))
    }

    fun addDrawable(key: Int, texture: Texture) {
        mDrawableMap.put(key, Drawable(texture))
    }

    fun addDrawable(key: Int, ninePatch: NinePatch) {
        mDrawableMap.put(key, Drawable(ninePatch))
    }

    fun addDrawable(key: Int, drawable: Drawable) {
        mDrawableMap.put(key, drawable)
    }

    fun getDrawable(key: Int): Drawable {
        return mDrawableMap[key].let { it!! }
    }

    companion object {

        @JvmStatic val instance: AssetManager by lazy { AssetManager() }

        @JvmStatic fun dispose() {
            val assetManager: AssetManager = instance
            for (texture in assetManager.mTextureList!!) {
                texture.dispose()
            }
            assetManager.mTextureList = null
        }
    }
}
