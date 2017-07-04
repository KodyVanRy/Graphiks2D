package com.desitum.library.view

import com.badlogic.gdx.Application
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.GlyphLayout
import com.badlogic.gdx.utils.viewport.Viewport
import com.desitum.library.game.World
import com.desitum.library.widgets.LinearLayout

/**
 * Created by kody on 12/15/15.
 * can be used by kody and people in [kody}]
 */
open class EditText @JvmOverloads constructor(world: World, layoutConstraints: LayoutConstraints? = null, var font: BitmapFont) : View(world, layoutConstraints) {

    var text: String
    var hint: String
    private var displayText: String
        get() {
            if (type == TEXT) return text
            return field
        }
    var alignment: Int = 0
    var type: Int = 0
    var blinkOnTime: Float = 0f
    var blinkOffTime: Float = 0f
    var blinkTime: Float = 0f
    private var blink: Boolean = false
    private val glyphLayout: GlyphLayout
    var textColor: Color
    var hintColor: Color

    val textWidth: Float
        get() {
            if (type == TEXT) glyphLayout.setText(font, displayText)
            return glyphLayout.width
        }

    val hintWidth: Float
        get() {
            glyphLayout.setText(font, hint)
            return glyphLayout.width
        }

    private val blinkString: String
        get() {
            if (blink) return "|"
            return ""
        }

    init {
        this.text = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
        glyphLayout = GlyphLayout(font, text)
        setupFontSize()
        displayText = ""
        text = ""
        hint = ""
        textColor = Color.BLACK
        hintColor = Color(0.2f, 0.2f, 0.2f, 0.3f)
        this.blinkOffTime = 0.4f
        this.blinkOnTime = 0.6f
        this.alignment = LinearLayout.ALIGNMENT_LEFT
        this.type = TEXT
    }

    override fun update(delta: Float) {
        super.update(delta)
        if (focus) {
            updateTextInput(delta)
            updateBlink(delta)
        } else {
            blink = false
        }
    }

    override fun dispatchLayout() {
        super.dispatchLayout()
        setupFontSize()
    }

    override fun onTouchEvent(touchEvent: TouchEvent): Boolean {
        if (enabled) {
            if (touchEvent.action == TouchEvent.Action.UP) {
                if (Gdx.app.type == Application.ApplicationType.Android) {
                    val inputListener = object : Input.TextInputListener {
                        override fun input(input: String) {
                            text = input
                        }

                        override fun canceled() {

                        }
                    }
                    Gdx.input.getTextInput(inputListener, hint, text, "")
                }
            }
        }
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

    override fun onDraw(batch: Batch, viewport: Viewport) {
        //        Rectangle scissor = new Rectangle();
        //        Rectangle clipBounds = new Rectangle(getX(), getY(), getWidth() + 1, getHeight() + 1);
        //        camera.calculateScissors(batch.getTransformMatrix(), clipBounds, scissor);
        //        ScissorStack.pushScissors(scissor);

        super.onDraw(batch, viewport)
        font.color = textColor
        if (alignment == LinearLayout.ALIGNMENT_LEFT)
            font.draw(batch, displayText + blinkString, x + height * 0.2f, y + height * 0.8f)
        if (alignment == LinearLayout.ALIGNMENT_CENTER)
            font.draw(batch, displayText + blinkString, x + width / 2 - textWidth / 2, y + height * 0.8f)
        if (alignment == LinearLayout.ALIGNMENT_RIGHT)
            font.draw(batch, displayText + blinkString, x + width - textWidth - height * 0.2f, y + height * 0.8f)
        if (text.isEmpty() && !focus) {
            font.color = hintColor
            if (alignment == LinearLayout.ALIGNMENT_LEFT)
                font.draw(batch, hint, x + height * 0.2f, y + height * 0.8f)
            if (alignment == LinearLayout.ALIGNMENT_CENTER)
                font.draw(batch, hint, x + width / 2 - hintWidth / 2, y + height * 0.8f)
            if (alignment == LinearLayout.ALIGNMENT_RIGHT)
                font.draw(batch, hint, x + width - hintWidth - height * 0.2f, y + height * 0.8f)
        }
        //        batch.flush();

        //        ScissorStack.popScissors();
    }

    fun updateTextInput(delta: Float) {
        var toAppend = ""
        if (Gdx.input.isKeyJustPressed(Input.Keys.A)) {
            toAppend = "a"
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.B)) {
            toAppend = "b"
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.C)) {
            toAppend = "c"
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.D)) {
            toAppend = "d"
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.E)) {
            toAppend = "e"
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.F)) {
            toAppend = "f"
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.G)) {
            toAppend = "g"
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.H)) {
            toAppend = "h"
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.I)) {
            toAppend = "i"
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.J)) {
            toAppend = "j"
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.K)) {
            toAppend = "k"
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.L)) {
            toAppend = "l"
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.M)) {
            toAppend = "m"
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.N)) {
            toAppend = "n"
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.O)) {
            toAppend = "o"
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.P)) {
            toAppend = "p"
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.Q)) {
            toAppend = "q"
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.R)) {
            toAppend = "r"
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.S)) {
            toAppend = "s"
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.T)) {
            toAppend = "t"
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.U)) {
            toAppend = "u"
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.V)) {
            toAppend = "v"
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.W)) {
            toAppend = "w"
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.X)) {
            toAppend = "x"
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.Y)) {
            toAppend = "y"
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.Z)) {
            toAppend = "z"
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_0)) {
            toAppend = "0"
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)) {
            toAppend = "1"
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_2)) {
            toAppend = "2"
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_3)) {
            toAppend = "3"
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_4)) {
            toAppend = "4"
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_5)) {
            toAppend = "5"
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_6)) {
            toAppend = "6"
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_7)) {
            toAppend = "7"
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_8)) {
            toAppend = "8"
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_9)) {
            toAppend = "9"
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.COLON)) {
            toAppend = ":"
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.SEMICOLON)) {
            toAppend = ";"
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.COMMA)) {
            toAppend = ","
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.PERIOD)) {
            toAppend = "."
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.POUND)) {
            toAppend = "#"
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.APOSTROPHE)) {
            toAppend = "'"
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.STAR)) {
            toAppend = "*"
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.SLASH)) {
            toAppend = "/"
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.EQUALS)) {
            toAppend = "="
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.PLUS)) {
            toAppend = "+"
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.MINUS)) {
            toAppend = "-"
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            toAppend = " "
        } else if (Gdx.input.isKeyPressed(Input.Keys.BACKSPACE)) {
            toAppend = ""
            if (text.isNotEmpty()) text = text.substring(0, text.length - 1)
        }

        if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT) || Gdx.input.isKeyJustPressed(Input.Keys.SHIFT_RIGHT)) {
            toAppend = toAppend.toUpperCase()
        }
        text += toAppend
        if (toAppend.isNotEmpty()) {
            displayText += "*"
        }
    }

    private fun updateBlink(delta: Float) {
        this.blinkTime += delta
        if (blink) {
            if (blinkTime > blinkOnTime) {
                blink = false
                blinkTime = 0f
            }
        } else {
            if (blinkTime > blinkOffTime) {
                blink = true
                blinkTime = 0f
            }
        }
    }

    override fun setSize(width: Float, height: Float) {
        super.setSize(width, height)
        setupFontSize()
    }

    companion object {

        val TEXT = 0
        val PASSWORD = 1

        val INPUT_TEXT = "mText"
        val INPUT_PASSWORD = "password"
        val KEY_REPEAT = 0.05f

        fun parseInputType(inputtype: String): Int {
            if (inputtype == INPUT_TEXT) {
                return TEXT
            } else if (inputtype == INPUT_PASSWORD) {
                return PASSWORD
            }
            return 0
        }
    }
}
