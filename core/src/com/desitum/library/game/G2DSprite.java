package com.desitum.library.game;

import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.desitum.library.view.TouchEvent;

/**
 * Created by kodyvanry on 5/2/17.
 */

public abstract class G2DSprite extends com.badlogic.gdx.graphics.g2d.Sprite implements Comparable<G2DSprite> {

    private int mLayer = 0;

    public G2DSprite() {
        super();
    }

    public void setLayer(int mLayer) {
        this.mLayer = mLayer;
    }

    public int getLayer() {
        return mLayer;
    }

    @Override
    public int compareTo(G2DSprite o) {
        return mLayer - o.mLayer;
    }

    public abstract void update(float delta);

    public boolean onTouchDown(TouchEvent touchEvent) {
        return false;
    }

    public boolean onTouchMoved(TouchEvent touchEvent) {
        return false;
    }

    public boolean onTouchUp(TouchEvent touchEvent) {
        return false;
    }
}
