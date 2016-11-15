package com.desitum.library.animation;

import com.badlogic.gdx.graphics.g2d.PolygonSprite;
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

    private float amountToRotate;
    private float startRotation;
    private float endRotation;
    private float currentRotation;

    private Interpolator interpolator;

    public RotateAnimator(float duration, float delay, float amountToRotate, int interpolator) {
        super(duration, delay);
        this.amountToRotate = amountToRotate;
        this.startRotation = 0;
        this.endRotation = amountToRotate;
        setupInterpolator(interpolator);
    }

    public RotateAnimator(Sprite sprite, float duration, float delay, float startRotation, float endRotation, int interpolator) {
        super(sprite, duration, delay);
        this.startRotation = startRotation;
        this.endRotation = endRotation;
        this.amountToRotate = endRotation - startRotation;
        setupInterpolator(interpolator);
    }

    public RotateAnimator(PolygonSprite sprite, float duration, float delay, float startRotation, float endRotation, int interpolator) {
        super(sprite, duration, delay);
        this.startRotation = startRotation;
        this.endRotation = endRotation;
        this.amountToRotate = endRotation - startRotation;
        setupInterpolator(interpolator);
    }

    @Override
    public float getCurrentAmount() {
        return 0;
    }

    @Override
    protected void updateAnimation() {
        currentRotation = startRotation + amountToRotate * interpolator.getInterpolation(getTimeInAnimation());

        if (sprite != null) {
            sprite.setRotation(currentRotation);
        } else if (pSprite != null) {
            pSprite.setRotation(currentRotation);
        }
    }

    @Override
    public Animator duplicate() {
        return new RotateAnimator(sprite, getDuration(), getDelay(), startRotation, endRotation, Interpolation.getInterpolatorNum(interpolator));
    }

    private void setupInterpolator(int interpolator) {
        if (interpolator == Interpolation.ACCELERATE_INTERPOLATOR) {
            this.interpolator = AccelerateInterpolator.$();
        } else if (interpolator == Interpolation.DECELERATE_INTERPOLATOR) {
            this.interpolator = DecelerateInterpolator.$();
        } else if (interpolator == Interpolation.ANTICIPATE_INTERPOLATOR) {
            this.interpolator = AnticipateInterpolator.$();
        } else if (interpolator == Interpolation.OVERSHOOT_INTERPOLATOR) {
            this.interpolator = OvershootInterpolator.$();
        } else if (interpolator == Interpolation.ACCELERATE_DECELERATE_INTERPOLATOR) {
            this.interpolator = AccelerateDecelerateInterpolator.$();
        } else if (interpolator == Interpolation.BOUNCE_INTERPOLATOR) {
            this.interpolator = BounceInterpolator.$();
        } else if (interpolator == Interpolation.LINEAR_INTERPOLATOR) {
            this.interpolator = LinearInterpolator.$();
        }
    }
}
