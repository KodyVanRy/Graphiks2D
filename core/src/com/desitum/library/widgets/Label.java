package com.desitum.library.widgets;

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
public class Label extends Widget {

    private String mText;
    private int mAlignment;
    private float mUnderlineHeight;
    private BitmapFont mFont;
    private Texture mUnderline;
    private GlyphLayout mGlyphLayout;
    private Color mTextColor;

    boolean mTextNeedsResize = false;

    public Label(Texture background, String name, float width, float height, float x, float y, Layout parent, BitmapFont font) {
        super(background, name, width, height, x, y, parent);
        this.mText = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        this.mFont = font;
        mGlyphLayout = new GlyphLayout(mFont, mText);
        setupFontSize();
        mText = "";
        mTextColor = Color.WHITE;
        this.mAlignment = LinearLayout.ALIGNMENT_LEFT;
        this.mUnderlineHeight = height * 0.05f;
    }

    @Override
    public void update(float delta) {
        super.update(delta);
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
        invalidateText();
        getTextWidth();
    }

    private void invalidateText() {
        mTextNeedsResize = true;
    }

    public void setUnderline(Texture texture) {
        this.mUnderline = texture;
    }

    public void setUnderlineHeight(float underlineHeight) {
        this.mUnderlineHeight = underlineHeight;
    }

    public void setUnderlineColor(Color color) {
        float scale = 50 / this.mUnderlineHeight;
        this.mUnderline = Drawing.getTextureRoundedRectangle((int) (this.getWidth() * scale), (int) (mUnderlineHeight * scale), (int) (mUnderlineHeight * scale), color);
    }

    @Override
    public void draw(Batch batch, Viewport camera) {
        super.draw(batch, camera);
        if (mUnderline != null) batch.draw(mUnderline, getX(), getY(), getWidth(), mUnderlineHeight);
        mFont.setColor(mTextColor);
        if (mAlignment == LinearLayout.ALIGNMENT_LEFT)
            mFont.draw(batch, getText(), getX() + getHeight() * 0.2f, getY() + getHeight() * 0.8f);
        if (mAlignment == LinearLayout.ALIGNMENT_CENTER)
            mFont.draw(batch, getText(), getX() + getWidth() / 2 - getTextWidth() / 2, getY() + getHeight() * 0.8f);
        if (mAlignment == LinearLayout.ALIGNMENT_RIGHT)
            mFont.draw(batch, getText(), getX() + getWidth() - getTextWidth() - getHeight() * 0.2f, getY() + getHeight() * 0.8f);
    }

    public float getTextWidth() {
        if (mTextNeedsResize) {
            mGlyphLayout.setText(mFont, getText());
            mTextNeedsResize = false;
        }
        return mGlyphLayout.width;
    }

    public String getText() {
        return mText;
    }

    public void setText(String text) {
        mTextNeedsResize = true;
        this.mText = text;
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

    public void setTextColor(Color color) {
        this.mTextColor = color;
    }
}
