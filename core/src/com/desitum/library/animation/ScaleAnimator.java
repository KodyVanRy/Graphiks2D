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
 * Created by dvan6234 on 2/24/2015.
 */
public class ScaleAnimator extends Animator {

    private float startScale;
    private float endScale;

    private float scaleSize;

    private boolean growing;

    private Interpolator interpolator;

    private boolean controllingX;
    private boolean controllingY;

    private OnAnimationFinishedListener finishedListener;

    public ScaleAnimator(float duration, float startScale, float endScale, int interpolator) {
        super(duration, 0);
        this.startScale = startScale;
        this.endScale = endScale;

        scaleSize = startScale;

        if (startScale > endScale) {
            growing = false;
        } else {
            growing = true;
        }

        setupInterpolator(interpolator);
    }

    public ScaleAnimator(float duration, float delay, float startScale, float endScale, int interpolator) {
        super(duration, delay);
        this.startScale = startScale;
        this.endScale = endScale;

        if (startScale > endScale) {
            growing = false;
        } else {
            growing = true;
        }

        setupInterpolator(interpolator);
    }

    public ScaleAnimator(Sprite sprite, float duration, float delay, float startScale, float endScale, int interpolator, boolean controlWidth, boolean controlHeight) {
        super(sprite, duration, delay);
        this.startScale = startScale;
        this.endScale = endScale;

        this.controllingX = controlWidth;
        this.controllingY = controlHeight;

        if (startScale > endScale) {
            growing = false;
        } else {
            growing = true;
        }

        setupInterpolator(interpolator);
    }

    public ScaleAnimator(PolygonSprite sprite, float duration, float delay, float startScale, float endScale, int interpolator, boolean controlWidth, boolean controlHeight) {
        super(sprite, duration, delay);
        this.startScale = startScale;
        this.endScale = endScale;

        this.controllingX = controlWidth;
        this.controllingY = controlHeight;

        if (startScale > endScale) {
            growing = false;
        } else {
            growing = true;
        }

        setupInterpolator(interpolator);
    }

    protected void updateAnimation() {
        if (growing) {
            scaleSize = startScale + (endScale - startScale) * interpolator.getInterpolation(getTimeInAnimation());
        } else {
            scaleSize = startScale - (startScale - endScale) * interpolator.getInterpolation(getTimeInAnimation());
        }

        if (sprite != null) {
            if (controllingY) {
                this.sprite.setScale(this.sprite.getScaleX(), this.getScaleSize());
            }
            if (controllingX) {
                this.sprite.setScale(this.getScaleSize(), this.sprite.getScaleY());
            }
        } else if (pSprite != null) {
            if (controllingY) {
                this.pSprite.setScale(this.pSprite.getScaleX(), this.getScaleSize());
            }
            if (controllingX) {
                this.pSprite.setScale(this.getScaleSize(), this.pSprite.getScaleY());
            }
        }
    }

    public void setSprite(Sprite control, boolean controlX, boolean controlY) {
        super.setSprite(control);

        this.controllingX = controlX;
        this.controllingY = controlY;
    }

    public boolean isControllingY() {
        return controllingY;
    }

    public boolean isControllingX() {
        return controllingX;
    }

    public boolean setControlX(boolean control) {
        return controllingX = control;
    }

    public boolean setControlY(boolean control) {
        return controllingY = control;
    }

    @Override
    public float getCurrentAmount() {
        return getScaleSize();
    }

    public float getScaleSize() {
        return scaleSize;
    }

    @Override
    public void setOnFinishedListener(OnAnimationFinishedListener listener) {
        this.finishedListener = listener;
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
    public Animator duplicate() {
        return new ScaleAnimator(sprite, getDuration(), getDelay(), startScale, endScale, Interpolation.getInterpolatorNum(interpolator), controllingX, controllingY);
    }
}
