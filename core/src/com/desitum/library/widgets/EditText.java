package com.desitum.library.widgets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.desitum.library.drawing.Drawing;

/**
 * Created by kody on 12/15/15.
 * can be used by kody and people in [kody}]
 */
public class EditText extends Widget {

    public static final int TEXT = 0;
    public static final int PASSWORD = 1;

    public static final String INPUT_TEXT = "mText";
    public static final String INPUT_PASSWORD = "password";

    private String mText, mHint, mDisplayText;
    private int mAlignment, mType;
    private float mUnderlineHeight, mBlinkOnTime, mBlinkOffTime, mBlinkTime;
    private boolean mBlink, mFocus;
    private BitmapFont mFont;
    private Texture mUnderline;
    private GlyphLayout mGlyphLayout;
    private Color mTextColor;
    private Color mHintColor;

    public EditText(Texture background, String name, float width, float height, float x, float y, Layout parent, BitmapFont font) {
        super(name, width, height, x, y);
        this.mText = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        this.mFont = font;
        mGlyphLayout = new GlyphLayout(font, mText);
        setupFontSize();
        mDisplayText = "";
        mText = "";
        mHint = "Name";
        mTextColor = Color.WHITE;
        mHintColor = new Color(0.2f, 0.2f, 0.2f, 0.3f);
        this.mBlinkOffTime = 0.4f;
        this.mBlinkOnTime = 0.6f;
        this.mFocus = false;
        this.mAlignment = LinearLayout.ALIGNMENT_LEFT;
        this.mUnderlineHeight = height * 0.05f;
        this.mType = TEXT;
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

        if (mFocus) {
            updateTextInput();
            updateBlink(delta);
        }
    }

//    @Override
//    public void updateTouchInput(Vector3 mousePos, boolean clickDown) {
//        // Android has to come before super.updateTouchInput due to complications with 4 popups.
//        if (isVisible()) {
//            if (Gdx.app.getType() == Application.ApplicationType.Android) {
//                if (isClickDown() && !clickDown && isPointInWidget(mousePos)) {
//                    Input.TextInputListener inputListener = new Input.TextInputListener() {
//                        @Override
//                        public void input(String input) {
//                            mText = input;
//                        }
//
//                        @Override
//                        public void canceled() {
//
//                        }
//                    };
//                    Gdx.input.getTextInput(inputListener, mHint, mText, "");
//                }
//            }
//
////            super.updateTouchInput(mousePos, clickDown);
//
//            if (clickDown) {
//                mFocus = false;
//                if (CollisionDetection.pointInRectangle(getBoundingRectangle(), mousePos)) {
//                    if (Gdx.app.getType() == Application.ApplicationType.Desktop) {
//                        mFocus = true;
//                    }
//                }
//            }
//        }
//    }

    private void setupFontSize() {
        float x = 0;
        float z = 0;
        while (x < getHeight() * 0.6f) {
            z += 0.01f;
            mFont.getData().setScale(z);
            x = mFont.getCapHeight();
        }
        mFont.getData().setScale(z - 0.01f);
    }

    public void setUnderline(Texture texture) {
        this.mUnderline = texture;
    }

    public void setUnderlineHeight(float underlineHeight) {
        this.mUnderlineHeight = underlineHeight;
    }

    public void setUnderlineColor(Color color) {
        float scale = 50 / this.mUnderlineHeight;
        this.mUnderline = Drawing.INSTANCE.getTextureRoundedRectangle((int) (this.getWidth() * scale), (int) (mUnderlineHeight * scale), (int) (mUnderlineHeight * scale), color);
    }

    @Override
    public void draw(Batch batch, Viewport camera) {
        super.draw(batch, camera);
        if (mUnderline != null) batch.draw(mUnderline, getX(), getY(), getWidth(), mUnderlineHeight);
        mFont.setColor(mTextColor);
        if (mAlignment == LinearLayout.ALIGNMENT_LEFT)
            mFont.draw(batch, getDisplayText() + getBlinkString(), getX() + getHeight() * 0.2f, getY() + getHeight() * 0.8f);
        if (mAlignment == LinearLayout.ALIGNMENT_CENTER)
            mFont.draw(batch, getDisplayText() + getBlinkString(), getX() + getWidth() / 2 - getTextWidth() / 2, getY() + getHeight() * 0.8f);
        if (mAlignment == LinearLayout.ALIGNMENT_RIGHT)
            mFont.draw(batch, getDisplayText() + getBlinkString(), getX() + getWidth() - getTextWidth() - getHeight() * 0.2f, getY() + getHeight() * 0.8f);
        if (mText.length() == 0 && !mFocus) {
            mFont.setColor(mHintColor);
            if (mAlignment == LinearLayout.ALIGNMENT_LEFT)
                mFont.draw(batch, mHint, getX() + getHeight() * 0.2f, getY() + getHeight() * 0.8f);
            if (mAlignment == LinearLayout.ALIGNMENT_CENTER)
                mFont.draw(batch, mHint, getX() + getWidth() / 2 - getHintWidth() / 2, getY() + getHeight() * 0.8f);
            if (mAlignment == LinearLayout.ALIGNMENT_RIGHT)
                mFont.draw(batch, mHint, getX() + getWidth() - getHintWidth() - getHeight() * 0.2f, getY() + getHeight() * 0.8f);
        }
    }

    private String getDisplayText() {
        if (mType == TEXT) return getText();
        return mDisplayText;
    }

    public float getTextWidth() {
        if (mType == TEXT) mGlyphLayout.setText(mFont, getDisplayText());
        return mGlyphLayout.width;
    }

    public float getHintWidth() {
        mGlyphLayout.setText(mFont, mHint);
        return mGlyphLayout.width;
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
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.COLON)) {
            toAppend = ":";
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.SEMICOLON)) {
            toAppend = ";";
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.COMMA)) {
            toAppend = ",";
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.PERIOD)) {
            toAppend = ".";
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.POUND)) {
            toAppend = "#";
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.APOSTROPHE)) {
            toAppend = "'";
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.STAR)) {
            toAppend = "*";
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.SLASH)) {
            toAppend = "/";
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.EQUALS)) {
            toAppend = "=";
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.PLUS)) {
            toAppend = "+";
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.MINUS)) {
            toAppend = "-";
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            toAppend = " ";
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.BACKSPACE)) {
            toAppend = "";
            if (mText.length() > 0) mText = mText.substring(0, mText.length() - 1);
            if (mDisplayText.length() > 0)
                mDisplayText = mDisplayText.substring(0, mDisplayText.length() - 1);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT) || Gdx.input.isKeyJustPressed(Input.Keys.SHIFT_RIGHT)) {
            toAppend = toAppend.toUpperCase();
        }
        mText += toAppend;
        if (toAppend.length() > 0) {
            mDisplayText = mDisplayText + "*";
        }
    }

    private void updateBlink(float delta) {
        this.mBlinkTime += delta;
        if (mBlink) {
            if (mBlinkTime > mBlinkOnTime) {
                mBlink = false;
                mBlinkTime = 0;
            }
        } else {
            if (mBlinkTime > mBlinkOffTime) {
                mBlink = true;
                mBlinkTime = 0;
            }
        }
    }

    private String getBlinkString() {
        if (mBlink && mFocus) return "|";
        return "";
    }

    public boolean hasFocus() {
        return mFocus;
    }

    public void setFocus(boolean focus) {
        this.mFocus = focus;
    }

    public String getText() {
        return mText;
    }

    public void setText(String text) {
        this.mText = text;
    }

    public String getHint() {
        return mHint;
    }

    public void setHint(String hint) {
        this.mHint = hint;
    }

    public int getAlignment() {
        return mAlignment;
    }

    public void setAlignment(int alignment) {
        this.mAlignment = alignment;
    }

    public BitmapFont getFont() {
        return mFont;
    }

    public void setFont(BitmapFont font) {
        this.mFont = font;
    }

    public float getBlinkOnTime() {
        return mBlinkOnTime;
    }

    public void setBlinkOnTime(float blinkOnTime) {
        this.mBlinkOnTime = blinkOnTime;
    }

    public float getBlinkOffTime() {
        return mBlinkOffTime;
    }

    public void setBlinkOffTime(float blinkOffTime) {
        this.mBlinkOffTime = blinkOffTime;
    }

    public void setTextColor(Color color) {
        this.mTextColor = color;
    }

    public void setHintColor(Color color) {
        this.mHintColor = color;
    }

    public void setInputType(int inputType) {
        this.mType = inputType;
    }
}
