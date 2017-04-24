package com.desitum.library.animation;

import com.badlogic.gdx.graphics.Color;

/**
 * Created by dvan6234 on 2/17/2015.
 */
public class ColorEffects extends Animator {
    private float mStartRed;
    private float mStartGreen;
    private float mStartBlue;
    private float mStartAlpha;

    private float mSlopeRed;
    private float mSlopeGreen;
    private float mSlopeBlue;
    private float mSlopeAlpha;

    private float mEndRed;
    private float mEndGreen;
    private float mEndBlue;
    private float mEndAlpha;

    private float mCurrentRed;
    private float mCurrentGreen;
    private float mCurrentBlue;
    private float mCurrentAlpha;

    public ColorEffects(Color startColor, Color endColor, float duration) {
        super(duration, 0);

        setupColors(startColor, endColor, duration);
    }

    public ColorEffects(Color startColor, Color endColor, float duration, float delay) {
        super(duration, delay);
        setupColors(startColor, endColor, duration);
    }

    static public boolean colorsMatch(Color color1, Color color2, float marginOfError) {
        if (color1.equals(color2)) return true;

        float error = 0;

        error += Math.abs(color1.r - color2.r);
        error += Math.abs(color1.g - color2.g);
        error += Math.abs(color1.b - color2.b);
        error += Math.abs(color1.a - color2.a);

        if (error < marginOfError) {
            return true;
        }
        return false;
    }

    private void setupColors(Color startColor, Color endColor, float duration) {
        if (duration <= 0) {
            mCurrentRed = endColor.r;
            mEndRed = (int) endColor.r;
            mSlopeRed = 0;
            mCurrentGreen = endColor.r;
            mEndGreen = (int) endColor.g;
            mSlopeGreen = 0;
            mCurrentBlue = endColor.b;
            mEndBlue = (int) endColor.b;
            mSlopeBlue = 0;
            mCurrentAlpha = endColor.a;
            mEndAlpha = (int) endColor.a;
            mSlopeAlpha = 0;
            return;
        }

        mStartRed = startColor.r;
        mStartGreen = startColor.g;
        mStartBlue = startColor.b;
        mStartAlpha = startColor.a;

        mSlopeRed = (endColor.r - startColor.r);
        mSlopeGreen = (endColor.g - startColor.g);
        mSlopeBlue = (endColor.b - startColor.b);
        mSlopeAlpha = (endColor.a - startColor.a);

        mCurrentRed = startColor.r;
        mCurrentGreen = startColor.g;
        mCurrentBlue = startColor.b;
        mCurrentAlpha = startColor.a;

        mEndRed = endColor.r;
        mEndGreen = endColor.g;
        mEndBlue = endColor.b;
        mEndAlpha = endColor.a;
    }

    public Color getCurrentColor() {
        return new Color(mCurrentRed, mCurrentGreen, mCurrentBlue, mCurrentAlpha);
    }

    public float getCurrentRed() {
        return mCurrentRed;
    }

    public float getCurrentGreen() {
        return mCurrentGreen;
    }

    public float getmCurrentBlue() {
        return mCurrentBlue;
    }

    public float getCurrentAlpha() {
        return mCurrentAlpha;
    }

    @Override
    public Animator duplicate() {
        return new ColorEffects(new Color(mStartRed, mStartGreen, mStartBlue, mStartAlpha), new Color(mEndRed, mEndGreen, mEndBlue, mEndAlpha), getDuration());
    }

    @Override
    public float getCurrentAmount() {
        return 0;
    }

    @Override
    protected void updateAnimation() {
        mCurrentRed = mSlopeRed * getTimeInAnimation() + mStartRed;
        mCurrentGreen = mSlopeGreen * getTimeInAnimation() + mStartGreen;
        mCurrentBlue = mSlopeBlue * getTimeInAnimation() + mStartBlue;
        mCurrentAlpha = mSlopeAlpha * getTimeInAnimation() + mStartAlpha;

        if (mSprite != null) {
            mSprite.setColor(getCurrentColor());
        }
    }
}

