package com.desitum.library.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.desitum.library.animation.MovementAnimator;
import com.desitum.library.drawing.Drawing;
import com.desitum.library.game.World;
import com.desitum.library.interpolation.Interpolation;
import com.desitum.library.particles.ParticleBuilder;
import com.desitum.library.particles.ParticleEmitter;
import com.desitum.library.widgets.Button;
import com.desitum.library.widgets.LinearLayout;
import com.desitum.library.widgets.MenuBuilder;
import com.desitum.library.widgets.Slider;
import com.desitum.library.widgets.Widget;

import java.util.ArrayList;

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
