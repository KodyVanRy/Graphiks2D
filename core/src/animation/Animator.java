package animation;

import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * Created by dvan6234 on 2/24/2015.
 */
public abstract class Animator {

    protected Sprite sprite;
    private float duration;
    private float timeInAnimation;
    private float delay;
    private float currentDelay;
    private boolean running;
    private boolean ran;
    private OnAnimationFinishedListener onAnimationFinishedListener;

    public Animator(Sprite sprite, float duration, float delay) {
        this.sprite = sprite;
        this.duration = duration;
        this.delay = delay;
        this.timeInAnimation = 0;
        this.currentDelay = 0;
        this.running = false;
        this.ran = false;
        this.onAnimationFinishedListener = null;
    }

    public Animator(float duration, float delay) {
        this.sprite = null;
        this.duration = duration;
        this.delay = delay;
        this.timeInAnimation = 0;
        this.currentDelay = 0;
        this.running = false;
        this.ran = false;
        this.onAnimationFinishedListener = null;
    }

    public void update(float delta) {
        if (running) {
            if (currentDelay >= delay) {
                timeInAnimation += delta / duration;
                if (timeInAnimation >= 1) {
                    timeInAnimation = 1;
                    running = false;
                    if (onAnimationFinishedListener != null) {
                        onAnimationFinishedListener.onAnimationFinished(this);
                    }
                }
            } else {
                currentDelay += delta;
            }
            updateAnimation();
        }
    }

    public void start(boolean isProtectedWhileRunning) {
        if (isProtectedWhileRunning) {
            if (!running) {
                reset();
                running = true;
            }
        } else {
            reset();
            running = true;
        }
    }

    public void reset() {
        this.timeInAnimation = 0;
        this.currentDelay = 0;
    }

    public abstract Animator duplicate();

    // region getters and setters
    public boolean didFinish() {
        if (ran && !running) {
            return true;
        }
        return false;
    }

    public abstract float getCurrentAmount();

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public void setOnFinishedListener(OnAnimationFinishedListener listener) {
        onAnimationFinishedListener = listener;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public void setSprite(Sprite control) {
        this.sprite = control;
    }

    public float getTimeInAnimation() {
        return timeInAnimation;
    }

    public void setTimeInAnimation(float timeInAnimation) {
        this.timeInAnimation = timeInAnimation;
    }

    public float getDuration() {
        return duration;
    }

    public void setDuration(float duration) {
        this.duration = duration;
    }

    public float getDelay() {
        return delay;
    }

    public void setDelay(float delay) {
        this.delay = delay;
    }

    public float getCurrentDelay() {
        return currentDelay;
    }

    public void setCurrentDelay(float currentDelay) {
        this.currentDelay = currentDelay;
    }

    public boolean isRan() {
        return ran;
    }

    public void setRan(boolean ran) {
        this.ran = ran;
    }

    public OnAnimationFinishedListener getOnAnimationFinishedListener() {
        return onAnimationFinishedListener;
    }

    public void setOnAnimationFinishedListener(OnAnimationFinishedListener onAnimationFinishedListener) {
        this.onAnimationFinishedListener = onAnimationFinishedListener;
    }

    protected abstract void updateAnimation();
    //endregion
}
