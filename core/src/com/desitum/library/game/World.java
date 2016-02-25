package com.desitum.library.game;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
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

    private List<Widget> widgets;
    private List<GameObject> gameObjects;
    private OrthographicCamera camera;

    /**
     * Create new {@link World}
     * @param camera camera from {@link GameScreen}
     */
    public World(OrthographicCamera camera) {
        this.camera = camera;
        this.widgets = new ArrayList<Widget>();
        this.gameObjects = new ArrayList<GameObject>();
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
        for (Widget widget : widgets) {
            widget.updateTouchInput(touchPos, touchDown);
        }
        for (GameObject gameObject : gameObjects) {
            gameObject.updateTouchInput(touchPos, touchDown);
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
        }
    }

    /**
     * Add a {@link GameObject} to be handled by {@link World}
     * @param gameObject {@link GameObject} to be handled
     */
    public void addGameObject(GameObject gameObject) {
        this.gameObjects.add(gameObject);
        if (layerGameObjects) {
            Collections.sort(gameObjects);
        }
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
}
