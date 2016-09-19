package com.desitum.library.widgets;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.desitum.library.drawing.Drawing;
import com.desitum.library.listener.OnValueChangeListener;

/**
 * Created by kody on 12/17/15.
 * can be used by kody and people in [kody}]
 */
public class Slider extends Widget {

    private float value, sliderWidth, sliderHeight, padding, barImageHeight;
    private Texture sliderImage, barImage;
    private OnValueChangeListener onValueChangeListener;

    public Slider(Texture text, String name, float width, float height, float x, float y, Layout parent) {
        super(text, name, width, height, x, y, parent);

        sliderImage = Drawing.getDiamondFilled(100, 100, Color.WHITE);
        sliderWidth = height / 2;
        sliderHeight = height / 2;
        barImage = Drawing.getHollowRectangle(100, 3, 3, Color.WHITE);
        padding = width / 100;
        barImageHeight = height / 10;
    }

    public Slider(Texture text, Texture sliderText, Texture barText, String name, float width, float height, float x, float y, Layout parent) {
        super(text, name, width, height, x, y, parent);

        this.sliderImage = sliderText;
        sliderWidth = height / 2;
        sliderHeight = height / 2;
        this.barImage = barText;
        padding = width / 100;
        barImageHeight = height / 10;
    }

    @Override
    public void updateTouchInput(Vector3 mousePos, boolean clickDown) {
        if (isVisible()) {
            if (isPointInWidget(mousePos)) {
                if (clickDown) {
                    value = (mousePos.x - getX()) / getWidth();
                } else if (isClickDown()) {
                    if (onValueChangeListener != null) onValueChangeListener.onChange(this, value);
                }
            }
            super.updateTouchInput(mousePos, clickDown);
        }
    }

    @Override
    public void draw(Batch batch, Viewport camera) {
        super.draw(batch, camera);

        batch.draw(barImage, getX() + padding, getY() + getHeight() / 2 - barImageHeight / 2, getWidth() - padding * 2, barImageHeight);
        batch.draw(sliderImage, getX() + padding + value * (getWidth() - padding * 2) - sliderWidth / 2, getY() + getHeight() / 2 - sliderHeight / 2, sliderWidth, sliderHeight);
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public float getSliderWidth() {
        return sliderWidth;
    }

    public void setSliderWidth(float sliderWidth) {
        this.sliderWidth = sliderWidth;
    }

    public float getSliderHeight() {
        return sliderHeight;
    }

    public void setSliderHeight(float sliderHeight) {
        this.sliderHeight = sliderHeight;
    }

    public Texture getSliderImage() {
        return sliderImage;
    }

    public void setSliderImage(Texture sliderImage) {
        this.sliderImage = sliderImage;
    }

    public void setSliderImage(Texture sliderImage, float width, float height) {
        this.sliderImage = sliderImage;
        setSliderWidth(width);
        setSliderHeight(height);
    }

    public void setBarImage(Texture barImage) {
        this.barImage = barImage;
    }

    public OnValueChangeListener getOnValueChangeListener() {
        return onValueChangeListener;
    }

    public void setOnValueChangeListener(OnValueChangeListener onValueChangeListener) {
        this.onValueChangeListener = onValueChangeListener;
    }

    public void setPadding(float padding) {
        this.padding = padding;
    }

    public void setBarImageHeight(float barImageHeight) {
        this.barImageHeight = barImageHeight;
    }
}
