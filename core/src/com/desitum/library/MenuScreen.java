package com.desitum.library;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.desitum.library.widgets.Widget;
import com.desitum.library.world.KodyWorld;

/**
 * Created by kody on 12/12/15.
 * can be used by kody and people in [kody}]
 */
public class MenuScreen implements Screen {

    public static final float SCREEN_WIDTH = 150.0f;
    public static final float SCREEN_HEIGHT = 100.0f;
    LibraryTest library;
    Vector3 mousePos;
    KodyWorld kodyWorld;
    private OrthographicCamera cam;
    private Viewport viewport;
    private SpriteBatch spriteBatch;

    public MenuScreen(LibraryTest libraryTest) {
        this.library = libraryTest;

        spriteBatch = new SpriteBatch();
        cam = new OrthographicCamera(SCREEN_WIDTH, SCREEN_HEIGHT);
        cam.position.set(SCREEN_WIDTH / 2, SCREEN_HEIGHT / 2, 0);
        viewport = new FitViewport(SCREEN_WIDTH, SCREEN_HEIGHT, cam);

        mousePos = new Vector3(0, 0, 0);

        kodyWorld = new KodyWorld(cam);
    }

    @Override
    public void show() {

    }

    private void update(float delta) {
        mousePos = viewport.unproject(mousePos.set(Gdx.input.getX(), Gdx.input.getY(), 0));
        kodyWorld.updateTouchInput(mousePos, Gdx.input.isTouched());

        kodyWorld.update(delta);
    }

    @Override
    public void render(float delta) {
        update(delta);

        cam.update();
        spriteBatch.setProjectionMatrix(cam.combined);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glClearColor(0, 0, 0, 1);

        spriteBatch.begin();
        for (Widget widget : kodyWorld.getWidgets()) {
            widget.draw(spriteBatch, viewport);
        }
        kodyWorld.getParticles().draw(spriteBatch);
        spriteBatch.end();
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
    public void resize(int width, int height) {
        viewport.update(width, height);
    }
}
