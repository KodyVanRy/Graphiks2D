package com.desitum.library.view;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.desitum.library.game.World;
import com.desitum.library.logging.Log;

/**
 * Created by kodyvanry on 5/1/17.
 */

public class ProgressBar extends View {

    protected static final float PROGRESS_BAR_HEIGHT = 2;

    private float mProgress;
    private TextureRegion mProgressTexture;
    private TextureRegion mProgressBackgroundTexture;

    public ProgressBar(World world) {
        super(world);
    }

    public ProgressBar(World world, LayoutConstraints layoutConstraints) {
        super(world, layoutConstraints);
    }

    @Override
    public void onDraw(Batch spriteBatch, Viewport viewport) {
        super.onDraw(spriteBatch, viewport);
        if (mProgressBackgroundTexture != null) {
            spriteBatch.draw(
                    mProgressBackgroundTexture,
                    getX(),
                    getY() + getHeight() / 2 - PROGRESS_BAR_HEIGHT / 2,
                    getWidth(),
                    PROGRESS_BAR_HEIGHT
            );
        }
        if (mProgressTexture != null) {
            spriteBatch.draw(
                    mProgressTexture,
                    getX(),
                    getY() + getHeight() / 2 - PROGRESS_BAR_HEIGHT / 2,
                    getWidth() * mProgress,
                    PROGRESS_BAR_HEIGHT
            );
        }
    }

    public void setProgress(float progress) {
        this.mProgress = Math.max(Math.min(progress, 1), 0);
    }

    public float getProgress() {
        return mProgress;
    }

    public void setProgressBackgroundTexture(TextureRegion progressBackgroundTexture) {
        this.mProgressBackgroundTexture = progressBackgroundTexture;
    }

    public void setProgressTexture(TextureRegion progressTexture) {
        this.mProgressTexture = progressTexture;
    }
}
