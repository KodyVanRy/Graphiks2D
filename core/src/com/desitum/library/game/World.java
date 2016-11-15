package com.desitum.library.game;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.desitum.library.game_objects.GameObject;
import com.desitum.library.game_objects.GameObjectUtils;
import com.desitum.library.game_objects.PolygonGameObject;
import com.desitum.library.particles.ParticleEmitter;
import com.desitum.library.widgets.Layout;
import com.desitum.library.widgets.Widget;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by kody on 12/27/15.
 * can be used by kody and people in [kody}]
 */
public class World implements InputProcessor{

    public boolean layerWidgets = true;
    public boolean layerGameObjects = true;
    private boolean clickDown = false;

    private List<Widget> widgets;
    private List<GameObject> gameObjects;
    private List<PolygonGameObject> shatterPieces;
    private List<ParticleEmitter> particleEmitters;
    private OrthographicCamera camera;
    private Viewport viewport;

    /**
     * Create new {@link World}
     * @param camera camera from {@link GameScreen}
     */
    public World(OrthographicCamera camera, Viewport viewport) {
        this.camera = camera;
        this.viewport = viewport;
        this.widgets = new ArrayList<Widget>();
        this.gameObjects = new ArrayList<GameObject>();
        this.shatterPieces = new ArrayList<PolygonGameObject>();
        this.particleEmitters = new ArrayList<ParticleEmitter>();
    }

    /**
     * update the world every frame using this method
     * @param delta time since last update
     */
    public void update(float delta) {
        for (Widget widget : widgets) {
            widget.update(delta);
        }
        for (GameObject gameObject : gameObjects) {
            gameObject.update(delta);
        }
        for (PolygonGameObject gameObject : shatterPieces) {
            gameObject.update(delta);
        }
        for (ParticleEmitter particleEmitter : particleEmitters) {
            particleEmitter.update(delta);
        }
    }

    /**
     * updates the world based on touch input
     * for best results update before the master update
     * @param touchPos position of mouse or touch point if on mobile
     * @param touchDown is clicking or if currently touching screen
     */
    public void updateTouchInput(Vector3 touchPos, boolean touchDown) {
        if (touchDown && !clickDown) {
            onClickDown(touchPos);
        } else if (!touchDown && clickDown) {
            onClickUp(touchPos);
        }
        clickDown = touchDown;
        for (Widget widget : widgets) {
            widget.updateTouchInput(touchPos, touchDown);
        }
        for (GameObject gameObject2D : gameObjects) {
            gameObject2D.updateTouchInput(touchPos, touchDown);
        }
    }

    /**
     * Add a {@link Widget} to be handled by {@link World}
     * @param widget widget to be handled
     */
    public void addWidget(Widget widget) {
        this.widgets.add(widget);
        if (layerWidgets) {
            Collections.sort(widgets);
            if (widget instanceof Layout) {
                ((Layout) widget).sortWidgets();
            }
        }
    }

    /**
     * Add a {@link GameObject} to be handled by {@link World}
     * @param gameObject2D {@link GameObject} to be handled
     */
    public void addGameObject(GameObject gameObject2D) {
        this.gameObjects.add(gameObject2D);
        if (layerGameObjects) {
            Collections.sort(gameObjects);
        }
    }

    /**
     * Add a {@link ParticleEmitter} to be handled by {@link World}
     * @param particleEmitter {@link ParticleEmitter} to be handled
     */
    public void addParticleEmitter(ParticleEmitter particleEmitter) {
        this.particleEmitters.add(particleEmitter);
    }

    /**
     * Remove a game object from the screen
     * @param gameObject {@link GameObject} to remove
     */
    public void removeGameObject(GameObject gameObject) {
        gameObjects.remove(gameObject);
    }

    /**
     * Remove a widget from the screen
     * @param widget {@link Widget} to remove
     */
    public void removeWidget(Widget widget) {
        widgets.remove(widget);
    }

    /**
     * Get a {@link List} of {@link GameObject}
     * @return {@link List} of {@link GameObject}
     */
    public List<GameObject> getGameObjects() {
        return gameObjects;
    }

    /**
     * Get a {@link List} of {@link GameObject}
     * @return {@link List} of {@link GameObject}
     */
    public List<PolygonGameObject> getShatterPieces() {
        return shatterPieces;
    }

    /**
     * Get a {@link List} of {@link Widget}
     * @return {@link List} of {@link Widget}
     */
    public List<Widget> getWidgets() {
        return widgets;
    }

    /**
     * Get {@link OrthographicCamera} used by the {@link World}
     * @return {@link OrthographicCamera}
     */
    public OrthographicCamera getCamera() {
        return camera;
    }

    public void onClickDown(Vector3 clickPos) {

    }

    public void onClickUp(Vector3 clickPos) {

    }

    public void setCamera(OrthographicCamera camera) {
        this.camera = camera;
    }

    public Viewport getViewport() {
        return viewport;
    }

    public void setViewport(Viewport viewport) {
        this.viewport = viewport;
    }

    public void dispose() {
        for (ParticleEmitter particleEmitter: particleEmitters) {
            particleEmitter.dispose();
        }
        for (Widget widget: widgets) {
            widget.dispose();
        }
        for (GameObject gameObject: gameObjects) {
            gameObject.dispose();
        }
    }

    public void shatterGameObject(GameObject gameObject, float intensity, int pieces, Vector3 pointInGameObject) {
        int index = -1;
        for (int i = 0; i < gameObjects.size(); i++) {
            if (gameObject == gameObjects.get(i)) {
                index = i;
                shatterPieces.addAll(GameObjectUtils.getPiecesFromGameObject(gameObject, intensity, pieces, pointInGameObject));
            }
        }
        if (index > -1)
            gameObjects.get(index).setPosition(50, 50);
    }

    public List<ParticleEmitter> getParticleEmitters() {
        return particleEmitters;
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
