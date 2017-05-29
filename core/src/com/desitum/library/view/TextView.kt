package com.desitum.library.view

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.GlyphLayout
import com.badlogic.gdx.utils.viewport.Viewport
import com.desitum.library.game.World


/**
 * Created by kody on 12/15/15.
 * can be used by kody and people in [kody}]
 */

class TextView @JvmOverloads constructor(world: World, layoutConstraints: LayoutConstraints? = null, var font: BitmapFont) : View(world, layoutConstraints) {

    var text: String = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
    var alignment: Int = 0
    private val glyphLayout: GlyphLayout
    var textColor: Color

    init {
        glyphLayout = GlyphLayout(font, text)
        setupFontSize()
        text = ""
        textColor = Color.BLACK
        this.alignment = LinearLayout.ALIGNMENT_LEFT
    }

    override fun update(delta: Float) {
        super.update(delta)
    }

    override fun onTouchEvent(touchEvent: TouchEvent): Boolean {
        return super.onTouchEvent(touchEvent)
    }

    private fun setupFontSize() {
        var x = 0f
        var z = 0f
        while (x < height * 0.6f) {
            z += 0.01f
            font.data.setScale(z)
            x = font.capHeight
        }
        font.data.setScale(z - 0.01f)
    }

    override fun draw(batch: Batch, camera: Viewport) {

        super.draw(batch, camera)
        font.color = textColor
        if (alignment == LinearLayout.ALIGNMENT_LEFT)
            font.draw(batch, text, x + height * 0.2f, y + height * 0.8f)
        if (alignment == LinearLayout.ALIGNMENT_CENTER)
            font.draw(batch, text, x + width / 2 - textWidth / 2, y + height * 0.8f)
        if (alignment == LinearLayout.ALIGNMENT_RIGHT)
            font.draw(batch, text, x + width - textWidth - height * 0.2f, y + height * 0.8f)
    }

    val textWidth: Float
        get() {
            glyphLayout.setText(font, text)
            return glyphLayout.width
        }

    override fun setSize(width: Float, height: Float) {
        super.setSize(width, height)
        setupFontSize()
    }
}
