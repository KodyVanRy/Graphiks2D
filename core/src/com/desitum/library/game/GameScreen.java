package com.desitum.library.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by kody on 12/27/15.
 * can be used by kody and people in [kody}]
 */
public class GameScreen implements Screen {

    private OrthographicCamera cam;
    private Viewport viewport;
    private SpriteBatch spriteBatch;
    private World world;
    private WorldRenderer worldRenderer;

    private Vector3 touchPos;

    public GameScreen(float viewportWidth, float viewportHeight) {
        spriteBatch = new SpriteBatch();
        cam = new OrthographicCamera(viewportWidth, viewportHeight);
        cam.position.set(viewportWidth / 2, viewportHeight / 2, 0);
        viewport = new FitViewport(viewportWidth, viewportHeight, cam);

        touchPos = new Vector3(0, 0, 0);

        world = new World(cam);
        worldRenderer = new WorldRenderer(world);
    }

    @Override
    public void render(float delta) {
        update(delta);
        draw();
    }

    public void update(float delta) {
        world.update(delta);
        cam.unproject(touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0));
        world.updateTouchInput(touchPos, Gdx.input.isTouched());
    }

    public void draw() {
        cam.update();
        spriteBatch.setProjectionMatrix(cam.combined);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glClearColor(0, 0, 0, 1);

        spriteBatch.begin();

        worldRenderer.draw(spriteBatch);

        spriteBatch.end();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
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
}
