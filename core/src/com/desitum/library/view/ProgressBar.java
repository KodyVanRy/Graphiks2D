package com.desitum.library.view;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.desitum.library.drawing.Drawable;
import com.desitum.library.game.World;

/**
 * Created by kodyvanry on 5/1/17.
 */

public class ProgressBar extends View {

    protected static final float DEFAULT_PROGRESS_BAR_HEIGHT = 50;

    protected float mProgress;
    protected float mProgressBarHeight;
    protected Drawable mProgressDrawable;
    protected Drawable mProgressBackgroundDrawable;

    public ProgressBar(World world) {
        this(world, null);
    }

    public ProgressBar(World world, LayoutConstraints layoutConstraints) {
        super(world, layoutConstraints);
        this.mProgressBarHeight = DEFAULT_PROGRESS_BAR_HEIGHT;
    }

    @Override
    public void onDraw(Batch batch, Viewport viewport) {
        super.onDraw(batch, viewport);
        if (mProgressBackgroundDrawable != null) {
            mProgressBackgroundDrawable.draw(
                    batch,
                    getX(),
                    getY() + getHeight() / 2 - mProgressBarHeight / 2,
                    getWidth(),
                    mProgressBarHeight
            );
        }
        if (mProgressDrawable != null) {
            mProgressDrawable.draw(
                    batch,
                    getX(),
                    getY() + getHeight() / 2 - mProgressBarHeight / 2,
                    getWidth() * mProgress,
                    mProgressBarHeight
            );
        }
    }

    public void setProgress(float progress) {
        this.mProgress = Math.max(Math.min(progress, 1), 0);
    }

    public float getProgress() {
        return mProgress;
    }

    public void setProgressBackgroundDrawable(Drawable progressBackgroundDrawable) {
        this.mProgressBackgroundDrawable = progressBackgroundDrawable;
    }

    public Drawable getProgressBackgroundDrawable() {
        return this.mProgressBackgroundDrawable;
    }

    public void setProgressDrawable(Drawable progressDrawable) {
        this.mProgressDrawable = progressDrawable;
    }

    public void setProgressBarHeight(float progressBarHeight) {
        this.mProgressBarHeight = progressBarHeight;
    }
}
