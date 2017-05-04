package com.desitum.library.view;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.desitum.library.game.World;

/**
 * Created by kodyvanry on 5/1/17.
 */

public class Button extends View {

    // DRAWING
    private TextureRegion mRestTexture;
    private TextureRegion mHoverTexture;

    public Button(World world) {
        this(world, null);
    }

    public Button(World world, LayoutConstraints layoutConstraints) {
        super(world, layoutConstraints);
        setClickable(true);
    }

    @Override
    public boolean onTouchEvent(TouchEvent touchEvent) {
        switch (touchEvent.getAction()) {
            case DOWN:
                if (mHoverTexture != null)
                    setBackgroundTexture(mHoverTexture);
                break;
            case MOVE:
                if (isTouching(touchEvent) && mHoverTexture != null)
                    setBackgroundTexture(mHoverTexture);
                else if (mRestTexture != null)
                    setBackgroundTexture(mRestTexture);
                break;
            case UP:
                if (mRestTexture != null)
                    setBackgroundTexture(mRestTexture);
        }
        return super.onTouchEvent(touchEvent);
    }

    public void setRestTexture(TextureRegion restTexture) {
        this.mRestTexture = restTexture;
        if (getBackgroundTexture() == null)
            setBackgroundTexture(this.mRestTexture);
    }

    public void setHoverTexture(TextureRegion hoverTexture) {
        this.mHoverTexture = hoverTexture;
        if (getBackgroundTexture() == null)
            setBackgroundTexture(this.mHoverTexture);
    }
}
