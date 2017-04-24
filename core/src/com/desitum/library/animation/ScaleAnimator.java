package com.desitum.library.animation;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.desitum.library.interpolation.AccelerateDecelerateInterpolator;
import com.desitum.library.interpolation.AccelerateInterpolator;
import com.desitum.library.interpolation.AnticipateInterpolator;
import com.desitum.library.interpolation.BounceInterpolator;
import com.desitum.library.interpolation.DecelerateInterpolator;
import com.desitum.library.interpolation.Interpolation;
import com.desitum.library.interpolation.Interpolator;
import com.desitum.library.interpolation.LinearInterpolator;
import com.desitum.library.interpolation.OvershootInterpolator;

/**
 * Created by dvan6234 on 2/24/2015.
 */
public class ScaleAnimator extends Animator {

    private float mStartScale;
    private float mEndScale;

    private float mScaleSize;

    private boolean mGrowing;

    private Interpolator mInterpolator;

    private boolean mControllingX;
    private boolean mControllingY;

    public ScaleAnimator(float duration, float startScale, float endScale, int interpolator) {
        super(duration, 0);
        this.mStartScale = startScale;
        this.mEndScale = endScale;

        mScaleSize = startScale;

        if (startScale > endScale) {
            mGrowing = false;
        } else {
            mGrowing = true;
        }

        setupInterpolator(interpolator);
    }

    public ScaleAnimator(float duration, float delay, float startScale, float endScale, int interpolator) {
        super(duration, delay);
        this.mStartScale = startScale;
        this.mEndScale = endScale;

        if (startScale > endScale) {
            mGrowing = false;
        } else {
            mGrowing = true;
        }

        setupInterpolator(interpolator);
    }

    public ScaleAnimator(Sprite sprite, float duration, float delay, float startScale, float endScale, int interpolator, boolean controlWidth, boolean controlHeight) {
        super(sprite, duration, delay);
        this.mStartScale = startScale;
        this.mEndScale = endScale;

        this.mControllingX = controlWidth;
        this.mControllingY = controlHeight;

        if (startScale > endScale) {
            mGrowing = false;
        } else {
            mGrowing = true;
        }

        setupInterpolator(interpolator);
    }

    protected void updateAnimation() {
        if (mGrowing) {
            mScaleSize = mStartScale + (mEndScale - mStartScale) * mInterpolator.getInterpolation(getTimeInAnimation());
        } else {
            mScaleSize = mStartScale - (mStartScale - mEndScale) * mInterpolator.getInterpolation(getTimeInAnimation());
        }

        if (mSprite != null) {
            if (mControllingY) {
                this.mSprite.setScale(this.mSprite.getScaleX(), this.getScaleSize());
            }
            if (mControllingX) {
                this.mSprite.setScale(this.getScaleSize(), this.mSprite.getScaleY());
            }
        }
    }

    public void setSprite(Sprite control, boolean controlX, boolean controlY) {
        super.setSprite(control);

        this.mControllingX = controlX;
        this.mControllingY = controlY;
    }

    public boolean isControllingY() {
        return mControllingY;
    }

    public boolean isControllingX() {
        return mControllingX;
    }

    public boolean setControlX(boolean control) {
        return mControllingX = control;
    }

    public boolean setControlY(boolean control) {
        return mControllingY = control;
    }

    @Override
    public float getCurrentAmount() {
        return getScaleSize();
    }

    public float getScaleSize() {
        return mScaleSize;
    }

    private void setupInterpolator(int interpolator) {
        if (interpolator == Interpolation.ACCELERATE_INTERPOLATOR) {
            this.mInterpolator = AccelerateInterpolator.$();
        } else if (interpolator == Interpolation.DECELERATE_INTERPOLATOR) {
            this.mInterpolator = DecelerateInterpolator.$();
        } else if (interpolator == Interpolation.ANTICIPATE_INTERPOLATOR) {
            this.mInterpolator = AnticipateInterpolator.$();
        } else if (interpolator == Interpolation.OVERSHOOT_INTERPOLATOR) {
            this.mInterpolator = OvershootInterpolator.$();
        } else if (interpolator == Interpolation.ACCELERATE_DECELERATE_INTERPOLATOR) {
            this.mInterpolator = AccelerateDecelerateInterpolator.$();
        } else if (interpolator == Interpolation.BOUNCE_INTERPOLATOR) {
            this.mInterpolator = BounceInterpolator.$();
        } else if (interpolator == Interpolation.LINEAR_INTERPOLATOR) {
            this.mInterpolator = LinearInterpolator.$();
        }
    }

    @Override
    public Animator duplicate() {
        return new ScaleAnimator(mSprite, getDuration(), getDelay(), mStartScale, mEndScale, Interpolation.getInterpolatorNum(mInterpolator), mControllingX, mControllingY);
    }
}
