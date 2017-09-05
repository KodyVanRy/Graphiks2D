package com.desitum.library.widgets;

import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;

/**
 * Created by kodyvanry on 2/13/17.
 */

public class VerticalScrollView extends Widget {

    private Widget childView;
    private float scrollAmount;

    /**
     * Create a new {@link VerticalScrollView}
     *
     * @param text   {@link Texture} to be used as the background
     * @param name   String used to identify {@link Widget}
     * @param width  Width of widget
     * @param height height of widget
     * @param x      x position of widget
     * @param y      y position of widget
     * @param parent
     */
    public VerticalScrollView(Texture text, String name, float width, float height, float x, float y, Layout parent) {
        super(text, name, width, height, x, y, parent);
    }
}
