package com.desitum.library.view;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.desitum.library.drawing.Drawable;
import com.desitum.library.game.World;

/**
 * Created by kodyvanry on 5/1/17.
 */

public class SeekBar extends ProgressBar {

    private static final float SEEKER_SIZE = DEFAULT_PROGRESS_BAR_HEIGHT * 2;

    private Drawable mSeekerDrawable;
    private OnSeekBarChangeListener mOnSeekBarChangeListener;

    public SeekBar(World world) {
        super(world);
    }

    public SeekBar(World world, LayoutConstraints layoutConstraints) {
        super(world, layoutConstraints);
    }

    @Override
    public boolean onTouchEvent(TouchEvent touchEvent) {
        switch (touchEvent.getAction()) {
            case DOWN:
                setProgress((touchEvent.getX() - getX()) / getWidth());
                if (mOnSeekBarChangeListener != null) {
                    mOnSeekBarChangeListener.onStartTrackingTouch(this);
                    mOnSeekBarChangeListener.onSeekChanged(this, getProgress());
                }
                break;
            case MOVE:
                setProgress((touchEvent.getX() - getX()) / getWidth());
                if (mOnSeekBarChangeListener != null)
                    mOnSeekBarChangeListener.onSeekChanged(this, getProgress());
                break;
            case UP:
                if (mOnSeekBarChangeListener != null)
                    mOnSeekBarChangeListener.onStopTrackingTouch(this);
        }
        return super.onTouchEvent(touchEvent);
    }

    @Override
    public void onDraw(Batch batch, Viewport viewport) {
        super.onDraw(batch, viewport);
        if (mSeekerDrawable != null)
            mSeekerDrawable.draw(
                    batch,
                    getX() + getWidth() * getProgress() - SEEKER_SIZE / 2,
                    getY() + getHeight() / 2 - SEEKER_SIZE / 2,
                    SEEKER_SIZE,
                    SEEKER_SIZE
                    );
    }

    public void setSeekerDrawable(Drawable seekerDrawable) {
        this.mSeekerDrawable = seekerDrawable;
    }

    public void setOnSeekBarChangeListener(OnSeekBarChangeListener onSeekBarChangeListener) {
        this.mOnSeekBarChangeListener = onSeekBarChangeListener;
    }

    public interface OnSeekBarChangeListener {
        void onSeekChanged(View view, float value);

        void onStartTrackingTouch(View view);

        void onStopTrackingTouch(View view);
    }
}
