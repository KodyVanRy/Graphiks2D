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
 * Created by kody on 2/24/15.
 * can be used by kody and people in []
 */
public class MovementAnimator extends Animator {

    private float mStartPos;
    private float mEndPos;
    private float mTravelDistance;
    private float mCurrentPosition;

    private boolean mControllingX;
    private boolean mControllingY;
    private Interpolator mInterpolator;

    public MovementAnimator(float startPos, float endPos, float duration, int interpolator) {
        super(duration, 0);
        this.mStartPos = startPos;
        this.mEndPos = endPos;
        this.mTravelDistance = endPos - startPos;
        this.mCurrentPosition = startPos;

        setupInterpolator(interpolator);
    }

    public MovementAnimator(Sprite sprite, float startPos, float endPos, float duration, float delay, int interpolator, boolean controlX, boolean controlY) {
        super(sprite, duration, delay);
        this.mStartPos = startPos;
        this.mEndPos = endPos;
        this.mTravelDistance = endPos - startPos;
        this.mCurrentPosition = startPos;

        this.mControllingX = controlX;
        this.mControllingY = controlY;

        setupInterpolator(interpolator);
    }

    public void setSprite(Sprite control, boolean controlX, boolean controlY) {
        this.mSprite = control;

        this.mControllingX = controlX;
        this.mControllingY = controlY;
    }

    public float getCurrentPos() {
        return mCurrentPosition;
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
    public void reset() {
        super.reset();
        mCurrentPosition = mStartPos;
    }

    @Override
    public float getCurrentAmount() {
        return getCurrentPos();
    }

    @Override
    protected void updateAnimation() {
        mCurrentPosition = mInterpolator.getInterpolation(getTimeInAnimation()) * mTravelDistance + mStartPos;

        if (mSprite != null) {
            if (this.mControllingX) {
                this.mSprite.setX(getCurrentPos());
            }
            if (this.mControllingY) {
                this.mSprite.setY(getCurrentPos());
            }
        }
    }

    @Override
    public Animator duplicate() {
        return new MovementAnimator(mSprite, this.mStartPos, this.mEndPos, getDuration(), getDelay(), Interpolation.getInterpolatorNum(this.mInterpolator), this.mControllingX, this.mControllingY);
    }

    public float getStartPos() {
        return mStartPos;
    }

    public void setStartPos(float startPos) {
        this.mStartPos = startPos;
    }

    public float getEndPos() {
        return mEndPos;
    }

    public void setEndPos(float endPos) {
        this.mEndPos = endPos;
    }

    public boolean isControllingX() {
        return mControllingX;
    }

    public void setControllingX(boolean controllingX) {
        this.mControllingX = controllingX;
    }

    public boolean setControlX(boolean control) {
        return mControllingX = control;
    }

    public boolean setControlY(boolean control) {
        return mControllingY = control;
    }

    public boolean isControllingY() {
        return mControllingY;
    }

    public void setControllingY(boolean controllingY) {
        this.mControllingY = controllingY;
    }

    public float getDistance() {
        return mTravelDistance;
    }
}
