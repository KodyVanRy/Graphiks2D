package com.desitum.library.view;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.desitum.library.drawing.Drawable;
import com.desitum.library.game.World;

/**
 * Created by kodyvanry on 5/1/17.
 */

public class Button extends View {

    // DRAWING
    private Drawable mRestTexture;
    private Drawable mHoverTexture;

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
                    setBackgroundDrawable(mHoverTexture);
                break;
            case MOVE:
                if (isTouching(touchEvent) && mHoverTexture != null)
                    setBackgroundDrawable(mHoverTexture);
                else if (mRestTexture != null)
                    setBackgroundDrawable(mRestTexture);
                break;
            case UP:
                if (mRestTexture != null)
                    setBackgroundDrawable(mRestTexture);
        }
        return super.onTouchEvent(touchEvent);
    }

    public void setRestDrawable(Drawable restDrawable) {
        this.mRestTexture = restDrawable;
        if (getBackgroundDrawable() == null)
            setBackgroundDrawable(this.mRestTexture);
    }

    public void setHoverDrawable(Drawable hoverDrawable) {
        this.mHoverTexture = hoverDrawable;
        if (getBackgroundDrawable() == null)
            setBackgroundDrawable(this.mHoverTexture);
    }
}
