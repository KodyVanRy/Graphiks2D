package com.desitum.library.game;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.desitum.library.game_objects.GameObject;
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
public class World {

    public boolean layerWidgets = true;
    public boolean layerGameObjects = true;
    private boolean clickDown = false;

    private List<Widget> widgets;
    private List<GameObject> gameObjects;
    private List<ParticleEmitter> particleEmitters;
    private OrthographicCamera camera;

    /**
     * Create new {@link World}
     * @param camera camera from {@link GameScreen}
     */
    public World(OrthographicCamera camera) {
        this.camera = camera;
        this.widgets = new ArrayList<Widget>();
        this.gameObjects = new ArrayList<GameObject>();
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

    public List<ParticleEmitter> getParticleEmitters() {
        return particleEmitters;
    }
}
