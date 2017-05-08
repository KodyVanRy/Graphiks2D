package com.desitum.library.view;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.utils.ScissorStack;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.desitum.library.game.World;
import com.desitum.library.logging.Log;
import com.desitum.library.widgets.LinearLayout;

/**
 * Created by kody on 12/15/15.
 * can be used by kody and people in [kody}]
 */
public class EditText extends View {

    public static final int TEXT = 0;
    public static final int PASSWORD = 1;

    public static final String INPUT_TEXT = "mText";
    public static final String INPUT_PASSWORD = "password";
    public static final float KEY_REPEAT = 0.05f;

    private String mText, mHint, mDisplayText;
    private int mAlignment, mType;
    private float mBlinkOnTime, mBlinkOffTime, mBlinkTime;
    private boolean mBlink;
    private BitmapFont mFont;
    private GlyphLayout mGlyphLayout;
    private Color mTextColor;
    private Color mHintColor;

    public EditText(World world) {
        this(world, null);
    }

    public EditText(World world, LayoutConstraints layoutConstraints) {
        this(world, layoutConstraints, null);
    }

    public EditText(World world, LayoutConstraints layoutConstraints, BitmapFont font) {
        super(world, layoutConstraints);
        this.mText = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        this.mFont = font;
        mGlyphLayout = new GlyphLayout(font, mText);
        setupFontSize();
        mDisplayText = "";
        mText = "";
        mHint = "";
        mTextColor = Color.BLACK;
        mHintColor = new Color(0.2f, 0.2f, 0.2f, 0.3f);
        this.mBlinkOffTime = 0.4f;
        this.mBlinkOnTime = 0.6f;
        this.mAlignment = LinearLayout.ALIGNMENT_LEFT;
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
        if (hasFocus()) {
            updateTextInput(delta);
            updateBlink(delta);
        } else {
            mBlink = false;
        }
    }

    @Override
    public boolean onTouchEvent(TouchEvent touchEvent) {
        if (isEnabled()) {
            switch (touchEvent.getAction()) {
                case DOWN:
                    getWorld().requestFocus(this);
                    break;
                case UP:
                    if (Gdx.app.getType() == Application.ApplicationType.Android) {
                        Input.TextInputListener inputListener = new Input.TextInputListener() {
                            @Override
                            public void input(String input) {
                                mText = input;
                            }

                            @Override
                            public void canceled() {

                            }
                        };
                        Gdx.input.getTextInput(inputListener, mHint, mText, "");
                    }
                    break;
            }
        }
        return super.onTouchEvent(touchEvent);
    }

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

    @Override
    public void draw(Batch batch, Viewport camera) {
        Rectangle scissor = new Rectangle();
        Rectangle clipBounds = new Rectangle(getX(), getY(), getWidth() + 1, getHeight() + 1);
        camera.calculateScissors(batch.getTransformMatrix(), clipBounds, scissor);
        ScissorStack.pushScissors(scissor);

        super.draw(batch, camera);
        mFont.setColor(mTextColor);
        if (mAlignment == LinearLayout.ALIGNMENT_LEFT)
            mFont.draw(batch, getDisplayText() + getBlinkString(), getX() + getHeight() * 0.2f, getY() + getHeight() * 0.8f);
        if (mAlignment == LinearLayout.ALIGNMENT_CENTER)
            mFont.draw(batch, getDisplayText() + getBlinkString(), getX() + getWidth() / 2 - getTextWidth() / 2, getY() + getHeight() * 0.8f);
        if (mAlignment == LinearLayout.ALIGNMENT_RIGHT)
            mFont.draw(batch, getDisplayText() + getBlinkString(), getX() + getWidth() - getTextWidth() - getHeight() * 0.2f, getY() + getHeight() * 0.8f);
        if (mText.length() == 0 && !hasFocus()) {
            mFont.setColor(mHintColor);
            if (mAlignment == LinearLayout.ALIGNMENT_LEFT)
                mFont.draw(batch, mHint, getX() + getHeight() * 0.2f, getY() + getHeight() * 0.8f);
            if (mAlignment == LinearLayout.ALIGNMENT_CENTER)
                mFont.draw(batch, mHint, getX() + getWidth() / 2 - getHintWidth() / 2, getY() + getHeight() * 0.8f);
            if (mAlignment == LinearLayout.ALIGNMENT_RIGHT)
                mFont.draw(batch, mHint, getX() + getWidth() - getHintWidth() - getHeight() * 0.2f, getY() + getHeight() * 0.8f);
        }
        batch.flush();

        ScissorStack.popScissors();
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

    public void updateTextInput(float delta) {
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
        } else if (Gdx.input.isKeyPressed(Input.Keys.BACKSPACE)) {
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
        if (mBlink) return "|";
        return "";
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

    @Override
    public void setSize(float width, float height) {
        super.setSize(width, height);
        setupFontSize();
    }
}
