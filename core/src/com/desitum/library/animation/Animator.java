package com.desitum.library.animation;

import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * Created by kody on 2/24/2015
 */
public abstract class Animator {

    protected Sprite mSprite;
    private float mDuration;
    private float mTimeInAnimation;
    private float mDelay;
    private float mCurrentDelay;
    private boolean mRunning;
    private boolean mRan;
    private OnAnimationFinishedListener mOnAnimationFinishedListener;

    public Animator(Sprite sprite, float duration, float delay) {
        this.mSprite = sprite;
        this.mDuration = duration;
        this.mDelay = delay;
        this.mTimeInAnimation = 0;
        this.mCurrentDelay = 0;
        this.mRunning = false;
        this.mRan = false;
        this.mOnAnimationFinishedListener = null;
    }

    public Animator(float duration, float delay) {
        this.mSprite = null;
        this.mDuration = duration;
        this.mDelay = delay;
        this.mTimeInAnimation = 0;
        this.mCurrentDelay = 0;
        this.mRunning = false;
        this.mRan = false;
        this.mOnAnimationFinishedListener = null;
    }

    public void update(float delta) {
        if (!mRunning) {
            return;
        }
        mCurrentDelay += delta;
        if (mCurrentDelay >= mDelay) {
            mTimeInAnimation += delta / mDuration;
        }
        if (mTimeInAnimation >= 1) {
            mTimeInAnimation = 1;
            mRunning = false;
            mRan = true;
            if (mOnAnimationFinishedListener != null) {
                mOnAnimationFinishedListener.onAnimationFinished(this);
            }
        }
        updateAnimation();
    }

    public void start(boolean isProtectedWhileRunning) {
        if (isProtectedWhileRunning && mRunning) {
            reset();
            mRunning = true;
        } else if (!isProtectedWhileRunning) {
            reset();
            mRunning = true;
        }
    }

    public void reset() {
        this.mRunning = false;
        this.mTimeInAnimation = 0;
        this.mCurrentDelay = 0;
    }

    public abstract Animator duplicate();

    // region getters and setters
    public boolean didFinish() {
        return mRan && !mRunning;
    }

    public abstract float getCurrentAmount();

    public boolean isRunning() {
        return mRunning;
    }

    public void setRunning(boolean running) {
        this.mRunning = running;
    }

    public void setOnFinishedListener(OnAnimationFinishedListener listener) {
        mOnAnimationFinishedListener = listener;
    }

    public Sprite getSprite() {
        return mSprite;
    }

    public void setSprite(Sprite control) {
        this.mSprite = control;
    }

    public float getTimeInAnimation() {
        return mTimeInAnimation;
    }

    public void setTimeInAnimation(float timeInAnimation) {
        this.mTimeInAnimation = timeInAnimation;
    }

    public float getDuration() {
        return mDuration;
    }

    public void setDuration(float duration) {
        this.mDuration = duration;
    }

    public float getDelay() {
        return mDelay;
    }

    public void setDelay(float delay) {
        this.mDelay = delay;
    }

    public float getCurrentDelay() {
        return mCurrentDelay;
    }

    public void setCurrentDelay(float currentDelay) {
        this.mCurrentDelay = currentDelay;
    }

    public boolean isRan() {
        return mRan;
    }

    public void setRan(boolean ran) {
        this.mRan = ran;
    }

    public OnAnimationFinishedListener getOnAnimationFinishedListener() {
        return mOnAnimationFinishedListener;
    }

    public void setOnAnimationFinishedListener(OnAnimationFinishedListener onAnimationFinishedListener) {
        this.mOnAnimationFinishedListener = onAnimationFinishedListener;
    }

    protected abstract void updateAnimation();
    //endregion
}
