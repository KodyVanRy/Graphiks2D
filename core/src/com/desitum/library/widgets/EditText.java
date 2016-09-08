package com.desitum.library.widgets;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Vector3;
import com.desitum.library.drawing.Drawing;
import com.desitum.library.math.CollisionDetection;

/**
 * Created by kody on 12/15/15.
 * can be used by kody and people in [kody}]
 */
public class EditText extends Widget {

    public static final int TEXT = 0;
    public static final int PASSWORD = 1;

    public static final String INPUT_TEXT = "text";
    public static final String INPUT_PASSWORD = "password";

    private String text, hint, displayText;
    private int alignment, type;
    private float underlineHeight, blinkOnTime, blinkOffTime, blinkTime;
    private boolean blink, focus;
    private BitmapFont font;
    private Texture underline;
    private GlyphLayout glyphLayout;
    private Color textColor;
    private Color hintColor;

    public EditText(Texture background, String name, float width, float height, float X, float Y, BitmapFont font) {
        super(background, name, width, height, X, Y);
        this.text = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        this.font = font;
        glyphLayout = new GlyphLayout(font, text);
        setupFontSize();
        displayText = "";
        text = "";
        hint = "Name";
        textColor = Color.WHITE;
        hintColor = new Color(0.2f, 0.2f, 0.2f, 0.3f);
        this.blinkOffTime = 0.4f;
        this.blinkOnTime = 0.6f;
        this.focus = false;
        this.alignment = LinearLayout.ALIGNMENT_LEFT;
        this.underlineHeight = height * 0.05f;
        this.type = TEXT;
    }

    public static int parseInputType(String inputtype) {
        if (inputtype.equals(INPUT_TEXT)) {
            return TEXT;
        } else if (inputtype.equals(INPUT_PASSWORD)) {
            return PASSWORD;
        }
        return 0;
    }

    @Override
    public void update(float delta) {
        super.update(delta);

        if (focus) {
            updateTextInput();
            updateBlink(delta);
        }
    }

    @Override
    public void updateTouchInput(Vector3 mousePos, boolean clickDown) {
        // Android has to come before super.updateTouchInput due to complications with 4 popups.
        if (isVisible()) {
            if (Gdx.app.getType() == Application.ApplicationType.Android) {
                if (getClickIsDown() && !clickDown && pointInWidget(mousePos)) {
                    Input.TextInputListener inputListener = new Input.TextInputListener() {
                        @Override
                        public void input(String input) {
                            text = input;
                        }

                        @Override
                        public void canceled() {

                        }
                    };
                    Gdx.input.getTextInput(inputListener, hint, text, "");
                }
            }

            super.updateTouchInput(mousePos, clickDown);

            if (clickDown) {
                focus = false;
                if (CollisionDetection.pointInRectangle(getBoundingRectangle(), mousePos)) {
                    if (Gdx.app.getType() == Application.ApplicationType.Desktop) {
                        focus = true;
                    }
                }
            }
        }
    }

    private void setupFontSize() {
        float x = 0;
        float z = 0;
        while (x < getHeight() * 0.6f) {
            z += 0.01f;
            font.getData().setScale(z);
            x = font.getCapHeight();
        }
        font.getData().setScale(z - 0.01f);
    }

    public void setUnderline(Texture texture) {
        this.underline = texture;
    }

    public void setUnderlineHeight(float underlineHeight) {
        this.underlineHeight = underlineHeight;
    }

    public void setUnderlineColor(Color color) {
        float scale = 50 / this.underlineHeight;
        this.underline = Drawing.getTextureRoundedRectangle((int) (this.getWidth() * scale), (int) (underlineHeight * scale), (int) (underlineHeight * scale), color);
    }

    @Override
    public void draw(Batch batch, Camera camera) {
        super.draw(batch, camera);
        if (underline != null) batch.draw(underline, getX(), getY(), getWidth(), underlineHeight);
        font.setColor(textColor);
        if (alignment == LinearLayout.ALIGNMENT_LEFT)
            font.draw(batch, getDisplayText() + getBlinkString(), getX() + getHeight() * 0.2f, getY() + getHeight() * 0.8f);
        if (alignment == LinearLayout.ALIGNMENT_CENTER)
            font.draw(batch, getDisplayText() + getBlinkString(), getX() + getWidth() / 2 - getTextWidth() / 2, getY() + getHeight() * 0.8f);
        if (alignment == LinearLayout.ALIGNMENT_RIGHT)
            font.draw(batch, getDisplayText() + getBlinkString(), getX() + getWidth() - getTextWidth() - getHeight() * 0.2f, getY() + getHeight() * 0.8f);
        if (text.length() == 0 && !focus) {
            font.setColor(hintColor);
            if (alignment == LinearLayout.ALIGNMENT_LEFT)
                font.draw(batch, hint, getX() + getHeight() * 0.2f, getY() + getHeight() * 0.8f);
            if (alignment == LinearLayout.ALIGNMENT_CENTER)
                font.draw(batch, hint, getX() + getWidth() / 2 - getHintWidth() / 2, getY() + getHeight() * 0.8f);
            if (alignment == LinearLayout.ALIGNMENT_RIGHT)
                font.draw(batch, hint, getX() + getWidth() - getHintWidth() - getHeight() * 0.2f, getY() + getHeight() * 0.8f);
        }
    }

    private String getDisplayText() {
        if (type == TEXT) return getText();
        return displayText;
    }

    public float getTextWidth() {
        if (type == TEXT) glyphLayout.setText(font, getDisplayText());
        return glyphLayout.width;
    }

    public float getHintWidth() {
        glyphLayout.setText(font, hint);
        return glyphLayout.width;
    }

    public void updateTextInput() {
        String toAppend = "";
        if (Gdx.input.isKeyJustPressed(Input.Keys.A)) {
            toAppend = "a";
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.B)) {
            toAppend = "b";
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.C)) {
            toAppend = "c";
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.D)) {
            toAppend = "d";
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.E)) {
            toAppend = "e";
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.F)) {
            toAppend = "f";
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.G)) {
            toAppend = "g";
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.H)) {
            toAppend = "h";
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.I)) {
            toAppend = "i";
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.J)) {
            toAppend = "j";
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.K)) {
            toAppend = "k";
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.L)) {
            toAppend = "l";
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.M)) {
            toAppend = "m";
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.N)) {
            toAppend = "n";
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.O)) {
            toAppend = "o";
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.P)) {
            toAppend = "p";
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.Q)) {
            toAppend = "q";
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.R)) {
            toAppend = "r";
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.S)) {
            toAppend = "s";
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.T)) {
            toAppend = "t";
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.U)) {
            toAppend = "u";
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.V)) {
            toAppend = "v";
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.W)) {
            toAppend = "w";
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.X)) {
            toAppend = "x";
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.Y)) {
            toAppend = "y";
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.Z)) {
            toAppend = "z";
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_0)) {
            toAppend = "0";
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)) {
            toAppend = "1";
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_2)) {
            toAppend = "2";
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_3)) {
            toAppend = "3";
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_4)) {
            toAppend = "4";
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_5)) {
            toAppend = "5";
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_6)) {
            toAppend = "6";
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_7)) {
            toAppend = "7";
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_8)) {
            toAppend = "8";
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_9)) {
            toAppend = "9";
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            toAppend = " ";
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.BACKSPACE)) {
            toAppend = "";
            if (text.length() > 0) text = text.substring(0, text.length() - 1);
            if (displayText.length() > 0)
                displayText = displayText.substring(0, displayText.length() - 1);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT) || Gdx.input.isKeyJustPressed(Input.Keys.SHIFT_RIGHT)) {
            toAppend = toAppend.toUpperCase();
        }
        text += toAppend;
        if (toAppend.length() > 0) {
            displayText = displayText + "*";
        }
    }

    private void updateBlink(float delta) {
        this.blinkTime += delta;
        if (blink) {
            if (blinkTime > blinkOnTime) {
                blink = false;
                blinkTime = 0;
            }
        } else {
            if (blinkTime > blinkOffTime) {
                blink = true;
                blinkTime = 0;
            }
        }
    }

    private String getBlinkString() {
        if (blink && focus) return "|";
        return "";
    }

    public boolean isFocus() {
        return focus;
    }

    public void setFocus(boolean focus) {
        this.focus = focus;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    public int getAlignment() {
        return alignment;
    }

    public void setAlignment(int alignment) {
        this.alignment = alignment;
    }

    public BitmapFont getFont() {
        return font;
    }

    public void setFont(BitmapFont font) {
        this.font = font;
    }

    public float getBlinkOnTime() {
        return blinkOnTime;
    }

    public void setBlinkOnTime(float blinkOnTime) {
        this.blinkOnTime = blinkOnTime;
    }

    public float getBlinkOffTime() {
        return blinkOffTime;
    }

    public void setBlinkOffTime(float blinkOffTime) {
        this.blinkOffTime = blinkOffTime;
    }

    public void setTextColor(Color color) {
        this.textColor = color;
    }

    public void setHintColor(Color color) {
        this.hintColor = color;
    }

    public void setInputType(int inputType) {
        this.type = inputType;
    }
}
