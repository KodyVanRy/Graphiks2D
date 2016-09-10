package com.desitum.library.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.desitum.library.animation.MovementAnimator;
import com.desitum.library.drawing.Drawing;
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
public class KodyWorld implements InputProcessor {

    private OrthographicCamera cam;

    private ArrayList<Widget> widgets;
    private ParticleEmitter pEmitter;
    LinearLayout ll;

    public KodyWorld(OrthographicCamera cam) {
        widgets = new ArrayList<Widget>();
        this.cam = cam;

        Widget widget = MenuBuilder.build(Gdx.files.internal("layout.json"), cam);
        ll = (LinearLayout) widget.findByName("myLayout");

        MovementAnimator animator = new MovementAnimator(-100, 90, 0.9f, Interpolation.DECELERATE_INTERPOLATOR);
        animator.setControllingY(true);

        Slider mSlider = new Slider(Drawing.getFilledRectangle(1, 1, Color.BLUE), "", 100, 10, 0, 0, null);
        ll.addWidget(mSlider);

//        Button button1 = (Button) ll.findByName("button1");
//        button1.startAnimator(animator);
        widgets.add(ll);

        pEmitter = ParticleBuilder.buildParticleEmitter(Gdx.files.internal("wallParticles.prt"));
//        pEmitter.turnOn();
    }

    public void update(float delta) {
        delta = delta / 10;
        for (Widget widget : widgets) {
            widget.update(delta);
        }
        ll.sortWidgets();
        pEmitter.update(delta);
    }

    public OrthographicCamera getCam() {
        return this.cam;
    }

    public void setCam(OrthographicCamera cam) {
        this.cam = cam;
    }

    public void updateTouchInput(Vector3 mousePos, boolean touchDown) {
        for (Widget widget : widgets) {
            widget.updateTouchInput(mousePos, touchDown);
        }
    }

    public ArrayList<Widget> getWidgets() {
        return widgets;
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    public ParticleEmitter getParticles() {
        return pEmitter;
    }
}
