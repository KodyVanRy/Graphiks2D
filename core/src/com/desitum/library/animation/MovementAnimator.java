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

    private float startPos;
    private float endPos;
    private float travelDistance;
    private float currentPosition;

    private boolean controllingX;
    private boolean controllingY;
    private Interpolator interpolator;

    public MovementAnimator(float startPos, float endPos, float duration, int interpolator) {
        super(duration, 0);
        this.startPos = startPos;
        this.endPos = endPos;
        this.travelDistance = endPos - startPos;
        this.currentPosition = startPos;

        setupInterpolator(interpolator);
    }

    public MovementAnimator(Sprite sprite, float startPos, float endPos, float duration, float delay, int interpolator, boolean controlX, boolean controlY) {
        super(sprite, duration, delay);
        this.startPos = startPos;
        this.endPos = endPos;
        this.travelDistance = endPos - startPos;
        this.currentPosition = startPos;

        this.controllingX = controlX;
        this.controllingY = controlY;

        setupInterpolator(interpolator);
    }

    public void setSprite(Sprite control, boolean controlX, boolean controlY) {
        this.sprite = control;

        this.controllingX = controlX;
        this.controllingY = controlY;
    }

    public float getCurrentPos() {
        return currentPosition;
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

    @Override
    public void reset() {
        super.reset();
        currentPosition = startPos;
    }

    @Override
    public float getCurrentAmount() {
        return getCurrentPos();
    }

    @Override
    protected void updateAnimation() {
        currentPosition = interpolator.getInterpolation(getTimeInAnimation()) * travelDistance + startPos;

        if (sprite != null) {
            if (this.controllingX) {
                this.sprite.setX(getCurrentPos());
            }
            if (this.controllingY) {
                this.sprite.setY(getCurrentPos());
            }
        }
    }

    @Override
    public Animator duplicate() {
        return new MovementAnimator(sprite, this.startPos, this.endPos, getDuration(), getDelay(), Interpolation.getInterpolatorNum(this.interpolator), this.controllingX, this.controllingY);
    }

    public float getStartPos() {
        return startPos;
    }

    public void setStartPos(float startPos) {
        this.startPos = startPos;
    }

    public float getEndPos() {
        return endPos;
    }

    public void setEndPos(float endPos) {
        this.endPos = endPos;
    }

    public boolean isControllingX() {
        return controllingX;
    }

    public void setControllingX(boolean controllingX) {
        this.controllingX = controllingX;
    }

    public boolean setControlX(boolean control) {
        return controllingX = control;
    }

    public boolean setControlY(boolean control) {
        return controllingY = control;
    }

    public boolean isControllingY() {
        return controllingY;
    }

    public void setControllingY(boolean controllingY) {
        this.controllingY = controllingY;
    }

    public float getDistance() {
        return travelDistance;
    }
}
