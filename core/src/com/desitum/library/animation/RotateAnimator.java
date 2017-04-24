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
 * Created by kody on 12/21/15.
 * can be used by kody and people in [kody}]
 */
public class RotateAnimator extends Animator {

    private float mAmountToRotate;
    private float mStartRotation;
    private float mEndRotation;
    private float mCurrentRotation;

    private Interpolator mInterpolator;

    public RotateAnimator(float duration, float delay, float amountToRotate, int interpolator) {
        super(duration, delay);
        this.mAmountToRotate = amountToRotate;
        this.mStartRotation = 0;
        this.mEndRotation = amountToRotate;
        setupInterpolator(interpolator);
    }

    public RotateAnimator(Sprite sprite, float duration, float delay, float startRotation, float endRotation, int interpolator) {
        super(sprite, duration, delay);
        this.mStartRotation = startRotation;
        this.mEndRotation = endRotation;
        this.mAmountToRotate = endRotation - startRotation;
        setupInterpolator(interpolator);
    }

    @Override
    public float getCurrentAmount() {
        return 0;
    }

    @Override
    protected void updateAnimation() {
        mCurrentRotation = mStartRotation + mAmountToRotate * mInterpolator.getInterpolation(getTimeInAnimation());

        if (mSprite != null) {
            mSprite.setRotation(mCurrentRotation);
        }
    }

    @Override
    public Animator duplicate() {
        return new RotateAnimator(mSprite, getDuration(), getDelay(), mStartRotation, mEndRotation, Interpolation.getInterpolatorNum(mInterpolator));
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
}
