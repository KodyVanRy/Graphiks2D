package com.desitum.library.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by kody on 12/27/15.
 * can be used by kody and people in [kody}]
 */
public class GameScreen implements Screen {

    private OrthographicCamera cam;
    private Viewport viewport;
    private SpriteBatch spriteBatch;
    private PolygonSpriteBatch polygonSpriteBatch;
    private World world;
    private WorldRenderer worldRenderer;

    private Vector3 touchPos;
    private Color clearColor;

    /**
     * Create a new {@link GameScreen} object
     * @param viewportWidth Viewport width to fit to screen
     * @param viewportHeight Viewport height to fit to screen
     * @param world world class controller
     */
    public GameScreen(float viewportWidth, float viewportHeight, World world, WorldRenderer worldRenderer) {
        spriteBatch = new SpriteBatch();
        cam = new OrthographicCamera(viewportWidth, viewportHeight);
        cam.position.set(viewportWidth / 2, viewportHeight / 2, 0);
        viewport = new FitViewport(viewportWidth, viewportHeight, cam);

        touchPos = new Vector3(0, 0, 0);

        world.setCamera(cam);
        world.setViewport(viewport);
        worldRenderer.setWorld(world);
        clearColor = new Color(0, 0, 0, 1);
    }

    /**
     * Create a new {@link GameScreen} object
     * @param viewportWidth Viewport width to fit to screen
     * @param viewportHeight Viewport height to fit to screen
     * @param world world class controller
     */
    public GameScreen(float viewportWidth, float viewportHeight, World world) {
        spriteBatch = new SpriteBatch();
        cam = new OrthographicCamera(viewportWidth, viewportHeight);
        cam.position.set(viewportWidth / 2, viewportHeight / 2, 0);
        viewport = new FitViewport(viewportWidth, viewportHeight, cam);

        touchPos = new Vector3(0, 0, 0);

        world.setCamera(cam);
        world.setViewport(viewport);
        worldRenderer = new WorldRenderer(world);
        clearColor = new Color(0, 0, 0, 1);
    }

    /**
     * Create a new {@link GameScreen} object
     * @param viewportWidth Viewport width to fit to screen
     * @param viewportHeight Viewport height to fit to screen
     */
    public GameScreen(float viewportWidth, float viewportHeight) {
        spriteBatch = new SpriteBatch();
        cam = new OrthographicCamera(viewportWidth, viewportHeight);
        cam.position.set(viewportWidth / 2, viewportHeight / 2, 0);
        viewport = new FitViewport(viewportWidth, viewportHeight, cam);

        touchPos = new Vector3(0, 0, 0);

        world = new World(cam, viewport);
        worldRenderer = new WorldRenderer(world);
        clearColor = new Color(0, 0, 0, 1);
    }

    @Override
    public void render(float delta) {
        update(delta);
        draw();
    }

    /**
     * Update based on time since last frame
     * @param delta time since last frame
     */
    public void update(float delta) {
        touchPos = viewport.unproject(touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0));
        world.updateTouchInput(touchPos, Gdx.input.isTouched());
        world.update(delta);
        System.out.println("polygonSpriteBatch1 = " + polygonSpriteBatch);
        System.out.println("getShatterPieces = " + world.getShatterPieces());
        System.out.println("getShatterPieces() = " + world.getShatterPieces() != null);
        if (world.getShatterPieces() != null && polygonSpriteBatch == null) {
            polygonSpriteBatch = new PolygonSpriteBatch();
            System.out.println("polygonSpriteBatch2 = " + polygonSpriteBatch);
        }
        System.out.println("polygonSpriteBatch3 = " + polygonSpriteBatch);
        System.out.println("getShatterPieces = " + world.getShatterPieces());
    }

    /**
     * Draw the world to the screen
     */
    public void draw() {
        cam.update();
        spriteBatch.setProjectionMatrix(cam.combined);
        if (polygonSpriteBatch != null)
            polygonSpriteBatch.setProjectionMatrix(cam.combined);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glClearColor(clearColor.r, clearColor.g, clearColor.b, clearColor.a);

        spriteBatch.begin();

        worldRenderer.draw(spriteBatch, polygonSpriteBatch);
        spriteBatch.end();
        if (polygonSpriteBatch != null) {
            polygonSpriteBatch.begin();
            worldRenderer.draw(polygonSpriteBatch);
            polygonSpriteBatch.end();
        }
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
        try {
            spriteBatch.dispose();
            world.dispose();
        } catch (IllegalArgumentException e) {
            // Never got initialized
        }
    }

    public OrthographicCamera getCam() {
        return cam;
    }

    public void setCam(OrthographicCamera cam) {
        this.cam = cam;
    }

    public Viewport getViewport() {
        return viewport;
    }

    public void setViewport(Viewport viewport) {
        this.viewport = viewport;
    }

    public SpriteBatch getSpriteBatch() {
        return spriteBatch;
    }

    public void setSpriteBatch(SpriteBatch spriteBatch) {
        this.spriteBatch = spriteBatch;
    }

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
        world.setCamera(cam);
        world.setViewport(viewport);
        worldRenderer.setWorld(world);
        this.worldRenderer = new WorldRenderer(world);
    }

    public WorldRenderer getWorldRenderer() {
        return worldRenderer;
    }

    public void setWorldRenderer(WorldRenderer worldRenderer) {
        this.worldRenderer = worldRenderer;
    }

    public Vector3 getTouchPos() {
        return touchPos;
    }

    public void setTouchPos(Vector3 touchPos) {
        this.touchPos = touchPos;
    }

    public void setClearColor(Color clearColor) {
        this.clearColor = clearColor;
    }
}
