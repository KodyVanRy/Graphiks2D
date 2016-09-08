package com.desitum.library.widgets;

import com.badlogic.gdx.graphics.Texture;

/**
 * Created by kody on 12/26/15.
 * can be used by kody and people in [kody}]
 */
public class FloatingButton extends Button {

    public FloatingButton(Texture text, Texture shadow, String name, float width, float height, float x, float y, float z, Layout parent) {
        super(text, name, width, height, x, y, parent);

        setShadow(shadow);
        setZ(z);
    }
}
