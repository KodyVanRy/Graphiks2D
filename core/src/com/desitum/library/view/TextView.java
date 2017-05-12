package com.desitum.library.view;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.desitum.library.game.World;
import com.desitum.library.widgets.LinearLayout;


/**
 * Created by kody on 12/15/15.
 * can be used by kody and people in [kody}]
 */

public class TextView extends View {

    private String mText;
    private int mAlignment;
    private BitmapFont mFont;
    private GlyphLayout mGlyphLayout;
    private Color mTextColor;

    public TextView(World world) {
        this(world, null);
    }

    public TextView(World world, LayoutConstraints layoutConstraints) {
        this(world, layoutConstraints, null);
    }

    public TextView(World world, LayoutConstraints layoutConstraints, BitmapFont font) {
        super(world, layoutConstraints);
        this.mText = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        this.mFont = font;
        mGlyphLayout = new GlyphLayout(font, mText);
        setupFontSize();
        mText = "";
        mTextColor = Color.BLACK;
        this.mAlignment = LinearLayout.ALIGNMENT_LEFT;
    }

    @Override
    public void update(float delta) {
        super.update(delta);
    }

    @Override
    public boolean onTouchEvent(TouchEvent touchEvent) {
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

        super.draw(batch, camera);
        mFont.setColor(mTextColor);
        if (mAlignment == LinearLayout.ALIGNMENT_LEFT)
            mFont.draw(batch, getText(), getX() + getHeight() * 0.2f, getY() + getHeight() * 0.8f);
        if (mAlignment == LinearLayout.ALIGNMENT_CENTER)
            mFont.draw(batch, getText(), getX() + getWidth() / 2 - getTextWidth() / 2, getY() + getHeight() * 0.8f);
        if (mAlignment == LinearLayout.ALIGNMENT_RIGHT)
            mFont.draw(batch, getText(), getX() + getWidth() - getTextWidth() - getHeight() * 0.2f, getY() + getHeight() * 0.8f);
    }

    public float getTextWidth() {
        mGlyphLayout.setText(mFont, getText());
        return mGlyphLayout.width;
    }

    public String getText() {
        return mText;
    }

    public void setText(String text) {
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

    @Override
    public void setSize(float width, float height) {
        super.setSize(width, height);
        setupFontSize();
    }
}
