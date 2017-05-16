package com.desitum.library.widgets;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.desitum.library.game.World;
import com.desitum.library.view.LayoutConstraints;
import com.desitum.library.view.ProgressBar;

/**
 * Created by kodyvanry on 5/15/17.
 */

public class CircularProgressBar extends ProgressBar {

    public CircularProgressBar(World world) {
        super(world);
    }

    public CircularProgressBar(World world, LayoutConstraints layoutConstraints) {
        super(world, layoutConstraints);
    }

    @Override
    public void onDraw(Batch batch, Viewport viewport) {
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
                    getX() + (getHeight() - (mProgressBarHeight * 0.46f)) / 2,
                    getY() + getHeight() / 2 - (mProgressBarHeight * 0.46f) / 2,
                    (getWidth() - (getHeight() - (mProgressBarHeight * 0.46f))) * mProgress,
                    mProgressBarHeight * 0.46f
            );
        }
    }
}
