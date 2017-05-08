package com.desitum.library.game;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.desitum.library.game_objects.GameObject;
import com.desitum.library.particles.ParticleEmitter;
import com.desitum.library.view.TouchEvent;
import com.desitum.library.view.View;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by kody on 12/27/15.
 * can be used by kody and people in [kody}]
 */
public class World implements InputProcessor{

    public boolean mLayerGameObjects = true;
    private boolean mClickDown = false;
    private boolean mForegroundClickDown = false;

    private List<GameObject> mGameObjects;
    private List<G2DSprite> mSprites;
    private List<View> mViews;
    private List<ParticleEmitter> mParticleEmitters;
    private OrthographicCamera mCamera;
    private OrthographicCamera mForegroundCamera;
    private Viewport mViewport;
    private Viewport mForegroundViewport;
    private View mViewFocus;
    private TouchEvent mTouchEvent;

    /**
     * Create new {@link World}
     * @param camera camera from {@link GameScreen}
     */
    public World(OrthographicCamera camera, Viewport viewport) {
        this.mCamera = camera;
        this.mViewport = viewport;
        this.mGameObjects = new ArrayList<GameObject>();
        this.mSprites = new ArrayList<G2DSprite>();
        this.mViews = new ArrayList<View>();
        this.mParticleEmitters = new ArrayList<ParticleEmitter>();
        this.mTouchEvent = new TouchEvent();
    }

    /**
     * Create new {@link World}
     * @param camera camera from {@link GameScreen}
     */
    public World(OrthographicCamera camera, Viewport viewport, OrthographicCamera foregroundCamera, Viewport foregroundViewport) {
        this.mCamera = camera;
        this.mViewport = viewport;
        this.mForegroundCamera = foregroundCamera;
        this.mForegroundViewport = foregroundViewport;
        this.mGameObjects = new ArrayList<GameObject>();
        this.mSprites = new ArrayList<G2DSprite>();
        this.mViews = new ArrayList<View>();
        this.mParticleEmitters = new ArrayList<ParticleEmitter>();
        this.mTouchEvent = new TouchEvent();
    }

    /**
     * update the world every frame using this method
     * @param delta time since last update
     */
    public void update(float delta) {
        for (GameObject gameObject : mGameObjects) {
            gameObject.update(delta);
        }
        for (ParticleEmitter particleEmitter : mParticleEmitters) {
            particleEmitter.update(delta);
        }
        for (G2DSprite g2DSprite : mSprites) {
            g2DSprite.update(delta);
        }
        for (G2DSprite g2DSprite : mViews) {
            g2DSprite.update(delta);
        }
    }

    /**
     * updates the world based on touch input
     * for best results update before the master update
     * @param touchPos position of mouse or touch point if on mobile
     * @param touchDown is clicking or if currently touching screen
     */
    public boolean updateTouchInput(Vector3 touchPos, boolean touchDown) {
        boolean returnVal = false;
        mTouchEvent.setX(touchPos.x);
        mTouchEvent.setY(touchPos.y);
        if (touchDown && !mClickDown) {
            returnVal = onTouchDown(touchPos);
        } else if (!touchDown && mClickDown) {
            returnVal = onTouchUp();
        } else if (touchDown && mClickDown) {
            returnVal = onTouchMoved();
        }
        for (GameObject gameObject2D : mGameObjects) {
            gameObject2D.updateTouchInput(touchPos, touchDown);
        }
        mClickDown = touchDown;
        return returnVal;
    }

    /**
     * updates the world based on touch input
     * for best results update before the master update
     * @param touchPos position of mouse or touch point if on mobile
     * @param touchDown is clicking or if currently touching screen
     */
    public boolean updateForegroundTouchInput(Vector3 touchPos, boolean touchDown) {
        boolean returnValue = false;
        if (!mViews.isEmpty()) {
            mTouchEvent.setX(touchPos.x);
            mTouchEvent.setY(touchPos.y);
            if (touchDown && !mForegroundClickDown) {
                returnValue = onTouchDownForeground();
            } else if (!touchDown && mForegroundClickDown) {
                returnValue = onTouchUpForeground();
            } else if (touchDown && mForegroundClickDown) {
                returnValue = onTouchMovedForeground();
            }
            mForegroundClickDown = touchDown;
        }
        return returnValue;
    }

    /**
     * Add a {@link GameObject} to be handled by {@link World}
     * @param gameObject2D {@link GameObject} to be handled
     */
    public void addGameObject(GameObject gameObject2D) {
        this.mGameObjects.add(gameObject2D);
        if (mLayerGameObjects) {
            Collections.sort(mGameObjects);
        }
    }

    /**
     * Add a {@link ParticleEmitter} to be handled by {@link World}
     * @param particleEmitter {@link ParticleEmitter} to be handled
     */
    public void addParticleEmitter(ParticleEmitter particleEmitter) {
        this.mParticleEmitters.add(particleEmitter);
    }

    /**
     * Add a {@link View} to be handled by {@link World}
     * @param view {@link View} to be handled
     */
    public void addView(View view) {
        this.mViews.add(view);
        Collections.sort(mViews);
    }

    /**
     * Remove a game object from the screen
     * @param gameObject {@link GameObject} to remove
     */
    public void removeGameObject(GameObject gameObject) {
        mGameObjects.remove(gameObject);
    }

    /**
     * Remove a view from the screen
     * @param view {@link View} to remove
     */
    public void removeView(View view) {
        mViews.remove(view);
    }

    /**
     * Get a {@link List} of {@link GameObject}
     * @return {@link List} of {@link GameObject}
     */
    public List<GameObject> getGameObjects() {
        return mGameObjects;
    }

    /**
     * Get a {@link List} of {@link View}
     * @return {@link List} of {@link View}
     */
    public List<View> getViews() {
        return mViews;
    }

    /**
     * Get a {@link List} of {@link G2DSprite}
     * @return {@link List} of {@link G2DSprite}
     */
    public List<G2DSprite> getSprites() {
        return mSprites;
    }

    /**
     * Get {@link OrthographicCamera} used by the {@link World}
     * @return {@link OrthographicCamera}
     */
    public OrthographicCamera getCamera() {
        return mCamera;
    }

    /**
     * Get {@link OrthographicCamera} used by the {@link World}
     * @return {@link OrthographicCamera}
     */
    public OrthographicCamera getForegroundCamera() {
        return mForegroundCamera;
    }

    public boolean onTouchDown(Vector3 clickPos) {
        mTouchEvent.setAction(TouchEvent.Action.DOWN);
        // TODO Game Object Touch Events
        return false;
    }

    public boolean onTouchUp() {
        mTouchEvent.setAction(TouchEvent.Action.UP);
        // TODO Game Object Touch Events
        return false;
    }

    private boolean onTouchMoved() {
        mTouchEvent.setAction(TouchEvent.Action.MOVE);
        // TODO Game Object Touch Events
        return false;
    }

    public boolean onTouchDownForeground() {
        mTouchEvent.setAction(TouchEvent.Action.DOWN);
        for (View view : mViews) {
            if (view.isTouching(mTouchEvent)) {
                view.dispatchTouchEvent(mTouchEvent);
                return true;
            }
        }
        return false;
    }

    public boolean onTouchUpForeground() {
        mTouchEvent.setAction(TouchEvent.Action.UP);
        if (mViewFocus != null) {
            mViewFocus.dispatchTouchEvent(mTouchEvent);
            mViewFocus = null;
            return true;
        }
        return false;
    }

    private boolean onTouchMovedForeground() {
        mTouchEvent.setAction(TouchEvent.Action.MOVE);
        if (mViewFocus != null) {
            mViewFocus.dispatchTouchEvent(mTouchEvent);
            return true;
        }
        return false;
    }

    public void setCamera(OrthographicCamera camera) {
        this.mCamera = camera;
    }

    public Viewport getViewport() {
        return mViewport;
    }

    public void setViewport(Viewport viewport) {
        this.mViewport = viewport;
    }

    public Viewport getForegroundViewport() {
        return mForegroundViewport;
    }

    public void dispose() {
        for (ParticleEmitter particleEmitter: mParticleEmitters) {
            particleEmitter.dispose();
        }
        for (GameObject gameObject: mGameObjects) {
            gameObject.dispose();
        }
    }

    public List<ParticleEmitter> getParticleEmitters() {
        return mParticleEmitters;
    }

    public void requestFocus(View view) {
        for (View v : mViews) {
            v.clearFocus();
        }
        mViewFocus = view;
        mViewFocus.setFocus(true);
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
}
