package com.desitum.library.world;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.desitum.library.game.World;

/**
 * Created by kody on 12/12/15.
 * can be used by kody and people in [kody}]
 */
public class KodyWorld extends World {

    public KodyWorld(OrthographicCamera cam, Viewport viewport) {
        super(cam, viewport);

    }

    @Override
    public void update(float delta) {
        delta = delta / 10;
        super.update(delta);
    }
}
