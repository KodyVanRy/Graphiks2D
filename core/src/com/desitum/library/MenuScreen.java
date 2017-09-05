package com.desitum.library;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.desitum.library.animation.MovementAnimator;
import com.desitum.library.drawing.Drawing;
import com.desitum.library.game.GameScreen;
import com.desitum.library.game_objects.GameObject;
import com.desitum.library.interpolation.Interpolation;
import com.desitum.library.particles.ParticleBuilder;
import com.desitum.library.widgets.LinearLayout;
import com.desitum.library.widgets.MenuBuilder;
import com.desitum.library.widgets.Slider;
import com.desitum.library.widgets.Widget;

/**
 * Created by kody on 12/12/15.
 * can be used by kody and people in [kody}]
 */
public class MenuScreen extends GameScreen {

    public static final float SCREEN_WIDTH = 1500.0f;
    public static final float SCREEN_HEIGHT = 1000.0f;

    public MenuScreen() {
        super(SCREEN_WIDTH, SCREEN_HEIGHT);
        setupWorld();
        setClearColor(new Color(1, 0, 0, 1));
    }

    private void setupWorld() {
        System.out.println("setupWorld()");
        Widget widget = MenuBuilder.build(Gdx.files.internal("layout.json"), getCam());
        LinearLayout ll = (LinearLayout) widget.findByName("myLayout");

        MovementAnimator animator = new MovementAnimator(-100, 0, 0.5f, Interpolation.DECELERATE_INTERPOLATOR);
        animator.setControllingY(true);
        animator.setRemoveOnFinish(true );

        widget.startAnimator(animator);

        Slider mSlider = new Slider(Drawing.getFilledRectangle(1, 1, new Color(0, 0, 0, 0)), new Texture(Gdx.files.internal("diamond.png")), Drawing.getHollowRectangle(100, 3, 3, Color.WHITE), "", 100, 10, 0, 0, null);
        ll.addWidget(mSlider);

        getWorld().addWidget(widget);

        getWorld().addParticleEmitter(ParticleBuilder.buildParticleEmitter(Gdx.files.internal("wallParticles.prt")));
        getWorld().getParticleEmitters().get(0).turnOn();
        getWorld().addGameObject(new GameObject(new Texture("diamond.png")));
        getWorld().addGameObject(new Lol(new Texture("badlogic.jpg")));
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

    class Lol extends GameObject {

        public Lol(Texture texture) {
            super(texture);
        }
    }
}
