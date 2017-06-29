package com.desitum.library.drawing

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.Texture

/**
 * Created by Zmyth97 on 2/9/2015.
 */
object Drawing {

    @JvmStatic fun getTextureRoundedRectangle(width: Int, height: Int, radius: Int, color: Color): Texture {
        val pixmap: Pixmap

        pixmap = Pixmap(width, height, Pixmap.Format.RGBA8888)
        pixmap.setColor(color)

        // Horizontal Rectangle
        pixmap.fillRectangle(0, radius, pixmap.width, pixmap.height - 2 * radius)

        // Green rectangle
        pixmap.fillRectangle(radius, 0, pixmap.width - 2 * radius, pixmap.height)


        // Bottom-left circle
        pixmap.fillCircle(radius, radius, radius)

        // Top-left circle
        pixmap.fillCircle(radius, pixmap.height - radius, radius)

        // Bottom-right circle
        pixmap.fillCircle(pixmap.width - radius, radius, radius)

        // Top-right circle
        pixmap.fillCircle(pixmap.width - radius, pixmap.height - radius, radius)

        val returnTexture = Texture(pixmap.width, pixmap.height, Pixmap.Format.RGBA8888)
        returnTexture.draw(pixmap, 0, 0)
        return returnTexture
    }

    @JvmStatic fun getHollowRectangle(width: Int, height: Int, lineWidth: Int, color: Color): Texture {
        val pixmap = Pixmap(width, height, Pixmap.Format.RGBA8888)
        pixmap.setColor(color)

        //left rectangle
        pixmap.fillRectangle(0, 0, lineWidth, height)

        //right rectangle
        pixmap.fillRectangle(width - lineWidth, 0, lineWidth, height)

        //top rectangle
        pixmap.fillRectangle(lineWidth, height - lineWidth, width - lineWidth * 2, lineWidth)

        //bottom rectangle
        pixmap.fillRectangle(lineWidth, 0, width - lineWidth * 2, lineWidth)
        val returnTexture = Texture(pixmap.width, pixmap.height, Pixmap.Format.RGBA8888)
        returnTexture.draw(pixmap, 0, 0)
        return returnTexture
    }

    @JvmStatic fun getFilledRectangle(width: Int, height: Int, color: Color): Texture {
        val pixmap = Pixmap(width, height, Pixmap.Format.RGBA8888)
        pixmap.setColor(color)

        // rectangle
        pixmap.fillRectangle(0, 0, width, height)

        val returnTexture = Texture(pixmap.width, pixmap.height, Pixmap.Format.RGBA8888)
        returnTexture.draw(pixmap, 0, 0)
        return returnTexture
    }

    @JvmStatic fun getFilledCircle(radius: Int, color: Color): Texture {
        val pixmap = Pixmap(radius * 2, radius * 2, Pixmap.Format.RGBA8888)
        pixmap.setColor(color)

        // rectangle
        pixmap.fillCircle(radius, radius, radius)

        val returnTexture = Texture(pixmap.width, pixmap.height, Pixmap.Format.RGBA8888)
        returnTexture.draw(pixmap, 0, 0)
        return returnTexture
    }

    @JvmStatic fun getHollowCircle(radius: Int, color: Color): Texture {
        val pixmap = Pixmap(radius * 2, radius * 2, Pixmap.Format.RGBA8888)
        pixmap.setColor(color)

        // rectangle
        pixmap.drawCircle(radius, radius, radius)

        val returnTexture = Texture(pixmap.width, pixmap.height, Pixmap.Format.RGBA8888)
        returnTexture.draw(pixmap, 0, 0)
        return returnTexture
    }

    @JvmStatic fun getDiamondFilled(width: Int, height: Int, color: Color): Texture {
        val pixmap = Pixmap(width, height, Pixmap.Format.RGBA8888)
        pixmap.setColor(color)

        // Diamond
        pixmap.fillTriangle(0, height / 2, width / 2, height, width, height / 2)
        pixmap.fillTriangle(0, height / 2, width / 2, 0, width, height / 2)

        val returnTexture = Texture(pixmap.width, pixmap.height, Pixmap.Format.RGBA8888)
        returnTexture.draw(pixmap, 0, 0)
        return returnTexture
    }
}
