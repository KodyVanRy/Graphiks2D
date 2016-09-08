package com.desitum.library.widgets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.utils.ScissorStack;

/**
 * Created by kody on 12/11/15.
 * can be used by kody and people in [kody}]
 */
public class PopupMenu extends Layout {

    public PopupMenu(Texture text, String name, float width, float height, float X, float Y, Layout parent) {
        super(text, name, width, height, X, Y, parent);
    }

    @Override
    public void draw(Batch batch, Camera camera) {
        Rectangle scissor = new Rectangle();
        Rectangle clipBounds = new Rectangle(getX(), getY(), getWidth(), getHeight());
        ScissorStack.calculateScissors(camera, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), batch.getTransformMatrix(), clipBounds, scissor);
        ScissorStack.pushScissors(scissor);

        super.draw(batch, camera);

        ScissorStack.popScissors();
    }
}
