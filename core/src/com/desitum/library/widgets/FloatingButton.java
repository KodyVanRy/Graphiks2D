package com.desitum.library.widgets;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;

/**
 * Created by kody on 12/26/15.
 * can be used by kody and people in [kody}]
 */
public class FloatingButton extends Button {

    public FloatingButton(Texture text, Texture shadow, String name, float width, float height, float X, float Y, float z, Camera camera) {
        super(text, name, width, height, X, Y, camera);

        setShadow(shadow);
        setZ(z);
    }
}
