package com.desitum.library.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by kody on 12/27/15.
 * can be used by kody and people in [kody}]
 */
public class GameScreen implements Screen {

    private float mViewportWidth;
    private float mViewportHeight;
    
    private OrthographicCamera mCam;
    private OrthographicCamera mForegroundCam;
    private Viewport mViewport;
    private Viewport mForegroundViewport;
    private SpriteBatch mSpriteBatch;
    private World mWorld;
    private WorldRenderer mWorldRenderer;

    private Vector3 mTouchPos;
    private Vector3 mForegroundTouchPos;
    private Color mClearColor;

    private ShapeRenderer debugShapeRenderer;

    public static final int ASPECT_FILL = 1;
    public static final int ASPECT_FIT = 1 << 1;

    /**
     * Create a new {@link GameScreen} object
     * @param viewportWidth Viewport width to fit to screen
     * @param viewportHeight Viewport height to fit to screen
     * @param world mWorld class controller
     */
    public GameScreen(float viewportWidth, float viewportHeight, World world, WorldRenderer worldRenderer) {
        init(viewportWidth, viewportHeight, world, worldRenderer);
    }

    /**
     * Create a new {@link GameScreen} object
     * @param viewportWidth Viewport width to fit to screen
     * @param viewportHeight Viewport height to fit to screen
     * @param world mWorld class controller
     */
    public GameScreen(float viewportWidth, float viewportHeight, World world) {
        init(viewportWidth, viewportHeight, world, null);
    }

    /**
     * Create a new {@link GameScreen} object
     * @param viewportWidth Viewport width to fit to screen
     * @param viewportHeight Viewport height to fit to screen
     */
    public GameScreen(float viewportWidth, float viewportHeight) {
        init(viewportWidth, viewportHeight, null, null);
    }

    private void init(float viewportWidth, float viewportHeight, World world, WorldRenderer worldRenderer) {
        mSpriteBatch = new SpriteBatch();
        mCam = new OrthographicCamera(viewportWidth, viewportHeight);
        mForegroundCam = new OrthographicCamera(viewportWidth, viewportHeight);
        mCam.position.set(viewportWidth / 2, viewportHeight / 2, 0);
        mForegroundCam.position.set(viewportWidth / 2, viewportHeight / 2, 0);
        mViewport = new FitViewport(viewportWidth, viewportHeight, mCam);
        mForegroundViewport = new FitViewport(viewportWidth, viewportHeight, mForegroundCam);
        mViewportWidth = viewportWidth;
        mViewportHeight = viewportHeight;

        mTouchPos = new Vector3(0, 0, 0);
        mForegroundTouchPos = new Vector3(0, 0, 0);

        if (world == null) {
            mWorld = new World(mCam, mViewport);
        } else {
            mWorld = world;
        }
        if (worldRenderer == null) {
            mWorldRenderer = new WorldRenderer(mWorld);
        } else {
            mWorldRenderer = worldRenderer;
        }
        mClearColor = new Color(0, 0, 0, 1);


        debugShapeRenderer = new ShapeRenderer();
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
        mForegroundTouchPos = mForegroundViewport.unproject(mTouchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0));
        if (!mWorld.updateForegroundTouchInput(mTouchPos, Gdx.input.isTouched())) {
            mTouchPos = mViewport.unproject(mTouchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0));
            mWorld.updateTouchInput(mTouchPos, Gdx.input.isTouched());
        }
        mWorld.update(delta);
    }

    /**
     * Draw the mWorld to the screen
     */
    public void draw() {
        mCam.update();
        mForegroundCam.update();
        mSpriteBatch.setProjectionMatrix(mCam.combined);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glClearColor(mClearColor.r, mClearColor.g, mClearColor.b, mClearColor.a);

        mSpriteBatch.begin();

        mWorldRenderer.draw(mSpriteBatch);

        mSpriteBatch.end();

        mSpriteBatch.setProjectionMatrix(mForegroundCam.combined);

        mSpriteBatch.begin();

        mWorldRenderer.drawForeground(mSpriteBatch);

        mSpriteBatch.end();
    }

    @Override
    public void resize(int width, int height) {
        mViewport.update(width, height);
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
            mSpriteBatch.dispose();
            mWorld.dispose();
        } catch (IllegalArgumentException e) {
            // Never got initialized
        }
    }

    public OrthographicCamera getCam() {
        return mCam;
    }

    public void setCam(OrthographicCamera mCam) {
        this.mCam = mCam;
    }

    public Viewport getViewport() {
        return mViewport;
    }

    public void setViewport(Viewport viewport) {
        this.mViewport = viewport;
    }

    public SpriteBatch getSpriteBatch() {
        return mSpriteBatch;
    }

    public void setSpriteBatch(SpriteBatch spriteBatch) {
        this.mSpriteBatch = spriteBatch;
    }

    public World getWorld() {
        return mWorld;
    }

    public void setWorld(World world) {
        this.mWorld = world;
        world.setCamera(mCam);
        world.setViewport(mViewport);
        mWorldRenderer.setWorld(world);
        this.mWorldRenderer = new WorldRenderer(world);
    }

    public WorldRenderer getWorldRenderer() {
        return mWorldRenderer;
    }

    public void setWorldRenderer(WorldRenderer worldRenderer) {
        this.mWorldRenderer = worldRenderer;
    }

    public Vector3 getTouchPos() {
        return mTouchPos;
    }

    public void setTouchPos(Vector3 touchPos) {
        this.mTouchPos = touchPos;
    }

    public void setClearColor(Color clearColor) {
        this.mClearColor = clearColor;
    }

    public float getViewportWidth() {
        return mViewportWidth;
    }

    public float getViewportHeight() {
        return mViewportHeight;
    }

    protected static float getScreenWidth(float desiredWidth, float desiredHeight,
                                          int aspectFlags) {

        if ((aspectFlags & ASPECT_FIT) != 0) {
            return desiredWidth;
        } else if ((aspectFlags & ASPECT_FILL) != 0) {
            float viewWidth = Gdx.graphics.getWidth();
            float viewHeight = Gdx.graphics.getHeight();
            float viewAspect = viewWidth / viewHeight;

            if (desiredWidth > (desiredHeight * viewAspect)) {
                return desiredWidth;
            } else {
                return (desiredHeight * viewAspect);
            }
        }
        return desiredWidth;
    }


    protected static float getScreenHeight(float desiredWidth, float desiredHeight,
                                           int aspectFlags) {
        if ((aspectFlags & ASPECT_FIT) != 0) {
            return desiredHeight;
        } else if ((aspectFlags & ASPECT_FILL) != 0) {
            float viewWidth = Gdx.graphics.getWidth();
            float viewHeight = Gdx.graphics.getHeight();
            float viewAspect = viewWidth / viewHeight;

            if (desiredWidth > (desiredHeight * viewAspect)) {
                return (desiredWidth / viewAspect);
            } else {
                return desiredHeight;
            }
        }
        return desiredHeight;
    }
}
