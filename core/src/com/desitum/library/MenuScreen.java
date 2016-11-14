package com.desitum.library;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.desitum.library.animation.MovementAnimator;
import com.desitum.library.drawing.Drawing;
import com.desitum.library.game.GameScreen;
import com.desitum.library.game_objects.GameObject;
import com.desitum.library.game_objects.GameObjectUtils;
import com.desitum.library.interpolation.Interpolation;
import com.desitum.library.particles.ParticleBuilder;
import com.desitum.library.widgets.LinearLayout;
import com.desitum.library.widgets.MenuBuilder;
import com.desitum.library.widgets.Slider;
import com.desitum.library.widgets.Widget;
import com.desitum.library.world.KodyWorld;

/**
 * Created by kody on 12/12/15.
 * can be used by kody and people in [kody}]
 */
public class MenuScreen extends GameScreen implements GameObject.OnClickListener {

    public static final float SCREEN_WIDTH = 150.0f;
    public static final float SCREEN_HEIGHT = 100.0f;

    public MenuScreen() {
        super(SCREEN_WIDTH, SCREEN_HEIGHT);
        setupWorld();
        setClearColor(new Color(1, 0, 0, 1));
    }

    private void setupWorld() {
        Widget widget = MenuBuilder.build(Gdx.files.internal("layout.json"), getCam());
        LinearLayout ll = (LinearLayout) widget.findByName("myLayout");

        MovementAnimator animator = new MovementAnimator(-100, 90, 0.9f, Interpolation.DECELERATE_INTERPOLATOR);
        animator.setControllingY(true);

        Slider mSlider = new Slider(Drawing.getFilledRectangle(1, 1, new Color(0, 0, 0, 0)), new Texture(Gdx.files.internal("slider.png")), Drawing.getHollowRectangle(100, 3, 3, Color.WHITE), "", 100, 10, 0, 0, null);
        ll.addWidget(mSlider);

        getWorld().addWidget(ll);

        getWorld().addParticleEmitter(ParticleBuilder.buildParticleEmitter(Gdx.files.internal("wallParticles.prt")));
        getWorld().getParticleEmitters().get(0).turnOn();

        GameObject shatterObject = new GameObject(
                new Texture("badlogic.jpg"),
                10, 10, 0, 0
        );
        shatterObject.setOnClickListener(this);
        getWorld().addGameObject(shatterObject);
    }

    @Override
    public void show() {

    }


    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    @Override
    public void onClick(GameObject gameObject, Vector3 touchPoint) {
        getWorld().shatterGameObject(gameObject, 0.1f, 7, GameObjectUtils.convertPointToLocal(gameObject, touchPoint));
    }
}
